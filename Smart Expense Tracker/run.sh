#!/bin/bash

# Smart Expense Tracker - Run Script
# This script runs the compiled application

if [ ! -d "bin" ]; then
    echo "Project not built. Please run ./build.sh first"
    exit 1
fi

echo "Starting Smart Expense Tracker..."
java -cp "bin:lib/*" Main
