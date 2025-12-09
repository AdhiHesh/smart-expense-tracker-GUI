#!/bin/bash

# Smart Expense Tracker - Build Script
# This script downloads dependencies and compiles the project without Maven

echo "Building Smart Expense Tracker..."

# Create directories
mkdir -p lib
mkdir -p bin
mkdir -p data

# Download dependencies if not present
echo "Checking dependencies..."

# Gson
if [ ! -f "lib/gson-2.10.1.jar" ]; then
    echo "Downloading Gson..."
    curl -L -o lib/gson-2.10.1.jar "https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar"
fi

# JFreeChart
if [ ! -f "lib/jfreechart-1.5.4.jar" ]; then
    echo "Downloading JFreeChart..."
    curl -L -o lib/jfreechart-1.5.4.jar "https://repo1.maven.org/maven2/org/jfree/jfreechart/1.5.4/jfreechart-1.5.4.jar"
fi

# JCommon (required by JFreeChart)
if [ ! -f "lib/jcommon-1.0.24.jar" ]; then
    echo "Downloading JCommon..."
    curl -L -o lib/jcommon-1.0.24.jar "https://repo1.maven.org/maven2/org/jfree/jcommon/1.0.24/jcommon-1.0.24.jar"
fi

echo "Compiling source files..."

# Compile all Java files
find src -name "*.java" -print0 | xargs -0 javac -d bin -cp "lib/*" 2>&1

if [ $? -eq 0 ]; then
    echo "Build successful!"
    echo "To run the application, use: ./run.sh"
else
    echo "Build failed!"
    exit 1
fi
