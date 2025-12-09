package ui.panels;

import managers.ExpenseManager;
import models.Category;
import reports.ReportGenerator;
import ui.MainFrame;
import ui.utils.ModernUI;

import javax.swing.*;
import java.awt.*;
import java.time.YearMonth;
import java.util.Map;

/**
 * Panel for viewing reports and charts
 */
public class ReportsPanel extends JPanel {
    private ExpenseManager expenseManager;
    private MainFrame mainFrame;
    private JPanel chartPanel;
    private JComboBox<String> chartTypeBox;
    private JTextArea summaryArea;
    
    public ReportsPanel(ExpenseManager expenseManager, MainFrame mainFrame) {
        this.expenseManager = expenseManager;
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout(15, 15));
        setBackground(ModernUI.BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        initializeUI();
    }
    
    private void initializeUI() {
        // Top panel with title and controls
        JPanel topPanel = new JPanel(new BorderLayout(15, 15));
        topPanel.setOpaque(false);
        
        // Title
        JLabel titleLabel = new JLabel("📊 Reports & Analytics");
        titleLabel.setFont(new Font("SF Pro Display", Font.BOLD, 28));
        titleLabel.setForeground(ModernUI.TEXT_PRIMARY);
        topPanel.add(titleLabel, BorderLayout.WEST);
        
        // Controls panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        controlPanel.setOpaque(false);
        
        JLabel chartLabel = new JLabel("Chart Type:");
        chartLabel.setFont(new Font("SF Pro Text", Font.PLAIN, 13));
        chartLabel.setForeground(ModernUI.TEXT_SECONDARY);
        controlPanel.add(chartLabel);
        
        String[] chartTypes = {"📊 Category Pie Chart", "📈 Monthly Bar Chart", "📉 Trend Line Chart"};
        chartTypeBox = new JComboBox<>(chartTypes);
        chartTypeBox.setFont(new Font("SF Pro Text", Font.PLAIN, 13));
        chartTypeBox.setPreferredSize(new Dimension(200, 35));
        chartTypeBox.addActionListener(e -> updateChart());
        controlPanel.add(chartTypeBox);
        
        topPanel.add(controlPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        // Split pane for chart and summary
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.65);
        splitPane.setDividerSize(8);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Chart panel in card
        JPanel chartCard = ModernUI.createCard();
        chartCard.setLayout(new BorderLayout());
        
        JLabel chartTitle = new JLabel("📊 Visualization");
        chartTitle.setFont(new Font("SF Pro Text", Font.BOLD, 16));
        chartTitle.setForeground(ModernUI.TEXT_PRIMARY);
        chartTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        chartCard.add(chartTitle, BorderLayout.NORTH);
        
        chartPanel = new JPanel(new BorderLayout());
        chartPanel.setOpaque(false);
        chartCard.add(chartPanel, BorderLayout.CENTER);
        
        splitPane.setTopComponent(chartCard);
        
        // Summary panel in card
        JPanel summaryCard = ModernUI.createCard();
        summaryCard.setLayout(new BorderLayout(0, 10));
        
        JLabel summaryTitle = new JLabel("📄 Summary Report");
        summaryTitle.setFont(new Font("SF Pro Text", Font.BOLD, 16));
        summaryTitle.setForeground(ModernUI.TEXT_PRIMARY);
        summaryCard.add(summaryTitle, BorderLayout.NORTH);
        
        summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("SF Mono", Font.PLAIN, 12));
        summaryArea.setBackground(new Color(248, 249, 250));
        summaryArea.setForeground(ModernUI.TEXT_PRIMARY);
        summaryArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ModernUI.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JScrollPane summaryScroll = new JScrollPane(summaryArea);
        summaryScroll.setBorder(BorderFactory.createEmptyBorder());
        summaryCard.add(summaryScroll, BorderLayout.CENTER);
        
        splitPane.setBottomComponent(summaryCard);
        
        add(splitPane, BorderLayout.CENTER);
        
        refresh();
    }
    
    public void refresh() {
        updateChart();
        updateSummary();
    }
    
    private void updateChart() {
        chartPanel.removeAll();
        
        if (expenseManager.getAllExpenses().isEmpty()) {
            // Empty state
            JPanel emptyPanel = new JPanel(new GridBagLayout());
            emptyPanel.setOpaque(false);
            
            JLabel emptyLabel = new JLabel("<html><div style='text-align: center;'>" +
                    "<div style='font-size: 48px; margin-bottom: 10px;'>📊</div>" +
                    "<div style='font-size: 16px; color: #6c757d;'>No data available</div>" +
                    "<div style='font-size: 13px; color: #adb5bd;'>Add some expenses to see charts</div>" +
                    "</div></html>");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            
            emptyPanel.add(emptyLabel);
            chartPanel.add(emptyPanel, BorderLayout.CENTER);
        } else {
            String selectedChart = (String) chartTypeBox.getSelectedItem();
            
            try {
                if (selectedChart.contains("Pie")) {
                    Map<Category, Double> categoryExpenses = expenseManager.getExpensesByCategories();
                    if (!categoryExpenses.isEmpty()) {
                        chartPanel.add(ReportGenerator.createCategoryPieChart(
                                categoryExpenses,
                                mainFrame.isDarkMode()
                        ), BorderLayout.CENTER);
                    }
                } else if (selectedChart.contains("Bar")) {
                    Map<YearMonth, Double> monthlyExpenses = expenseManager.getMonthlyExpenses(6);
                    chartPanel.add(ReportGenerator.createMonthlyBarChart(
                            monthlyExpenses,
                            mainFrame.isDarkMode()
                    ), BorderLayout.CENTER);
                } else if (selectedChart.contains("Trend")) {
                    Map<YearMonth, Double> trendData = expenseManager.getMonthlyExpenses(6);
                    chartPanel.add(ReportGenerator.createTrendLineChart(
                            trendData,
                            mainFrame.isDarkMode()
                    ), BorderLayout.CENTER);
                }
            } catch (Exception e) {
                JLabel errorLabel = new JLabel("⚠️ Error generating chart: " + e.getMessage());
                errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
                errorLabel.setForeground(ModernUI.DANGER_COLOR);
                chartPanel.add(errorLabel, BorderLayout.CENTER);
            }
        }
        
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    
    private void updateSummary() {
        Map<Category, Double> categoryExpenses = expenseManager.getExpensesByCategories();
        String summary = ReportGenerator.generateSummaryReport(
                expenseManager.getAllExpenses(),
                categoryExpenses
        );
        summaryArea.setText(summary);
    }
    
    public void applyTheme(boolean isDarkMode) {
        Color bgColor = isDarkMode ? new Color(45, 45, 45) : Color.WHITE;
        Color fgColor = isDarkMode ? new Color(220, 220, 220) : Color.BLACK;
        
        setBackground(bgColor);
        setForeground(fgColor);
        
        if (summaryArea != null) {
            summaryArea.setBackground(isDarkMode ? new Color(60, 60, 60) : Color.WHITE);
            summaryArea.setForeground(fgColor);
        }
        
        updateChart();
    }
}
