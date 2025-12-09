# Smart Expense Tracker

A comprehensive Java GUI application for tracking personal expenses with charts, reports, and data export capabilities.

## Features

### Core Functionality
- ✅ **Add, Edit, Delete Expenses** - Full CRUD operations for expense management
- 📊 **Dashboard** - Overview with total expenses and transaction count
- 📝 **Expense List** - Tabular view of all expenses with sorting
- ➕ **Quick Add** - Easy-to-use form for adding new expenses

### Categorization
- 🍔 Food
- ✈️ Travel
- 🎬 Entertainment
- 🛍️ Shopping
- 🏥 Healthcare
- 📄 Bills
- 📚 Education
- 📦 Others

### Reports & Analytics
- 📈 **Monthly Spending Charts** - Bar chart showing expenses over time
- 🥧 **Category Pie Chart** - Visual breakdown of spending by category
- 📉 **Trend Line Chart** - Track spending trends over months
- 📋 **Summary Reports** - Detailed text summaries with percentages

### Data Management
- 💾 **JSON Storage** - Automatic save/load of all expense data
- 📤 **Export to CSV** - Export expenses for Excel/spreadsheet analysis
- 📤 **Export to JSON** - Export in JSON format for data portability

### User Interface
- 🌙 **Dark Mode** - Toggle between light and dark themes
- 🎨 **Color-coded Categories** - Each category has its own color
- 📱 **Responsive Design** - Clean and intuitive interface
- 🔍 **Easy Navigation** - Sidebar navigation between different views

## Technologies Used

- **Java 11+** - Core programming language
- **Swing** - GUI framework
- **JFreeChart** - Chart generation library
- **Gson** - JSON serialization/deserialization
- **Maven** - Build and dependency management

## Object-Oriented Design

### Classes

#### Models
- **User** - Represents application user
- **Expense** - Expense entity with all details
- **Category** - Expense categories with colors and icons

#### Managers
- **ExpenseManager** - Business logic for expense operations
- **FileStorage** - Data persistence (JSON, CSV export)

#### UI Components
- **MainFrame** - Main application window with navigation
- **DashboardPanel** - Overview and statistics
- **ExpenseListPanel** - List view with edit/delete
- **AddExpensePanel** - Form for adding expenses
- **ReportsPanel** - Charts and reports view

#### Utilities
- **ReportGenerator** - Creates charts and summary reports

## Installation & Running

### Prerequisites
- Java 11 or higher
- (Optional) Maven 3.6+ for Maven build method

### Method 1: Using Build Scripts (Recommended - No Maven Required)

The project includes build scripts that automatically download dependencies:

```bash
# Navigate to project directory
cd "Smart Expense Tracker"

# Make scripts executable (first time only)
chmod +x build.sh run.sh

# Build the project (downloads dependencies and compiles)
./build.sh

# Run the application
./run.sh
```

### Method 2: Using Maven (if installed)

```bash
# Navigate to project directory
cd "Smart Expense Tracker"

# Build the project
mvn clean package

# Run the application
java -cp "target/smart-expense-tracker-1.0.0.jar:target/lib/*" Main
```

Or simply:
```bash
mvn clean compile exec:java -Dexec.mainClass="Main"
```

## Project Structure

```
Smart Expense Tracker/
├── src/
│   ├── Main.java                    # Application entry point
│   ├── models/
│   │   ├── User.java               # User model
│   │   ├── Expense.java            # Expense model
│   │   └── Category.java           # Category model
│   ├── managers/
│   │   └── ExpenseManager.java     # Business logic
│   ├── storage/
│   │   └── FileStorage.java        # Data persistence
│   ├── reports/
│   │   └── ReportGenerator.java    # Chart generation
│   └── ui/
│       ├── MainFrame.java          # Main window
│       └── panels/
│           ├── DashboardPanel.java
│           ├── ExpenseListPanel.java
│           ├── AddExpensePanel.java
│           └── ReportsPanel.java
├── data/                            # Data storage directory
│   ├── expenses.json               # Stored expenses
│   └── categories.json             # Custom categories
├── pom.xml                         # Maven configuration
└── README.md                       # This file
```

## Usage Guide

### Adding an Expense
1. Click **"➕ Add Expense"** in the sidebar
2. Fill in the expense details:
   - Description (e.g., "Lunch at restaurant")
   - Amount (e.g., 25.50)
   - Category (select from dropdown)
   - Date (use date picker)
   - Notes (optional)
3. Click **"Add Expense"**

### Viewing Expenses
1. Click **"📝 Expenses"** in the sidebar
2. View all expenses in a table format
3. Select an expense and click **"Edit"** to modify
4. Select an expense and click **"Delete"** to remove

### Viewing Reports
1. Click **"📈 Reports"** in the sidebar
2. Select chart type from dropdown:
   - Category Pie Chart
   - Monthly Bar Chart
   - Trend Line Chart
3. View summary statistics below the chart

### Exporting Data
1. Go to **File → Export to CSV** or **Export to JSON**
2. Choose location and filename
3. Click **Save**

### Enabling Dark Mode
1. Go to **View → Dark Mode** (check the option)
2. Toggle on/off as needed

## Data Storage

All data is automatically saved to the `data/` directory:
- `expenses.json` - All expense records
- `categories.json` - Category definitions

Data is loaded on startup and saved on exit.

## Future Enhancements

- 🔐 User authentication and multi-user support
- 💳 Budget setting and tracking
- 🔔 Spending alerts and notifications
- 📧 Email report generation
- 🌐 Cloud sync capabilities
- 📱 Mobile companion app
- 🔍 Advanced filtering and search
- 📊 More chart types and analytics

## License

MIT License - Feel free to use and modify for your needs.

## Author

Created as a demonstration of Object-Oriented Programming principles in Java with GUI.
