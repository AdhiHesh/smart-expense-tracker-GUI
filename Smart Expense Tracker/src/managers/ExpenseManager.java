package managers;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import models.Category;
import models.Expense;

/**
 * Manages all expense operations
 */
public class ExpenseManager {
    private List<Expense> expenses;
    private List<Category> categories;
    
    public ExpenseManager() {
        this.expenses = new ArrayList<>();
        this.categories = new ArrayList<>(Arrays.asList(Category.getDefaultCategories()));
    }
    
    // Expense operations
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }
    
    public void updateExpense(String id, Expense updatedExpense) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId().equals(id)) {
                updatedExpense.setId(id);
                expenses.set(i, updatedExpense);
                break;
            }
        }
    }
    
    public void deleteExpense(String id) {
        expenses.removeIf(expense -> expense.getId().equals(id));
    }
    
    public Expense getExpenseById(String id) {
        return expenses.stream()
                .filter(expense -> expense.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }
    
    public List<Expense> getExpensesByDateRange(LocalDate start, LocalDate end) {
        return expenses.stream()
                .filter(expense -> !expense.getDate().isBefore(start) && !expense.getDate().isAfter(end))
                .collect(Collectors.toList());
    }
    
    public List<Expense> getExpensesByCategory(Category category) {
        return expenses.stream()
                .filter(expense -> expense.getCategory().equals(category))
                .collect(Collectors.toList());
    }
    
    public List<Expense> getExpensesByMonth(YearMonth month) {
        return expenses.stream()
                .filter(expense -> YearMonth.from(expense.getDate()).equals(month))
                .collect(Collectors.toList());
    }
    
    // Statistics
    public double getTotalExpenses() {
        return expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
    
    public double getTotalExpensesByCategory(Category category) {
        return expenses.stream()
                .filter(expense -> expense.getCategory().equals(category))
                .mapToDouble(Expense::getAmount)
                .sum();
    }
    
    public double getTotalExpensesByMonth(YearMonth month) {
        return getExpensesByMonth(month).stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
    
    public Map<Category, Double> getExpensesByCategories() {
        Map<Category, Double> categoryTotals = new HashMap<>();
        for (Category category : categories) {
            double total = getTotalExpensesByCategory(category);
            if (total > 0) {
                categoryTotals.put(category, total);
            }
        }
        return categoryTotals;
    }
    
    public Map<YearMonth, Double> getMonthlyExpenses(int months) {
        Map<YearMonth, Double> monthlyTotals = new LinkedHashMap<>();
        YearMonth currentMonth = YearMonth.now();
        
        for (int i = months - 1; i >= 0; i--) {
            YearMonth month = currentMonth.minusMonths(i);
            double total = getTotalExpensesByMonth(month);
            monthlyTotals.put(month, total);
        }
        
        return monthlyTotals;
    }
    
    // Category operations
    public List<Category> getCategories() {
        return new ArrayList<>(categories);
    }
    
    public void addCategory(Category category) {
        categories.add(category);
    }
    
    public Category getCategoryById(String id) {
        return categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    // Bulk operations
    public void setExpenses(List<Expense> expenses) {
        this.expenses = new ArrayList<>(expenses);
    }
    
    public void setCategories(List<Category> categories) {
        this.categories = new ArrayList<>(categories);
    }
    
    public void clear() {
        expenses.clear();
    }
}
