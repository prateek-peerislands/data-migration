# 🚀 MCP Database Integration System

This project provides a complete solution for interacting with PostgreSQL and MongoDB databases using natural language queries through MCP (Model Context Protocol) servers.

## 🏗️ Architecture Overview

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Frontend      │    │   Spring Boot    │    │   MCP Bridge    │
│   (HTML/JS)     │◄──►│   Backend        │◄──►│   Service       │
│                 │    │   (Java)         │    │   (Node.js)     │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                │                       │
                                ▼                       ▼
                       ┌──────────────────┐    ┌─────────────────┐
                       │   PostgreSQL     │    │   MongoDB       │
                       │   MCP Server     │    │   MCP Server    │
                       └──────────────────┘    └─────────────────┘
```

## 🎯 Features

- **Natural Language Queries**: Ask questions like "backup my database" or "what is the current state of postgres"
- **Multi-Database Support**: Works with both PostgreSQL and MongoDB
- **Real-time MCP Integration**: Direct communication with MCP servers
- **Modern Web Interface**: Beautiful, responsive UI for database management
- **Intelligent Query Routing**: Automatically routes queries to appropriate databases
- **Health Monitoring**: Check the status of all MCP servers

## 🚀 Quick Start

### Prerequisites

- Java 17+ (for Spring Boot)
- Node.js 16+ (for MCP Bridge)
- PostgreSQL database running
- MongoDB Atlas account (for MongoDB MCP)

### 1. Start the MCP Bridge Service

```bash
# Install dependencies
npm install

# Start the service
npm start
```

The MCP Bridge will start on port 3000 and automatically launch your MCP servers.

### 2. Start the Spring Boot Application

```bash
# Using Maven
mvn spring-boot:run

# Or using the wrapper
./mvnw spring-boot:run
```

The Spring Boot app will start on port 8080.

### 3. Access the Web Interface

Open your browser and navigate to:
- **Main Interface**: http://localhost:8080/database-interface.html
- **API Endpoints**: http://localhost:8080/api/mcp/*

## 📋 Available Endpoints

### Frontend
- `GET /database-interface.html` - Main database management interface

### Backend API
- `POST /api/mcp/query` - Process natural language queries
- `GET /api/mcp/health` - Check MCP server health
- `GET /api/mcp/tools` - List available MCP tools
- `GET /api/mcp/examples` - Get example queries

### MCP Bridge Service
- `GET /health` - Bridge service health check
- `POST /mcp/postgresql` - PostgreSQL MCP operations
- `POST /mcp/mongodb` - MongoDB MCP operations

## 💬 Example Queries

### Database Operations
```
backup my database
backup the customer table
export all data from postgres
create a backup of mongo collections
```

### Status & Health
```
what is the current state of postgres
show me mongo cluster health
database status check
what's the health of my databases
```

### Analysis & Structure
```
analyze the dvdrental database structure
show me the schema of customer table
what collections exist in mongo
analyze database relationships
```

### Data Queries
```
show me all films in the database
find customers with more than 10 rentals
list all tables in postgres
show mongo collections
```

## 🔧 Configuration

### Application Properties

```properties
# MCP Server Configuration
mcp.postgresql.endpoint=http://localhost:3000/mcp/postgresql
mcp.mongodb.endpoint=http://localhost:3000/mcp/mongodb

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/dvdrental
spring.datasource.username=postgres
spring.datasource.password=183254
```

### MCP Server Configuration

The system uses your existing MCP configuration from `~/.cursor/mcp.json`:

```json
{
  "MongoDB": {
    "command": "npx",
    "args": ["-y", "mongodb-mcp-server", "--apiClientId", "...", "--apiClientSecret", "..."]
  },
  "postgresql": {
    "command": "npx",
    "args": ["-y", "@executeautomation/database-server", "--postgresql", "--host", "localhost", "--database", "dvdrental", "--user", "postgres", "--password", "183254"]
  }
}
```

## 🏗️ System Components

### 1. Frontend (`database-interface.html`)
- Modern, responsive web interface
- Natural language query input
- Real-time results display
- Example queries for quick start

### 2. Spring Boot Backend
- **MCPIntegrationService**: Core service for MCP communication
- **MCPInterfaceController**: REST API endpoints
- **MCPConfig**: Configuration and beans setup

### 3. MCP Bridge Service (`mcp-bridge.js`)
- Node.js service that bridges MCP servers
- HTTP endpoints for database operations
- Automatic MCP server management

### 4. MCP Servers
- **PostgreSQL MCP**: Database operations and schema analysis
- **MongoDB MCP**: Collection management and document operations

## 🔍 How It Works

1. **User Input**: User types a natural language query in the frontend
2. **Query Analysis**: Backend analyzes the query to determine:
   - Target database (PostgreSQL, MongoDB, or both)
   - Operation type (backup, status, analysis, etc.)
   - Specific targets (tables, collections)
3. **MCP Routing**: Query is routed to appropriate MCP server
4. **Execution**: MCP server executes the database operation
5. **Response**: Results are formatted and returned to the user

## 🛠️ Development

### Adding New MCP Tools

1. **Update MCPIntegrationService**: Add new tool mappings
2. **Update MCP Bridge**: Add new tool execution logic
3. **Update Frontend**: Add new example queries

### Customizing Query Analysis

Modify the `analyzeQuery` method in `MCPIntegrationService` to:
- Recognize new keywords
- Support new operation types
- Handle database-specific syntax

## 🚨 Troubleshooting

### Common Issues

1. **MCP Servers Not Starting**
   - Check Node.js version (requires 16+)
   - Verify MCP server packages are available
   - Check network connectivity for MongoDB Atlas

2. **Database Connection Errors**
   - Verify PostgreSQL is running on port 5432
   - Check database credentials in application.properties
   - Ensure database 'dvdrental' exists

3. **Frontend Not Loading**
   - Check Spring Boot is running on port 8080
   - Verify static files are in the correct location
   - Check browser console for JavaScript errors

### Debug Mode

Enable debug logging in `application.properties`:

```properties
logging.level.com.dvdrental.management.service.MCPIntegrationService=DEBUG
logging.level.com.dvdrental.management.controller.MCPInterfaceController=DEBUG
```

## 🔮 Future Enhancements

- **AI-Powered Query Understanding**: Better natural language processing
- **Query Templates**: Pre-built queries for common operations
- **Real-time Monitoring**: Live database performance metrics
- **Multi-User Support**: User authentication and permissions
- **Query History**: Save and replay previous queries
- **Export Formats**: Support for more export formats (XML, YAML, etc.)

## 📚 Resources

- [MCP (Model Context Protocol) Documentation](https://modelcontextprotocol.io/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [PostgreSQL MCP Server](https://github.com/executeautomation/database-server)
- [MongoDB MCP Server](https://github.com/mongodb/mongodb-mcp-server)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**Happy Database Management! 🎉**
