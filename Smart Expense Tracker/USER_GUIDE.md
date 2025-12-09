# 💰 Smart Expense Tracker - User Guide

A complete guide to using your modern expense tracking application.

## 📋 Table of Contents

1. [Getting Started](#getting-started)
2. [Dashboard Overview](#dashboard-overview)
3. [Adding Expenses](#adding-expenses)
4. [Managing Expenses](#managing-expenses)
5. [Viewing Reports](#viewing-reports)
6. [Exporting Data](#exporting-data)
7. [Settings & Dark Mode](#settings--dark-mode)
8. [Tips & Best Practices](#tips--best-practices)

---

## 🚀 Getting Started

### Installation

1. **Download** the Smart Expense Tracker folder to your computer
2. **Open Terminal** and navigate to the folder:
   ```bash
   cd "path/to/Smart Expense Tracker"
   ```

3. **Build the application** (first time only):
   ```bash
   ./build.sh
   ```
   This will automatically download all required dependencies.

4. **Run the application**:
   ```bash
   ./run.sh
   ```

### First Launch

When you first open the app, you'll see:
- **Clean Dashboard** with no expenses
- **Sidebar Navigation** on the left with icons
- **Modern UI** with indigo and pink color scheme

---

## 🏠 Dashboard Overview

The Dashboard is your home screen showing:

### 📊 Quick Stats
Three stat cards at the top display:
- **💰 Total Expenses**: Sum of all your spending
- **📊 Transactions**: Total number of expense entries
- **📈 Average**: Average amount per transaction

### 🏷️ Category Breakdown
Below the stats, see all your categories with:
- **Colored Icons**: Each category has a unique emoji and color
- **Amount Spent**: Total amount in each category
- **Progress Bars**: Visual representation of spending distribution
- **Percentage**: What portion of total spending

### Empty State
When you have no expenses, you'll see:
- Friendly message: "No expenses yet!"
- Prompt to add your first expense

---

## ➕ Adding Expenses

### Step-by-Step Guide

1. **Navigate to Add Expense**
   - Click **"Add Expense"** (📝) in the sidebar
   - You'll see a clean form card

2. **Fill in Details**

   **Description** (Required)
   - What did you spend money on?
   - Examples: "Lunch at restaurant", "Gas station", "Netflix subscription"
   - Click the field and type your description

   **Amount** (Required)
   - How much did you spend?
   - Enter numbers only (e.g., 45.50)
   - No currency symbol needed

   **Category** (Required)
   - Click the dropdown to see all categories
   - Each category shows with a colored icon
   - Choose the most appropriate one:
     - 🍕 **Food**: Meals, groceries, restaurants
     - 🚗 **Transport**: Gas, taxi, public transit
     - 🎬 **Entertainment**: Movies, games, streaming
     - 🛍️ **Shopping**: Clothes, gadgets, general shopping
     - 💡 **Bills**: Rent, utilities, subscriptions
     - 🏥 **Health**: Medical, pharmacy, fitness
     - 📚 **Education**: Books, courses, tuition
     - 📦 **Other**: Anything else

   **Date**
   - Defaults to today's date
   - Click to open date picker
   - Select any past or present date

   **Notes** (Optional)
   - Add extra details or context
   - Examples: "Split with John", "Business expense", "Birthday gift"

3. **Save the Expense**
   - Click **"Add Expense"** button (purple)
   - You'll see a confirmation message
   - Expense is automatically saved!

4. **Clear Form** (Optional)
   - Click **"Clear"** button to reset all fields
   - Start fresh with a new expense

### Quick Tips
- ✅ Use **descriptive names** for easier tracking
- ✅ Pick the **right category** for accurate reports
- ✅ Add **notes** for important context
- ✅ Don't forget to click **"Add Expense"** to save!

---

## 💳 Managing Expenses

### Viewing All Expenses

1. Click **"Expense List"** (💳) in the sidebar
2. See all your expenses in a beautiful table with:
   - 📅 **Date**: When the expense occurred
   - 📝 **Description**: What you spent on
   - 🏷️ **Category**: Which category
   - 💰 **Amount**: How much you spent
   - 📄 **Notes**: Additional details

### Table Features
- **Sortable**: Click column headers to sort
- **Alternating Rows**: Easier to read
- **Large Rows**: 45px height for comfortable viewing
- **Clean Design**: No grid lines, modern look

### Editing an Expense

1. **Select** the expense by clicking its row
2. Click **"Edit"** (✏️) button at the top
3. A modern dialog opens with the expense details
4. **Modify** any field:
   - Description
   - Amount
   - Category
   - Notes (date cannot be changed)
5. Click **"Save Changes"** (green button)
6. Or click **"Cancel"** to abort

### Deleting an Expense

1. **Select** the expense by clicking its row
2. Click **"Delete"** (🗑️) button at the top
3. **Confirm** the deletion in the popup
4. Expense is permanently removed

### Refreshing the List

- Click **"Refresh"** (🔄) button to reload
- Useful after bulk changes or imports

---

## 📊 Viewing Reports

### Accessing Reports

1. Click **"Reports"** (📊) in the sidebar
2. See two sections:
   - **Top**: Interactive charts
   - **Bottom**: Text summary

### Chart Types

Use the dropdown to switch between:

**📊 Category Pie Chart**
- Shows spending distribution by category
- Each slice represents a category
- Colored to match category colors
- Percentages displayed on slices
- Great for understanding where money goes

**📈 Monthly Bar Chart**
- Compares spending across last 6 months
- Each bar represents one month
- Height shows total spending
- Hover to see exact amounts
- Identify high-spending months

**📉 Trend Line Chart**
- Shows spending trend over time
- Line connects monthly totals
- See if spending is increasing/decreasing
- Useful for budget planning
- Spot patterns and anomalies

### Summary Report

Below the chart, see detailed statistics:
- **Total Expenses**: Overall sum
- **Category Breakdown**: Amount and percentage per category
- **Top Categories**: Which categories you spend most on
- **Transaction Count**: Number of expenses per category

### No Data Message

If you haven't added expenses yet:
- Friendly emoji and message appear
- Prompts you to add expenses first

---

## 💾 Exporting Data

### Why Export?
- **Backup**: Keep a copy of your data
- **Analysis**: Use in Excel or other tools
- **Sharing**: Send to accountant or partner
- **Records**: Keep for tax purposes

### Export Formats

**CSV (Comma-Separated Values)**
- Opens in Excel, Google Sheets, Numbers
- Good for spreadsheet analysis
- Includes all expense details
- Standard format, widely compatible

**JSON (JavaScript Object Notation)**
- Technical format for developers
- Can re-import into other systems
- Preserves all data structure
- Includes category colors and icons

### How to Export

1. Go to **Dashboard** or **Reports**
2. Look for **Export** buttons
3. Click **"Export to CSV"** or **"Export to JSON"**
4. File saved to the application folder
5. Open file or move to desired location

### What's Included?

All exports contain:
- Date of expense
- Description
- Amount
- Category name
- Notes
- Category color (JSON only)
- Category icon (JSON only)

---

## ⚙️ Settings & Dark Mode

### Accessing Settings

1. Click **"Settings"** (⚙️) icon in the sidebar
2. See available preferences

### Dark Mode

**Why Use Dark Mode?**
- Easier on the eyes in low light
- Reduces eye strain
- Saves battery (on some screens)
- Modern, sleek appearance

**How to Toggle:**
1. Go to Settings
2. Find **"Dark Mode"** toggle
3. Switch ON for dark theme
4. Switch OFF for light theme
5. Theme changes instantly!

**Dark Mode Features:**
- Dark backgrounds (dark gray/black)
- Light text for readability
- Adjusted chart colors
- Inverted color scheme throughout
- All panels update automatically

---

## 💡 Tips & Best Practices

### For Accurate Tracking

1. **Enter Expenses Immediately**
   - Don't wait days to add expenses
   - Fresh memory = accurate details
   - Use your phone to note and add later if needed

2. **Be Specific with Descriptions**
   - ❌ "Food" → ✅ "Lunch at Chipotle"
   - ❌ "Transport" → ✅ "Uber to airport"
   - ❌ "Shopping" → ✅ "New running shoes"

3. **Use Categories Consistently**
   - Always put groceries in Food
   - Gas stations in Transport
   - Streaming services in Entertainment
   - Consistency = better reports

4. **Add Helpful Notes**
   - "Split with Sarah"
   - "Business expense - reimbursable"
   - "Birthday gift for Mom"
   - "Annual subscription renewal"

### For Better Analysis

5. **Check Dashboard Weekly**
   - Review your spending patterns
   - Identify problem categories
   - Celebrate if under budget!

6. **Use Charts Monthly**
   - View trend chart at month-end
   - Compare to previous months
   - Adjust budget accordingly

7. **Export Regularly**
   - Monthly export for backup
   - Quarterly export for taxes
   - Annual export for records

8. **Review Category Breakdown**
   - Which category is highest?
   - Any surprises?
   - Need to cut back anywhere?

### For Organization

9. **Clean Up Old Entries**
   - Review and delete test entries
   - Fix any incorrect amounts
   - Update categories if needed

10. **Set a Routine**
    - Daily: Add expenses
    - Weekly: Review dashboard
    - Monthly: Check reports & export
    - Quarterly: Analyze trends

### Workflow Example

**Daily Routine:**
- Morning: Add coffee ($5.50, Food)
- Lunch: Add meal ($12.00, Food)
- Evening: Add any other expenses

**Weekly Review:**
- Friday: Check dashboard
- Review total for the week
- Compare to budget
- Plan for next week

**Monthly Analysis:**
- Last day: Check all reports
- View trend chart
- Export to CSV
- Plan next month's budget

---

## ❓ Common Questions

### How do I backup my data?
Export to CSV or JSON regularly. Your data is also saved in `expense_data.json` in the app folder.

### Can I edit the date of an expense?
Currently, dates cannot be edited. Delete and re-add the expense if needed.

### How do I add a new category?
Categories are pre-configured. Use "Other" for items that don't fit existing categories.

### Where is my data stored?
In `expense_data.json` in the Smart Expense Tracker folder. It auto-saves after every change.

### Can I use this on multiple computers?
Yes! Copy the entire folder (including expense_data.json) to another computer.

### What if I accidentally delete an expense?
Unfortunately, there's no undo. Export regularly to have backups.

### Charts aren't showing?
Make sure you have expenses added. Charts need data to display.

### Can I change category colors?
Not through the UI. Colors are defined in the code for consistency.

---

## 🎯 Sample Usage Scenarios

### Scenario 1: Student Budget Tracking

**Goal**: Track college expenses and stay within budget

**Routine:**
1. Add all expenses daily (meals, books, entertainment)
2. Check dashboard every Friday
3. View category pie chart to see where money goes
4. Reduce spending in top categories if over budget

### Scenario 2: Family Expense Management

**Goal**: Track household spending for both partners

**Routine:**
1. Each person adds their expenses
2. Weekly review together at dashboard
3. Monthly trend analysis
4. Export for tax records quarterly

### Scenario 3: Business Expense Tracking

**Goal**: Track reimbursable work expenses

**Routine:**
1. Add expenses with "Business" in notes
2. Use appropriate categories
3. Export to CSV at month-end
4. Submit to accounting for reimbursement

### Scenario 4: Vacation Budget

**Goal**: Track spending during a trip

**Routine:**
1. Add every purchase (meals, tickets, souvenirs)
2. Use Shopping for souvenirs
3. Use Entertainment for activities
4. Review total daily to stay on budget
5. Export after trip for records

---

## 🆘 Troubleshooting

### App Won't Start
```bash
# Check Java version (needs 11+)
java -version

# Rebuild
./build.sh

# Run again
./run.sh
```

### Data Not Saving
- Check file permissions in the app folder
- Look for `expense_data.json` file
- Make sure disk isn't full

### Charts Not Appearing
- Add some expenses first
- Try selecting different chart types
- Check that you have data for the time period

### Numbers Look Wrong
- Check that you entered amounts correctly
- Look for duplicate entries
- Verify categories are correct

---

## 🎉 You're Ready!

You now know everything about using Smart Expense Tracker!

**Start by:**
1. ➕ Adding your first expense
2. 🏠 Checking the dashboard
3. 📊 Viewing a chart
4. 💾 Exporting your data

**Happy expense tracking! 💰✨**

---

*For technical issues or feature requests, check the README.md file in the application folder.*
