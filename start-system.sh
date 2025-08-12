#!/bin/bash

echo "🚀 Starting MCP Database Integration System..."
echo "=============================================="

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "❌ Node.js is not installed. Please install Node.js 16+ first."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17+ first."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven first."
    exit 1
fi

echo "✅ Prerequisites check passed"
echo ""

# Start MCP Bridge Service
echo "🔧 Starting MCP Bridge Service..."
cd "$(dirname "$0")"

# Install Node.js dependencies if package.json exists
if [ -f "package.json" ]; then
    echo "📦 Installing Node.js dependencies..."
    npm install
fi

# Start MCP Bridge in background
echo "🚀 Starting MCP Bridge on port 3000..."
node mcp-bridge.js &
MCP_BRIDGE_PID=$!

# Wait a moment for MCP Bridge to start
sleep 3

# Check if MCP Bridge is running
if curl -s http://localhost:3000/health > /dev/null; then
    echo "✅ MCP Bridge Service started successfully"
else
    echo "❌ Failed to start MCP Bridge Service"
    kill $MCP_BRIDGE_PID 2>/dev/null
    exit 1
fi

echo ""

# Start Spring Boot Application
echo "☕ Starting Spring Boot Application..."
echo "🚀 Starting on port 8080..."

# Start Spring Boot in background
mvn spring-boot:run > spring-boot.log 2>&1 &
SPRING_BOOT_PID=$!

# Wait for Spring Boot to start
echo "⏳ Waiting for Spring Boot to start..."
for i in {1..30}; do
    if curl -s http://localhost:8080/api/mcp/health > /dev/null 2>&1; then
        echo "✅ Spring Boot Application started successfully"
        break
    fi
    if [ $i -eq 30 ]; then
        echo "❌ Spring Boot Application failed to start within 30 seconds"
        echo "📋 Check spring-boot.log for details"
        kill $MCP_BRIDGE_PID 2>/dev/null
        kill $SPRING_BOOT_PID 2>/dev/null
        exit 1
    fi
    echo "⏳ Still waiting... ($i/30)"
    sleep 1
done

echo ""
echo "🎉 System is now running!"
echo "=============================================="
echo "📱 Frontend Interface: http://localhost:8080/database-interface.html"
echo "🔧 API Endpoints: http://localhost:8080/api/mcp/*"
echo "🌉 MCP Bridge Health: http://localhost:3000/health"
echo ""
echo "💡 Try these example queries:"
echo "   • backup my database"
echo "   • what is the current state of postgres"
echo "   • show me all tables in mongo"
echo "   • analyze the dvdrental database structure"
echo ""
echo "🛑 To stop the system, press Ctrl+C"
echo ""

# Function to cleanup on exit
cleanup() {
    echo ""
    echo "🛑 Shutting down system..."
    kill $MCP_BRIDGE_PID 2>/dev/null
    kill $SPRING_BOOT_PID 2>/dev/null
    echo "✅ System stopped"
    exit 0
}

# Set trap to cleanup on exit
trap cleanup SIGINT SIGTERM

# Keep script running
wait
