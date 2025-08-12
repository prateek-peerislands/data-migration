@echo off
echo 🚀 Starting MCP Database Integration System...
echo ==============================================

REM Check if Node.js is installed
where node >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Node.js is not installed. Please install Node.js 16+ first.
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed. Please install Java 17+ first.
    pause
    exit /b 1
)

REM Check if Maven is installed
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven is not installed. Please install Maven first.
    pause
    exit /b 1
)

echo ✅ Prerequisites check passed
echo.

REM Start MCP Bridge Service
echo 🔧 Starting MCP Bridge Service...

REM Install Node.js dependencies if package.json exists
if exist "package.json" (
    echo 📦 Installing Node.js dependencies...
    npm install
)

REM Start MCP Bridge in background
echo 🚀 Starting MCP Bridge on port 3000...
start /B node mcp-bridge.js

REM Wait a moment for MCP Bridge to start
timeout /t 3 /nobreak >nul

REM Check if MCP Bridge is running
curl -s http://localhost:3000/health >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ MCP Bridge Service started successfully
) else (
    echo ❌ Failed to start MCP Bridge Service
    pause
    exit /b 1
)

echo.

REM Start Spring Boot Application
echo ☕ Starting Spring Boot Application...
echo 🚀 Starting on port 8080...

REM Start Spring Boot in background
start /B mvn spring-boot:run

REM Wait for Spring Boot to start
echo ⏳ Waiting for Spring Boot to start...
for /L %%i in (1,1,30) do (
    curl -s http://localhost:8080/api/mcp/health >nul 2>&1
    if !errorlevel! equ 0 (
        echo ✅ Spring Boot Application started successfully
        goto :success
    )
    echo ⏳ Still waiting... (%%i/30)
    timeout /t 1 /nobreak >nul
)

echo ❌ Spring Boot Application failed to start within 30 seconds
pause
exit /b 1

:success
echo.
echo 🎉 System is now running!
echo ==============================================
echo 📱 Frontend Interface: http://localhost:8080/database-interface.html
echo 🔧 API Endpoints: http://localhost:8080/api/mcp/*
echo 🌉 MCP Bridge Health: http://localhost:3000/health
echo.
echo 💡 Try these example queries:
echo    • backup my database
echo    • what is the current state of postgres
echo    • show me all tables in mongo
echo    • analyze the dvdrental database structure
echo.
echo 🛑 To stop the system, close this window
echo.
pause
