#!/bin/bash
echo "Testing compilation..."
javac -d bin -cp "lib/*" src/models/*.java src/managers/*.java src/storage/*.java src/reports/*.java src/ui/*.java src/ui/panels/*.java src/Main.java
if [ $? -eq 0 ]; then
    echo "✅ All files compile successfully!"
else
    echo "❌ Compilation errors found"
    exit 1
fi
