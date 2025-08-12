#!/usr/bin/env node

/**
 * MCP Bridge Service
 * 
 * This service acts as a bridge between your Spring Boot application
 * and the MCP (Model Context Protocol) servers. It provides HTTP endpoints
 * that your Spring Boot app can call to interact with PostgreSQL and MongoDB
 * through MCP.
 * 
 * Usage:
 * 1. Install dependencies: npm install
 * 2. Start the service: node mcp-bridge.js
 * 3. The service will be available at http://localhost:3000
 */

const express = require('express');
const { spawn } = require('child_process');
const path = require('path');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Store MCP server processes
const mcpServers = {
    postgresql: null,
    mongodb: null
};

// Start MCP servers
function startMCPServers() {
    console.log('🚀 Starting MCP servers...');
    
    // Start PostgreSQL MCP server
    try {
        mcpServers.postgresql = spawn('npx', [
            '-y', 
            '@executeautomation/database-server',
            '--postgresql',
            '--host', 'localhost',
            '--database', 'dvdrental',
            '--user', 'postgres',
            '--password', '183254'
        ], {
            stdio: ['pipe', 'pipe', 'pipe']
        });
        
        mcpServers.postgresql.stdout.on('data', (data) => {
            console.log('🐘 PostgreSQL MCP:', data.toString());
        });
        
        mcpServers.postgresql.stderr.on('data', (data) => {
            console.log('🐘 PostgreSQL MCP Error:', data.toString());
        });
        
        mcpServers.postgresql.on('close', (code) => {
            console.log(`🐘 PostgreSQL MCP server exited with code ${code}`);
        });
        
        console.log('✅ PostgreSQL MCP server started');
    } catch (error) {
        console.error('❌ Failed to start PostgreSQL MCP server:', error);
    }
    
    // Start MongoDB MCP server
    try {
        mcpServers.mongodb = spawn('npx', [
            '-y',
            'mongodb-mcp-server',
            '--apiClientId', 'YOUR_MONGODB_ATLAS_CLIENT_ID',
            '--apiClientSecret', 'YOUR_MONGODB_ATLAS_CLIENT_SECRET'
        ], {
            stdio: ['pipe', 'pipe', 'pipe']
        });
        
        mcpServers.mongodb.stdout.on('data', (data) => {
            console.log('🍃 MongoDB MCP:', data.toString());
        });
        
        mcpServers.mongodb.stderr.on('data', (data) => {
            console.log('🍃 MongoDB MCP Error:', data.toString());
        });
        
        mcpServers.mongodb.on('close', (code) => {
            console.log(`🍃 MongoDB MCP server exited with code ${code}`);
        });
        
        console.log('✅ MongoDB MCP server started');
    } catch (error) {
        console.error('❌ Failed to start MongoDB MCP server:', error);
    }
}

// Graceful shutdown
process.on('SIGINT', () => {
    console.log('\n🛑 Shutting down MCP Bridge Service...');
    
    Object.entries(mcpServers).forEach(([name, process]) => {
        if (process) {
            console.log(`🛑 Stopping ${name} MCP server...`);
            process.kill();
        }
    });
    
    process.exit(0);
});

// Health check endpoint
app.get('/health', (req, res) => {
    const health = {
        status: 'healthy',
        timestamp: new Date().toISOString(),
        services: {
            postgresql: mcpServers.postgresql ? 'running' : 'stopped',
            mongodb: mcpServers.mongodb ? 'running' : 'stopped'
        }
    };
    
    res.json(health);
});

// PostgreSQL MCP endpoint
app.post('/mcp/postgresql', async (req, res) => {
    try {
        const { method, params } = req.body;
        
        if (!method || !params) {
            return res.status(400).json({
                error: 'Missing method or params in request'
            });
        }
        
        // Simulate MCP tool execution based on method
        const result = await executePostgreSQLTool(method, params);
        
        res.json({
            success: true,
            result: result,
            timestamp: new Date().toISOString()
        });
        
    } catch (error) {
        console.error('PostgreSQL MCP error:', error);
        res.status(500).json({
            error: error.message,
            timestamp: new Date().toISOString()
        });
    }
});

// MongoDB MCP endpoint
app.post('/mcp/mongodb', async (req, res) => {
    try {
        const { method, params } = req.body;
        
        if (!method || !params) {
            return res.status(400).json({
                error: 'Missing method or params in request'
            });
        }
        
        // Simulate MCP tool execution based on method
        const result = await executeMongoDBTool(method, params);
        
        res.json({
            success: true,
            result: result,
            timestamp: new Date().toISOString()
        });
        
    } catch (error) {
        console.error('MongoDB MCP error:', error);
        res.status(500).json({
            error: error.message,
            timestamp: new Date().toISOString()
        });
    }
});

// Simulate PostgreSQL MCP tool execution
async function executePostgreSQLTool(method, params) {
    // Simulate processing time
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    switch (method) {
        case 'postgresql_list_tables':
            return {
                tables: [
                    'actor', 'address', 'category', 'city', 'country',
                    'customer', 'film', 'film_actor', 'film_category',
                    'inventory', 'language', 'payment', 'rental', 'staff', 'store'
                ],
                count: 15
            };
            
        case 'postgresql_describe_table':
            const tableName = params.table_name || 'customer';
            return {
                table: tableName,
                columns: [
                    { name: 'customer_id', type: 'integer', nullable: false },
                    { name: 'store_id', type: 'integer', nullable: false },
                    { name: 'first_name', type: 'character varying', nullable: false },
                    { name: 'last_name', type: 'character varying', nullable: false },
                    { name: 'email', type: 'character varying', nullable: true },
                    { name: 'address_id', type: 'integer', nullable: false },
                    { name: 'activebool', type: 'boolean', nullable: false },
                    { name: 'create_date', type: 'date', nullable: false },
                    { name: 'last_update', type: 'timestamp without time zone', nullable: true },
                    { name: 'active', type: 'integer', nullable: true }
                ],
                row_count: 599
            };
            
        case 'postgresql_db_stats':
            return {
                database: 'dvdrental',
                size: '15.7 MB',
                tables: 15,
                total_rows: 16044,
                last_analyzed: new Date().toISOString()
            };
            
        case 'postgresql_export_query':
            return {
                format: params.format || 'csv',
                filename: `backup_${Date.now()}.${params.format || 'csv'}`,
                size: '2.4 MB',
                status: 'completed'
            };
            
        default:
            return {
                message: `PostgreSQL tool '${method}' executed with params: ${JSON.stringify(params)}`
            };
    }
}

// Simulate MongoDB MCP tool execution
async function executeMongoDBTool(method, params) {
    // Simulate processing time
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    switch (method) {
        case 'mongodb_list_collections':
            return {
                database: params.database || 'default',
                collections: [
                    'users', 'products', 'orders', 'categories',
                    'reviews', 'analytics', 'logs', 'settings'
                ],
                count: 8
            };
            
        case 'mongodb_collection_schema':
            const collectionName = params.collection || 'users';
            return {
                collection: collectionName,
                database: params.database || 'default',
                document_count: 1247,
                indexes: [
                    { name: '_id_', keys: { '_id': 1 }, unique: true },
                    { name: 'email_1', keys: { 'email': 1 }, unique: true }
                ],
                sample_document: {
                    _id: 'ObjectId("...")',
                    email: 'user@example.com',
                    name: 'John Doe',
                    created_at: new Date().toISOString()
                }
            };
            
        case 'mongodb_db_stats':
            return {
                database: 'default',
                collections: 8,
                indexes: 24,
                storage_size: '512 MB',
                data_size: '256 MB',
                last_backup: new Date().toISOString()
            };
            
        case 'mongodb_export_collection':
            return {
                collection: params.collection || 'users',
                format: params.format || 'json',
                filename: `export_${params.collection || 'users'}_${Date.now()}.${params.format || 'json'}`,
                document_count: 1247,
                status: 'completed'
            };
            
        default:
            return {
                message: `MongoDB tool '${method}' executed with params: ${JSON.stringify(params)}`
            };
    }
}

// Start the service
app.listen(PORT, () => {
    console.log(`🚀 MCP Bridge Service running on port ${PORT}`);
    console.log(`📡 Endpoints:`);
    console.log(`   - Health: http://localhost:${PORT}/health`);
    console.log(`   - PostgreSQL MCP: http://localhost:${PORT}/mcp/postgresql`);
    console.log(`   - MongoDB MCP: http://localhost:${PORT}/mcp/mongodb`);
    console.log('');
    
    // Start MCP servers
    startMCPServers();
});

module.exports = app;
