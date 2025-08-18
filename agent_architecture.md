# 🏗️ MCP Database Agent - Architecture Diagram

## 📊 **System Architecture Overview**

```mermaid
graph TB
    %% User Interface Layer
    subgraph "User Interface Layer"
        CLI[CLI Interface]
        INTERACTIVE[Interactive Mode]
        NATURAL[Natural Language Processing]
    end

    %% Core Application Layer
    subgraph "Core Application Layer"
        MCP_AGENT[MCPAgent<br/>Orchestrator]
        CLI_HANDLER[CLI Handler]
        COMMAND_PARSER[Command Parser<br/>Commander.js]
    end

    %% MCP Protocol Layer
    subgraph "MCP Protocol Layer"
        MCP_CLIENT[MCPClient<br/>Protocol Handler]
        REAL_MCP_SERVER[RealMCPServer<br/>Database Connections]
        MCP_TOOLS[MCP Tools Registry]
    end

    %% Service Layer
    subgraph "Service Layer"
        POSTGRES_SERVICE[PostgreSQLService<br/>PostgreSQL Operations]
        MONGO_SERVICE[MongoDBService<br/>MongoDB Operations]
        SCHEMA_SERVICE[SchemaService<br/>Schema Management]
        MIGRATION_SERVICE[MigrationService<br/>Data Migration]
        MARKDOWN_GEN[MarkdownGenerator<br/>Documentation]
    end

    %% Database Layer
    subgraph "Database Layer"
        POSTGRES[(PostgreSQL<br/>Database)]
        MONGO[(MongoDB<br/>Database)]
    end

    %% Configuration Layer
    subgraph "Configuration Layer"
        CONFIG_LOADER[ConfigLoader<br/>Environment & Files]
        INTERACTIVE_SETUP[Interactive Setup<br/>User Configuration]
        DEFAULT_CONFIG[Default Configuration]
    end

    %% Monitoring & Health
    subgraph "Monitoring & Health"
        HEALTH_CHECKER[Health Checker<br/>Every 5 minutes]
        STATUS_MONITOR[Status Monitor]
        PERFORMANCE_METRICS[Performance Metrics]
    end

    %% Connections
    CLI --> CLI_HANDLER
    INTERACTIVE --> CLI_HANDLER
    NATURAL --> CLI_HANDLER
    
    CLI_HANDLER --> COMMAND_PARSER
    COMMAND_PARSER --> MCP_AGENT
    
    MCP_AGENT --> MCP_CLIENT
    MCP_AGENT --> POSTGRES_SERVICE
    MCP_AGENT --> MONGO_SERVICE
    MCP_AGENT --> SCHEMA_SERVICE
    MCP_AGENT --> MIGRATION_SERVICE
    MCP_AGENT --> MARKDOWN_GEN
    
    MCP_CLIENT --> REAL_MCP_SERVER
    REAL_MCP_SERVER --> POSTGRES
    REAL_MCP_SERVER --> MONGO
    
    POSTGRES_SERVICE --> MCP_CLIENT
    MONGO_SERVICE --> MCP_CLIENT
    SCHEMA_SERVICE --> MCP_CLIENT
    
    CONFIG_LOADER --> MCP_AGENT
    INTERACTIVE_SETUP --> CONFIG_LOADER
    DEFAULT_CONFIG --> CONFIG_LOADER
    
    HEALTH_CHECKER --> MCP_AGENT
    STATUS_MONITOR --> MCP_AGENT
    PERFORMANCE_METRICS --> MCP_AGENT

    %% Styling
    classDef userLayer fill:#e1f5fe,stroke:#01579b,stroke-width:2px
    classDef coreLayer fill:#f3e5f5,stroke:#4a148c,stroke-width:2px
    classDef mcpLayer fill:#e8f5e8,stroke:#1b5e20,stroke-width:2px
    classDef serviceLayer fill:#fff3e0,stroke:#e65100,stroke-width:2px
    classDef dbLayer fill:#fce4ec,stroke:#880e4f,stroke-width:2px
    classDef configLayer fill:#f1f8e9,stroke:#33691e,stroke-width:2px
    classDef monitorLayer fill:#e0f2f1,stroke:#004d40,stroke-width:2px

    class CLI,INTERACTIVE,NATURAL userLayer
    class MCP_AGENT,CLI_HANDLER,COMMAND_PARSER coreLayer
    class MCP_CLIENT,REAL_MCP_SERVER,MCP_TOOLS mcpLayer
    class POSTGRES_SERVICE,MONGO_SERVICE,SCHEMA_SERVICE,MIGRATION_SERVICE,MARKDOWN_GEN serviceLayer
    class POSTGRES,MONGO dbLayer
    class CONFIG_LOADER,INTERACTIVE_SETUP,DEFAULT_CONFIG configLayer
    class HEALTH_CHECKER,STATUS_MONITOR,PERFORMANCE_METRICS monitorLayer
```

## 🔄 **Data Flow Architecture**

```mermaid
sequenceDiagram
    participant User
    participant CLI
    participant MCPAgent
    participant MCPClient
    participant RealMCPServer
    participant PostgreSQL
    participant MongoDB

    %% Configuration Flow
    User->>CLI: Start Application
    CLI->>MCPAgent: Initialize with Config
    MCPAgent->>MCPClient: Initialize MCP Server
    MCPClient->>RealMCPServer: Connect to Databases
    RealMCPServer->>PostgreSQL: Establish Connection
    RealMCPServer->>MongoDB: Establish Connection
    RealMCPServer-->>MCPClient: Connection Status
    MCPClient-->>MCPAgent: Initialization Complete
    MCPAgent-->>CLI: Ready for Commands

    %% Query Execution Flow
    User->>CLI: Execute Query Command
    CLI->>MCPAgent: Process Command
    MCPAgent->>MCPClient: Call MCP Tool
    MCPClient->>RealMCPServer: Execute Database Operation
    RealMCPServer->>PostgreSQL: Execute Query
    PostgreSQL-->>RealMCPServer: Query Results
    RealMCPServer-->>MCPClient: Operation Results
    MCPClient-->>MCPAgent: Query Results
    MCPAgent-->>CLI: Formatted Results
    CLI-->>User: Display Results

    %% Health Check Flow
    Note over MCPAgent: Every 5 minutes
    MCPAgent->>MCPClient: Perform Health Check
    MCPClient->>RealMCPServer: Check Database Health
    RealMCPServer->>PostgreSQL: Health Check
    RealMCPServer->>MongoDB: Health Check
    RealMCPServer-->>MCPClient: Health Status
    MCPClient-->>MCPAgent: Update Health Status
```

## 🔀 **Application Flow Diagram**

```mermaid
flowchart TD
    START([Start Application]) --> CHECK_CONFIG{Configuration Exists?}
    
    CHECK_CONFIG -->|No| INTERACTIVE_SETUP[Interactive Setup]
    CHECK_CONFIG -->|Yes| LOAD_CONFIG[Load Configuration]
    
    INTERACTIVE_SETUP --> VALIDATE_CONFIG{Valid Configuration?}
    LOAD_CONFIG --> VALIDATE_CONFIG
    
    VALIDATE_CONFIG -->|No| SHOW_ERROR[Show Error & Exit]
    VALIDATE_CONFIG -->|Yes| INIT_MCP[Initialize MCP Client]
    
    INIT_MCP --> CONNECT_DBS[Connect to Databases]
    CONNECT_DBS --> HEALTH_CHECK[Perform Health Check]
    
    HEALTH_CHECK --> DB_HEALTHY{All DBs Healthy?}
    DB_HEALTHY -->|No| SHOW_STATUS[Show Connection Status]
    DB_HEALTHY -->|Yes| READY[Ready for Commands]
    
    SHOW_STATUS --> WAIT_RETRY[Wait for Retry]
    WAIT_RETRY --> CONNECT_DBS
    
    READY --> PARSE_COMMAND{Parse User Command}
    
    PARSE_COMMAND --> QUERY_CMD[Query Command]
    PARSE_COMMAND --> SCHEMA_CMD[Schema Command]
    PARSE_COMMAND --> MIGRATE_CMD[Migrate Command]
    PARSE_COMMAND --> STATUS_CMD[Status Command]
    PARSE_COMMAND --> INTERACTIVE_CMD[Interactive Mode]
    PARSE_COMMAND --> HELP_CMD[Help Command]
    
    %% Query Command Flow
    QUERY_CMD --> SELECT_DB{Select Database}
    SELECT_DB -->|PostgreSQL| PG_QUERY[Execute PostgreSQL Query]
    SELECT_DB -->|MongoDB| MONGO_QUERY[Execute MongoDB Operation]
    SELECT_DB -->|Cross-DB| CROSS_QUERY[Execute Cross-Database Query]
    
    PG_QUERY --> PG_RESULT[Process PostgreSQL Result]
    MONGO_QUERY --> MONGO_RESULT[Process MongoDB Result]
    CROSS_QUERY --> CROSS_RESULT[Process Cross-DB Result]
    
    PG_RESULT --> DISPLAY_RESULT[Display Results]
    MONGO_RESULT --> DISPLAY_RESULT
    CROSS_RESULT --> DISPLAY_RESULT
    
    %% Schema Command Flow
    SCHEMA_CMD --> SCHEMA_OP{Schema Operation}
    SCHEMA_OP -->|Analyze| ANALYZE_SCHEMA[Analyze PostgreSQL Schema]
    SCHEMA_OP -->|Validate| VALIDATE_SCHEMA[Validate Schema]
    SCHEMA_OP -->|Compare| COMPARE_SCHEMA[Compare Schemas]
    SCHEMA_OP -->|Generate| GENERATE_SCHEMA[Generate MongoDB Schema]
    
    ANALYZE_SCHEMA --> MARKDOWN_GEN[Generate Markdown Documentation]
    VALIDATE_SCHEMA --> VALIDATION_RESULT[Show Validation Results]
    COMPARE_SCHEMA --> COMPARISON_RESULT[Show Comparison Results]
    GENERATE_SCHEMA --> SCHEMA_RESULT[Show Generated Schema]
    
    MARKDOWN_GEN --> DISPLAY_RESULT
    VALIDATION_RESULT --> DISPLAY_RESULT
    COMPARISON_RESULT --> DISPLAY_RESULT
    SCHEMA_RESULT --> DISPLAY_RESULT
    
    %% Migration Command Flow
    MIGRATE_CMD --> MIGRATION_OP{Migration Operation}
    MIGRATION_OP -->|Validate| VALIDATE_MIGRATION[Validate Migration]
    MIGRATION_OP -->|Execute| EXECUTE_MIGRATION[Execute Migration]
    
    VALIDATE_MIGRATION --> MIGRATION_RESULT[Show Migration Results]
    EXECUTE_MIGRATION --> MIGRATION_RESULT
    
    MIGRATION_RESULT --> DISPLAY_RESULT
    
    %% Status Command Flow
    STATUS_CMD --> STATUS_OP{Status Operation}
    STATUS_OP -->|Health| HEALTH_STATUS[Show Health Status]
    STATUS_OP -->|Metrics| PERFORMANCE_METRICS[Show Performance Metrics]
    STATUS_OP -->|General| GENERAL_STATUS[Show General Status]
    
    HEALTH_STATUS --> DISPLAY_RESULT
    PERFORMANCE_METRICS --> DISPLAY_RESULT
    GENERAL_STATUS --> DISPLAY_RESULT
    
    %% Interactive Mode Flow
    INTERACTIVE_CMD --> NATURAL_LANG[Natural Language Processing]
    NATURAL_LANG --> PARSE_NATURAL{Parse Natural Language}
    
    PARSE_NATURAL -->|PostgreSQL| PG_NATURAL[Handle PostgreSQL Request]
    PARSE_NATURAL -->|MongoDB| MONGO_NATURAL[Handle MongoDB Request]
    PARSE_NATURAL -->|Schema| SCHEMA_NATURAL[Handle Schema Request]
    PARSE_NATURAL -->|Status| STATUS_NATURAL[Handle Status Request]
    
    PG_NATURAL --> PG_QUERY
    MONGO_NATURAL --> MONGO_QUERY
    SCHEMA_NATURAL --> SCHEMA_CMD
    STATUS_NATURAL --> STATUS_CMD
    
    %% Help Command Flow
    HELP_CMD --> SHOW_HELP[Display Help Information]
    SHOW_HELP --> READY
    
    %% Display Results and Continue
    DISPLAY_RESULT --> CONTINUE{Continue?}
    CONTINUE -->|Yes| READY
    CONTINUE -->|No| CLEANUP[Cleanup Resources]
    
    CLEANUP --> EXIT([Exit Application])
    
    %% Error Handling
    SHOW_ERROR --> EXIT
    
    %% Styling
    classDef startEnd fill:#e8f5e8,stroke:#2e7d32,stroke-width:3px
    classDef process fill:#e3f2fd,stroke:#1565c0,stroke-width:2px
    classDef decision fill:#fff3e0,stroke:#ef6c00,stroke-width:2px
    classDef error fill:#ffebee,stroke:#c62828,stroke-width:2px
    classDef result fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px
    
    class START,EXIT startEnd
    class INTERACTIVE_SETUP,LOAD_CONFIG,INIT_MCP,CONNECT_DBS,HEALTH_CHECK,READY,PG_QUERY,MONGO_QUERY,CROSS_QUERY,ANALYZE_SCHEMA,VALIDATE_SCHEMA,COMPARE_SCHEMA,GENERATE_SCHEMA,EXECUTE_MIGRATION,HEALTH_STATUS,PERFORMANCE_METRICS,GENERAL_STATUS,NATURAL_LANG,PG_NATURAL,MONGO_NATURAL,SCHEMA_NATURAL,STATUS_NATURAL,SHOW_HELP,CLEANUP process
    class CHECK_CONFIG,VALIDATE_CONFIG,DB_HEALTHY,SELECT_DB,SCHEMA_OP,MIGRATION_OP,STATUS_OP,PARSE_NATURAL,CONTINUE decision
    class SHOW_ERROR error
    class PG_RESULT,MONGO_RESULT,CROSS_RESULT,MARKDOWN_GEN,VALIDATION_RESULT,COMPARISON_RESULT,SCHEMA_RESULT,MIGRATION_RESULT,DISPLAY_RESULT result
```

## 🔄 **Natural Language Processing Flow**

```mermaid
flowchart TD
    USER_INPUT[User Natural Language Input] --> INPUT_PARSER[Input Parser]
    
    INPUT_PARSER --> PATTERN_MATCH{Pattern Matching}
    
    PATTERN_MATCH -->|PostgreSQL| PG_PATTERN[PostgreSQL Pattern Detected]
    PATTERN_MATCH -->|MongoDB| MONGO_PATTERN[MongoDB Pattern Detected]
    PATTERN_MATCH -->|Schema| SCHEMA_PATTERN[Schema Pattern Detected]
    PATTERN_MATCH -->|Status| STATUS_PATTERN[Status Pattern Detected]
    PATTERN_MATCH -->|Unknown| HELP_PATTERN[Show Help & Examples]
    
    %% PostgreSQL Pattern Processing
    PG_PATTERN --> PG_OPERATION{Operation Type}
    PG_OPERATION -->|UPDATE| PG_UPDATE[Parse UPDATE Operation]
    PG_OPERATION -->|DELETE| PG_DELETE[Parse DELETE Operation]
    PG_OPERATION -->|FETCH| PG_FETCH[Parse FETCH Operation]
    PG_OPERATION -->|COUNT| PG_COUNT[Parse COUNT Operation]
    
    PG_UPDATE --> PG_UPDATE_SQL[Generate UPDATE SQL]
    PG_DELETE --> PG_DELETE_SQL[Generate DELETE SQL]
    PG_FETCH --> PG_FETCH_SQL[Generate SELECT SQL]
    PG_COUNT --> PG_COUNT_SQL[Generate COUNT SQL]
    
    PG_UPDATE_SQL --> PG_EXECUTE[Execute PostgreSQL Query]
    PG_DELETE_SQL --> PG_EXECUTE
    PG_FETCH_SQL --> PG_EXECUTE
    PG_COUNT_SQL --> PG_EXECUTE
    
    %% MongoDB Pattern Processing
    MONGO_PATTERN --> MONGO_OPERATION{Operation Type}
    MONGO_OPERATION -->|UPDATE| MONGO_UPDATE[Parse UPDATE Operation]
    MONGO_OPERATION -->|DELETE| MONGO_DELETE[Parse DELETE Operation]
    MONGO_OPERATION -->|FETCH| MONGO_FETCH[Parse FETCH Operation]
    MONGO_OPERATION -->|COUNT| MONGO_COUNT[Parse COUNT Operation]
    
    MONGO_UPDATE --> MONGO_UPDATE_OP[Generate MongoDB Update]
    MONGO_DELETE --> MONGO_DELETE_OP[Generate MongoDB Delete]
    MONGO_FETCH --> MONGO_FETCH_OP[Generate MongoDB Find]
    MONGO_COUNT --> MONGO_COUNT_OP[Generate MongoDB Count]
    
    MONGO_UPDATE_OP --> MONGO_EXECUTE[Execute MongoDB Operation]
    MONGO_DELETE_OP --> MONGO_EXECUTE
    MONGO_FETCH_OP --> MONGO_EXECUTE
    MONGO_COUNT_OP --> MONGO_EXECUTE
    
    %% Schema Pattern Processing
    SCHEMA_PATTERN --> SCHEMA_OPERATION{Schema Operation}
    SCHEMA_OPERATION -->|Analyze| SCHEMA_ANALYZE[Trigger Schema Analysis]
    SCHEMA_OPERATION -->|Compare| SCHEMA_COMPARE[Trigger Schema Comparison]
    SCHEMA_OPERATION -->|Validate| SCHEMA_VALIDATE[Trigger Schema Validation]
    
    SCHEMA_ANALYZE --> SCHEMA_CMD
    SCHEMA_COMPARE --> SCHEMA_CMD
    SCHEMA_VALIDATE --> SCHEMA_CMD
    
    %% Status Pattern Processing
    STATUS_PATTERN --> STATUS_OPERATION{Status Operation}
    STATUS_OPERATION -->|Health| STATUS_HEALTH[Trigger Health Check]
    STATUS_OPERATION -->|Metrics| STATUS_METRICS[Trigger Performance Metrics]
    STATUS_OPERATION -->|General| STATUS_GENERAL[Show General Status]
    
    STATUS_HEALTH --> STATUS_CMD
    STATUS_METRICS --> STATUS_CMD
    STATUS_GENERAL --> STATUS_CMD
    
    %% Execution and Results
    PG_EXECUTE --> PG_RESULT_PROCESS[Process PostgreSQL Results]
    MONGO_EXECUTE --> MONGO_RESULT_PROCESS[Process MongoDB Results]
    
    PG_RESULT_PROCESS --> FORMAT_RESULT[Format Results]
    MONGO_RESULT_PROCESS --> FORMAT_RESULT
    
    FORMAT_RESULT --> DISPLAY_USER[Display to User]
    HELP_PATTERN --> DISPLAY_USER
    
    %% Error Handling
    PG_EXECUTE --> PG_ERROR{Success?}
    MONGO_EXECUTE --> MONGO_ERROR{Success?}
    
    PG_ERROR -->|No| PG_ERROR_HANDLE[Handle PostgreSQL Error]
    MONGO_ERROR -->|No| MONGO_ERROR_HANDLE[Handle MongoDB Error]
    
    PG_ERROR_HANDLE --> DISPLAY_USER
    MONGO_ERROR_HANDLE --> DISPLAY_USER
    
    %% Styling
    classDef input fill:#e8f5e8,stroke:#2e7d32,stroke-width:2px
    classDef process fill:#e3f2fd,stroke:#1565c0,stroke-width:2px
    classDef decision fill:#fff3e0,stroke:#ef6c00,stroke-width:2px
    classDef result fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px
    classDef error fill:#ffebee,stroke:#c62828,stroke-width:2px
    
    class USER_INPUT input
    class INPUT_PARSER,PG_PATTERN,MONGO_PATTERN,SCHEMA_PATTERN,STATUS_PATTERN,PG_UPDATE,PG_DELETE,PG_FETCH,PG_COUNT,MONGO_UPDATE,MONGO_DELETE,MONGO_FETCH,MONGO_COUNT,SCHEMA_ANALYZE,SCHEMA_COMPARE,SCHEMA_VALIDATE,STATUS_HEALTH,STATUS_METRICS,STATUS_GENERAL,PG_EXECUTE,MONGO_EXECUTE,PG_RESULT_PROCESS,MONGO_RESULT_PROCESS,FORMAT_RESULT,DISPLAY_USER process
    class PATTERN_MATCH,PG_OPERATION,MONGO_OPERATION,SCHEMA_OPERATION,STATUS_OPERATION,PG_ERROR,MONGO_ERROR decision
    class PG_UPDATE_SQL,PG_DELETE_SQL,PG_FETCH_SQL,PG_COUNT_SQL,MONGO_UPDATE_OP,MONGO_DELETE_OP,MONGO_FETCH_OP,MONGO_COUNT_OP,SCHEMA_CMD,STATUS_CMD result
    class PG_ERROR_HANDLE,MONGO_ERROR_HANDLE,HELP_PATTERN error
```



## 🔧 **MCP Tools Architecture**

```mermaid
graph TB
    subgraph "MCP Tools Available"
        subgraph "PostgreSQL MCP Tools"
            PG_READ[mcp_postgresql_read_query]
            PG_WRITE[mcp_postgresql_write_query]
            PG_TABLES[mcp_postgresql_list_tables]
            PG_DESCRIBE[mcp_postgresql_describe_table]
            PG_CREATE[mcp_postgresql_create_table]
            PG_ALTER[mcp_postgresql_alter_table]
            PG_DROP[mcp_postgresql_drop_table]
            PG_EXPORT[mcp_postgresql_export_query]
        end
        
        subgraph "MongoDB MCP Tools"
            MONGO_CONNECT[mcp_MongoDB_connect]
            MONGO_DBS[mcp_MongoDB_list-databases]
            MONGO_COLLECTIONS[mcp_MongoDB_list-collections]
            MONGO_FIND[mcp_MongoDB_find]
            MONGO_INSERT[mcp_MongoDB_insert-many]
            MONGO_UPDATE[mcp_MongoDB_update-many]
            MONGO_DELETE[mcp_MongoDB_delete-many]
            MONGO_COUNT[mcp_MongoDB_count]
            MONGO_AGGREGATE[mcp_MongoDB_aggregate]
        end
        
        subgraph "MongoDB Atlas MCP Tools"
            ATLAS_CLUSTERS[mcp_MongoDB_atlas-list-clusters]
            ATLAS_PROJECTS[mcp_MongoDB_atlas-list-projects]
            ATLAS_CREATE_CLUSTER[mcp_MongoDB_atlas-create-free-cluster]
            ATLAS_CONNECT[mcp_MongoDB_atlas-connect-cluster]
        end
    end

    subgraph "Tool Usage Flow"
        USER_REQUEST[User Request]
        TOOL_SELECTION[Tool Selection]
        PARAMETER_MAPPING[Parameter Mapping]
        EXECUTION[Tool Execution]
        RESULT_PROCESSING[Result Processing]
    end

    USER_REQUEST --> TOOL_SELECTION
    TOOL_SELECTION --> PARAMETER_MAPPING
    PARAMETER_MAPPING --> EXECUTION
    EXECUTION --> RESULT_PROCESSING

    %% Tool connections
    TOOL_SELECTION --> PG_READ
    TOOL_SELECTION --> PG_WRITE
    TOOL_SELECTION --> MONGO_FIND
    TOOL_SELECTION --> MONGO_INSERT
    TOOL_SELECTION --> ATLAS_CLUSTERS
```

## 📁 **File Structure Architecture**

```mermaid
graph TD
    ROOT[cursor-database/]
    
    ROOT --> SRC[src/]
    ROOT --> SCRIPTS[scripts/]
    ROOT --> CONFIG_FILES[Configuration Files]
    
    SRC --> CORE[core/]
    SRC --> SERVICES[services/]
    SRC --> CLI[cli/]
    SRC --> CONFIG[config/]
    SRC --> SERVER[server/]
    SRC --> TYPES[types/]
    SRC --> INDEX[index.ts]
    
    CORE --> MCP_AGENT[MCPAgent.ts]
    CORE --> MCP_CLIENT[MCPClient.ts]
    CORE --> MCP_BRIDGE[MCPBridge.ts]
    
    SERVICES --> POSTGRES_SERVICE[PostgreSQLService.ts]
    SERVICES --> MONGO_SERVICE[MongoDBService.ts]
    SERVICES --> SCHEMA_SERVICE[SchemaService.ts]
    SERVICES --> MIGRATION_SERVICE[MigrationService.ts]
    SERVICES --> MARKDOWN_GEN[MarkdownGenerator.ts]
    
    CLI --> CLI_MAIN[CLI.ts]
    
    CONFIG --> CONFIG_LOADER[config-loader.ts]
    CONFIG --> DEFAULT_CONFIG[default-config.ts]
    CONFIG --> INTERACTIVE_SETUP[interactive-setup.ts]
    
    SERVER --> REAL_MCP_SERVER[RealMCPServer.ts]
    
    TYPES --> INDEX_TYPES[index.ts]
    
    CONFIG_FILES --> ENV_TEMPLATE[env.template]
    CONFIG_FILES --> ENV_EXAMPLE[env.example]
    CONFIG_FILES --> MCP_CONFIG[mcp-config.json]
    
    SCRIPTS --> SETUP_ENV[setup-env.sh]
```

## 🚀 **Deployment Architecture**

```mermaid
graph TB
    subgraph "Development Environment"
        DEV_USER[Developer]
        DEV_CLI[CLI Tool]
        DEV_MCP_AGENT[MCP Agent]
        DEV_MCP_SERVER[Real MCP Server]
        DEV_POSTGRES[(Dev PostgreSQL)]
        DEV_MONGO[(Dev MongoDB)]
    end
    
    subgraph "Production Environment"
        PROD_USER[End User]
        PROD_CLI[CLI Tool]
        PROD_MCP_AGENT[MCP Agent]
        PROD_MCP_SERVER[Real MCP Server]
        PROD_POSTGRES[(Prod PostgreSQL)]
        PROD_MONGO[(Prod MongoDB)]
    end
    
    subgraph "MCP Tools Integration"
        MCP_TOOLS_REGISTRY[MCP Tools Registry]
        POSTGRES_MCP_TOOLS[PostgreSQL MCP Tools]
        MONGO_MCP_TOOLS[MongoDB MCP Tools]
        ATLAS_MCP_TOOLS[MongoDB Atlas MCP Tools]
    end
    
    %% Development Flow
    DEV_USER --> DEV_CLI
    DEV_CLI --> DEV_MCP_AGENT
    DEV_MCP_AGENT --> DEV_MCP_SERVER
    DEV_MCP_SERVER --> DEV_POSTGRES
    DEV_MCP_SERVER --> DEV_MONGO
    
    %% Production Flow
    PROD_USER --> PROD_CLI
    PROD_CLI --> PROD_MCP_AGENT
    PROD_MCP_AGENT --> PROD_MCP_SERVER
    PROD_MCP_SERVER --> PROD_POSTGRES
    PROD_MCP_SERVER --> PROD_MONGO
    
    %% MCP Tools Integration
    DEV_MCP_AGENT --> MCP_TOOLS_REGISTRY
    PROD_MCP_AGENT --> MCP_TOOLS_REGISTRY
    MCP_TOOLS_REGISTRY --> POSTGRES_MCP_TOOLS
    MCP_TOOLS_REGISTRY --> MONGO_MCP_TOOLS
    MCP_TOOLS_REGISTRY --> ATLAS_MCP_TOOLS
```

## 🔍 **Key Architectural Patterns**

### **1. MCP-First Architecture**
- **No Direct Database Connections**: All operations go through MCP tools
- **Protocol Abstraction**: Database operations abstracted via MCP protocol
- **Tool Registry**: Centralized MCP tools management

### **2. Service-Oriented Architecture**
- **Separation of Concerns**: Each database type has dedicated service
- **Service Orchestration**: MCPAgent coordinates between services
- **Loose Coupling**: Services communicate through well-defined interfaces

### **3. Command Pattern**
- **CLI Commands**: Structured command processing via Commander.js
- **Natural Language**: Human-readable commands converted to operations
- **Interactive Mode**: Real-time command processing

### **4. Factory Pattern**
- **Service Creation**: Services instantiated based on configuration
- **Connection Management**: Database connections managed centrally
- **Configuration Loading**: Multiple configuration sources supported

### **5. Observer Pattern**
- **Health Monitoring**: Continuous health status updates
- **Status Tracking**: Real-time database connection status
- **Performance Metrics**: Ongoing performance monitoring

## 🎯 **Architecture Benefits**

1. **Scalability**: Easy to add new database types via MCP tools
2. **Maintainability**: Clear separation of concerns and modular design
3. **Flexibility**: Configuration-driven behavior and MCP tool integration
4. **Reliability**: Health monitoring, retry logic, and error handling
5. **User Experience**: Natural language processing and interactive CLI
6. **Standards Compliance**: Built on MCP protocol standards

This architecture provides a robust, scalable, and maintainable foundation for database orchestration through the Model Context Protocol.
