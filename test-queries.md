# 🧪 Test Queries for MCP Database Integration System

Use these queries to test the improved system functionality:

## 🐘 PostgreSQL Tests (Should Work with Real Data)

### 1. List Tables
```
show me all tables in postgres
```
**Expected**: Real table list from your dvdrental database

### 2. Database Status
```
what is the current state of postgres
```
**Expected**: Real database size, table count, and row count

### 3. Table Data Retrieval
```
show me data from customer table
```
**Expected**: First 10 rows from the customer table with real data

### 4. Table Structure Analysis
```
analyze the customer table structure
```
**Expected**: Real column definitions, data types, and row count

### 5. Database Analysis
```
analyze the dvdrental database structure
```
**Expected**: Real database statistics and overview

## 🍃 MongoDB Tests (Should Show Connection Status)

### 1. Cluster Status
```
what is the current state of mongo cluster
```
**Expected**: Status showing MCP server connection required

### 2. List Collections
```
show me all collections in mongo
```
**Expected**: Note that real collections unavailable, MCP server connection required

### 3. Collection Analysis
```
analyze the users collection structure
```
**Expected**: Note that real analysis requires MCP server connection

## 🔄 Cross-Database Tests

### 1. General Query
```
backup my database
```
**Expected**: Backup commands prepared for both databases

### 2. Status Check
```
database status check
```
**Expected**: Combined status from both PostgreSQL and MongoDB

## 🎯 What's Fixed

### ✅ PostgreSQL Improvements
- **Real Data Retrieval**: Now fetches actual data from tables using JDBC
- **Real Statistics**: Database size, table count, and row count from actual queries
- **Real Schema**: Column definitions and data types from information_schema
- **Better Query Parsing**: Recognizes "show me data from X" and "values in X"

### ✅ MongoDB Improvements
- **Honest Status**: No more fake data - clearly states when MCP server is not connected
- **Clear Messages**: Explains what's needed to get real MongoDB information
- **No Misleading Data**: Won't pretend to have information it doesn't have

### ✅ Query Analysis Improvements
- **Better Pattern Matching**: Recognizes more natural language patterns
- **Smarter Routing**: Better determination of target database and operation type
- **Fallback Handling**: Graceful degradation when real data can't be retrieved

## 🚀 How to Test

1. **Start the system** using `./start-system.sh` or manually
2. **Open the interface** at `http://localhost:8080/database-interface.html`
3. **Try the test queries** above
4. **Verify responses** match expected behavior

## 🔍 Expected Behavior

- **PostgreSQL queries** should return real data from your dvdrental database
- **MongoDB queries** should clearly state that MCP server connection is required
- **Error messages** should be helpful and explain what went wrong
- **Response times** should be reasonable (1-3 seconds for most queries)

## 🐛 Troubleshooting

If queries don't work as expected:

1. **Check database connection**: Ensure PostgreSQL is running and accessible
2. **Check Spring Boot logs**: Look for database connection errors
3. **Verify table names**: Some tables might not exist in your dvdrental database
4. **Check permissions**: Ensure the postgres user has access to all tables

## 📊 Sample Expected Output

### PostgreSQL Table Data Query:
```
✅ Query executed successfully!
🤖 Query Processed Successfully

📝 Your Query: "show me data from customer table"

✅ MCP Server Response:
PostgreSQL table data retrieved using real database query

📊 Data:
{
  "table": "customer",
  "sample_data": [
    {
      "customer_id": 1,
      "store_id": 1,
      "first_name": "Mary",
      "last_name": "Smith",
      "email": "mary.smith@sakilacustomer.org"
    }
  ],
  "row_count": 10,
  "note": "Showing first 10 rows. Use LIMIT clause for more rows."
}
```

### MongoDB Status Query:
```
✅ Query executed successfully!
🤖 Query Processed Successfully

📝 Your Query: "what is the current state of mongo cluster"

✅ MCP Server Response:
MongoDB cluster status - MCP server connection required

📊 Data:
{
  "cluster": "cluster0-shard-00-00",
  "status": "Status unknown - MongoDB MCP server not connected",
  "note": "To get real MongoDB status, ensure MongoDB MCP server is running and connected"
}
```
