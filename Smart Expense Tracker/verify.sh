#!/bin/bash

echo "==================================================="
echo "Smart Expense Tracker - Verification Report"
echo "==================================================="
echo ""

# Check Java version
echo "✓ Java Version:"
java -version 2>&1 | head -n 1
echo ""

# Check project structure
echo "✓ Project Structure:"
[ -d "src" ] && echo "  ✓ src/ directory exists"
[ -d "bin" ] && echo "  ✓ bin/ directory exists"
[ -d "lib" ] && echo "  ✓ lib/ directory exists"
[ -d "data" ] && echo "  ✓ data/ directory exists"
echo ""

# Check dependencies
echo "✓ Dependencies:"
[ -f "lib/gson-2.10.1.jar" ] && echo "  ✓ Gson 2.10.1"
[ -f "lib/jfreechart-1.5.4.jar" ] && echo "  ✓ JFreeChart 1.5.4"
[ -f "lib/jcommon-1.0.24.jar" ] && echo "  ✓ JCommon 1.0.24"
echo ""

# Check compiled classes
echo "✓ Compiled Classes:"
[ -d "bin/models" ] && echo "  ✓ models package"
[ -d "bin/managers" ] && echo "  ✓ managers package"
[ -d "bin/storage" ] && echo "  ✓ storage package"
[ -d "bin/reports" ] && echo "  ✓ reports package"
[ -d "bin/ui" ] && echo "  ✓ ui package"
[ -f "bin/Main.class" ] && echo "  ✓ Main.class"
echo ""

# Count source files
echo "✓ Source Files:"
echo "  - Model classes: $(ls src/models/*.java 2>/dev/null | wc -l | xargs)"
echo "  - Manager classes: $(ls src/managers/*.java 2>/dev/null | wc -l | xargs)"
echo "  - Storage classes: $(ls src/storage/*.java 2>/dev/null | wc -l | xargs)"
echo "  - Report classes: $(ls src/reports/*.java 2>/dev/null | wc -l | xargs)"
echo "  - UI classes: $(ls src/ui/*.java 2>/dev/null | wc -l | xargs)"
echo "  - UI Panel classes: $(ls src/ui/panels/*.java 2>/dev/null | wc -l | xargs)"
echo ""

# Test compilation
echo "✓ Compilation Test:"
javac -d bin -cp "lib/*" src/models/*.java src/managers/*.java src/storage/*.java src/reports/*.java src/ui/*.java src/ui/panels/*.java src/Main.java 2>&1
if [ $? -eq 0 ]; then
    echo "  ✓ All files compile successfully!"
else
    echo "  ✗ Compilation failed"
    exit 1
fi
echo ""

# Check for Main class
echo "✓ Main Entry Point:"
if [ -f "bin/Main.class" ]; then
    echo "  ✓ Main.class exists and ready to run"
else
    echo "  ✗ Main.class not found"
    exit 1
fi
echo ""

echo "==================================================="
echo "✅ All checks passed! Application is ready to run."
echo "==================================================="
echo ""
echo "To run the application:"
echo "  ./run.sh"
echo ""
