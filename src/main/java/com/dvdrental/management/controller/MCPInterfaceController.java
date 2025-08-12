package com.dvdrental.management.controller;

import com.dvdrental.management.service.RealMCPClientService;
import com.dvdrental.management.service.RealMCPClientService.MCPQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.HashMap;

/**
 * REST Controller for handling MCP (Model Context Protocol) interface requests.
 * This controller provides endpoints for natural language database queries
 * and routes them to appropriate MCP servers for execution.
 */
@RestController
@RequestMapping("/api/mcp")
@CrossOrigin(origins = "*")
public class MCPInterfaceController {
    
    private static final Logger logger = LoggerFactory.getLogger(MCPInterfaceController.class);
    
    private final RealMCPClientService mcpClientService;
    
    @Autowired
    public MCPInterfaceController(RealMCPClientService mcpClientService) {
        this.mcpClientService = mcpClientService;
    }
    
    /**
     * Process a natural language database query
     * 
     * @param request The query request containing the natural language query
     * @return ResponseEntity with the query result
     */
    @PostMapping("/query")
    public ResponseEntity<Map<String, Object>> processQuery(@RequestBody QueryRequest request) {
        logger.info("Received MCP query request: {}", request.getQuery());
        
        try {
            // Validate request
            if (request.getQuery() == null || request.getQuery().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("Query cannot be empty"));
            }
            
            // Process the query through real MCP client service
            MCPQueryResult result = mcpClientService.processQuery(request.getQuery().trim());
            
            // Create response
            Map<String, Object> response = new HashMap<>();
            response.put("success", result.isSuccess());
            response.put("message", result.getMessage());
            response.put("data", result.getData());
            response.put("timestamp", result.getTimestamp());
            response.put("query", request.getQuery());
            
            if (result.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.ok(response); // Still return 200 but with error in response body
            }
            
        } catch (Exception e) {
            logger.error("Error processing MCP query: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                .body(createErrorResponse("Internal server error: " + e.getMessage()));
        }
    }
    
    /**
     * Get the health status of MCP servers
     * 
     * @return ResponseEntity with server health information
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getHealthStatus() {
        logger.info("Health check requested for MCP servers");
        
        try {
            Map<String, Object> healthStatus = new HashMap<>();
            
            // Check PostgreSQL MCP server health
            try {
                MCPQueryResult postgresHealth = mcpClientService.processQuery("what is the current state of postgres");
                healthStatus.put("postgresql", Map.of(
                    "status", postgresHealth.isSuccess() ? "healthy" : "unhealthy",
                    "message", postgresHealth.getMessage(),
                    "timestamp", postgresHealth.getTimestamp()
                ));
            } catch (Exception e) {
                healthStatus.put("postgresql", Map.of(
                    "status", "unhealthy",
                    "message", "Connection failed: " + e.getMessage(),
                    "timestamp", System.currentTimeMillis()
                ));
            }
            
            // Check MongoDB MCP server health
            try {
                MCPQueryResult mongoHealth = mcpClientService.processQuery("what is the current state of mongo");
                healthStatus.put("mongodb", Map.of(
                    "status", mongoHealth.isSuccess() ? "healthy" : "unhealthy",
                    "message", mongoHealth.getMessage(),
                    "timestamp", mongoHealth.getTimestamp()
                ));
            } catch (Exception e) {
                healthStatus.put("mongodb", Map.of(
                    "status", "unhealthy",
                    "message", "Connection failed: " + e.getMessage(),
                    "timestamp", System.currentTimeMillis()
                ));
            }
            
            // Overall system health
            boolean overallHealthy = healthStatus.values().stream()
                .anyMatch(status -> "healthy".equals(((Map<?, ?>) status).get("status")));
            
            healthStatus.put("overall", Map.of(
                "status", overallHealthy ? "healthy" : "degraded",
                "timestamp", System.currentTimeMillis()
            ));
            
            return ResponseEntity.ok(healthStatus);
            
        } catch (Exception e) {
            logger.error("Error checking MCP server health: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                .body(createErrorResponse("Health check failed: " + e.getMessage()));
        }
    }
    
    /**
     * Get available MCP tools and their capabilities
     * 
     * @return ResponseEntity with available tools information
     */
    @GetMapping("/tools")
    public ResponseEntity<Map<String, Object>> getAvailableTools() {
        logger.info("Available MCP tools requested");
        
        Map<String, Object> tools = new HashMap<>();
        
        // PostgreSQL tools
        tools.put("postgresql", Map.of(
            "name", "PostgreSQL MCP Server",
            "tools", new String[]{
                "mcp_postgresql_read_query",
                "mcp_postgresql_write_query", 
                "mcp_postgresql_list_tables",
                "mcp_postgresql_describe_table",
                "mcp_postgresql_db_stats",
                "mcp_postgresql_export_query"
            },
            "capabilities", new String[]{
                "Execute SQL queries",
                "List and describe tables",
                "Database statistics",
                "Data export and backup",
                "Schema analysis"
            }
        ));
        
        // MongoDB tools
        tools.put("mongodb", Map.of(
            "name", "MongoDB MCP Server",
            "tools", new String[]{
                "mcp_MongoDB_find",
                "mcp_MongoDB_aggregate",
                "mcp_MongoDB_list_collections",
                "mcp_MongoDB_collection_schema",
                "mcp_MongoDB_db_stats",
                "mcp_MongoDB_export_collection"
            },
            "capabilities", new String[]{
                "Document queries",
                "Aggregation pipelines",
                "Collection management",
                "Schema analysis",
                "Data export and backup"
            }
        ));
        
        return ResponseEntity.ok(tools);
    }
    
    /**
     * Get example queries for users
     * 
     * @return ResponseEntity with example queries
     */
    @GetMapping("/examples")
    public ResponseEntity<Map<String, Object>> getExampleQueries() {
        logger.info("Example queries requested");
        
        Map<String, Object> examples = new HashMap<>();
        
        examples.put("backup", new String[]{
            "backup my database",
            "backup the customer table",
            "export all data from postgres",
            "create a backup of mongo collections"
        });
        
        examples.put("status", new String[]{
            "what is the current state of postgres",
            "show me mongo cluster health",
            "database status check",
            "what's the health of my databases"
        });
        
        examples.put("analysis", new String[]{
            "analyze the dvdrental database structure",
            "show me the schema of customer table",
            "what collections exist in mongo",
            "analyze database relationships"
        });
        
        examples.put("query", new String[]{
            "show me all films in the database",
            "find customers with more than 10 rentals",
            "list all tables in postgres",
            "show mongo collections"
        });
        
        return ResponseEntity.ok(examples);
    }
    
    /**
     * Create an error response map
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", message);
        error.put("timestamp", System.currentTimeMillis());
        return error;
    }
    
    /**
     * Inner class for query request
     */
    public static class QueryRequest {
        private String query;
        
        public String getQuery() {
            return query;
        }
        
        public void setQuery(String query) {
            this.query = query;
        }
    }
}
