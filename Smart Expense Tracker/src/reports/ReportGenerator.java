package reports;

import models.Expense;
import models.Category;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Generates various reports and charts
 */
public class ReportGenerator {
    
    /**
     * Creates a pie chart for expenses by category
     */
    public static ChartPanel createCategoryPieChart(Map<Category, Double> categoryExpenses, boolean isDarkMode) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        
        for (Map.Entry<Category, Double> entry : categoryExpenses.entrySet()) {
            dataset.setValue(entry.getKey().getName(), entry.getValue());
        }
        
        JFreeChart chart = ChartFactory.createPieChart(
                "Expenses by Category",
                dataset,
                true,
                true,
                false
        );
        
        // Apply dark mode if enabled
        if (isDarkMode) {
            applyDarkModeToChart(chart);
        }
        
        // Customize colors based on categories
        PiePlot plot = (PiePlot) chart.getPlot();
        for (Map.Entry<Category, Double> entry : categoryExpenses.entrySet()) {
            plot.setSectionPaint(entry.getKey().getName(), entry.getKey().getColor());
        }
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 400));
        
        return chartPanel;
    }
    
    /**
     * Creates a bar chart for monthly expenses
     */
    public static ChartPanel createMonthlyBarChart(Map<YearMonth, Double> monthlyExpenses, boolean isDarkMode) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");
        
        for (Map.Entry<YearMonth, Double> entry : monthlyExpenses.entrySet()) {
            dataset.addValue(entry.getValue(), "Expenses", entry.getKey().format(formatter));
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Expenses",
                "Month",
                "Amount ($)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        // Apply dark mode if enabled
        if (isDarkMode) {
            applyDarkModeToChart(chart);
        }
        
        CategoryPlot plot = chart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, new Color(65, 105, 225));
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        
        return chartPanel;
    }
    
    /**
     * Creates a line chart for expense trends
     */
    public static ChartPanel createTrendLineChart(Map<YearMonth, Double> monthlyExpenses, boolean isDarkMode) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");
        
        for (Map.Entry<YearMonth, Double> entry : monthlyExpenses.entrySet()) {
            dataset.addValue(entry.getValue(), "Expenses", entry.getKey().format(formatter));
        }
        
        JFreeChart chart = ChartFactory.createLineChart(
                "Expense Trends",
                "Month",
                "Amount ($)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        // Apply dark mode if enabled
        if (isDarkMode) {
            applyDarkModeToChart(chart);
        }
        
        CategoryPlot plot = chart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, new Color(60, 179, 113));
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        
        return chartPanel;
    }
    
    /**
     * Applies dark mode styling to chart
     */
    private static void applyDarkModeToChart(JFreeChart chart) {
        Color darkBackground = new Color(45, 45, 45);
        Color darkForeground = new Color(220, 220, 220);
        
        chart.setBackgroundPaint(darkBackground);
        chart.getTitle().setPaint(darkForeground);
        
        if (chart.getPlot() instanceof PiePlot) {
            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setBackgroundPaint(darkBackground);
            plot.setOutlinePaint(darkForeground);
            plot.setLabelBackgroundPaint(darkBackground);
            plot.setLabelPaint(darkForeground);
        } else if (chart.getPlot() instanceof CategoryPlot) {
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setBackgroundPaint(darkBackground);
            plot.setOutlinePaint(darkForeground);
            plot.setRangeGridlinePaint(Color.GRAY);
            plot.setDomainGridlinePaint(Color.GRAY);
            plot.getDomainAxis().setLabelPaint(darkForeground);
            plot.getDomainAxis().setTickLabelPaint(darkForeground);
            plot.getRangeAxis().setLabelPaint(darkForeground);
            plot.getRangeAxis().setTickLabelPaint(darkForeground);
        }
        
        if (chart.getLegend() != null) {
            chart.getLegend().setBackgroundPaint(darkBackground);
            chart.getLegend().setItemPaint(darkForeground);
        }
    }
    
    /**
     * Generates a summary report
     */
    public static String generateSummaryReport(List<Expense> expenses, Map<Category, Double> categoryExpenses) {
        StringBuilder report = new StringBuilder();
        report.append("EXPENSE SUMMARY REPORT\n");
        report.append("=".repeat(50)).append("\n\n");
        
        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        report.append(String.format("Total Expenses: $%.2f\n", total));
        report.append(String.format("Number of Transactions: %d\n\n", expenses.size()));
        
        report.append("Expenses by Category:\n");
        report.append("-".repeat(50)).append("\n");
        
        categoryExpenses.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .forEach(entry -> {
                    double percentage = (entry.getValue() / total) * 100;
                    report.append(String.format("%-20s: $%8.2f (%5.1f%%)\n",
                            entry.getKey().getName(),
                            entry.getValue(),
                            percentage));
                });
        
        return report.toString();
    }
}
