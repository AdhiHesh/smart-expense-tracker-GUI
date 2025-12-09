package ui.panels;

import java.awt.*;
import java.util.Map;
import javax.swing.*;
import managers.ExpenseManager;
import models.Category;
import ui.MainFrame;
import ui.utils.ModernUI;

/**
 * Modern dashboard panel showing overview and statistics
 */
public class DashboardPanel extends JPanel {
    private ExpenseManager expenseManager;
    private MainFrame mainFrame;
    private JLabel totalLabel;
    private JLabel countLabel;
    private JLabel avgLabel;
    private JPanel categoryPanel;
    
    public DashboardPanel(ExpenseManager expenseManager, MainFrame mainFrame) {
        this.expenseManager = expenseManager;
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout(20, 20));
        setBackground(ModernUI.BACKGROUND_LIGHT);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        initializeUI();
    }
    
    private void initializeUI() {
        // Title section
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ModernUI.BACKGROUND_LIGHT);
        
        JLabel titleLabel = ModernUI.createModernLabel("Dashboard", 28, true);
        JLabel subtitleLabel = ModernUI.createModernLabel("Overview of your expenses", 14, false);
        subtitleLabel.setForeground(ModernUI.TEXT_SECONDARY_LIGHT);
        
        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setBackground(ModernUI.BACKGROUND_LIGHT);
        titleBox.add(titleLabel);
        titleBox.add(Box.createVerticalStrut(5));
        titleBox.add(subtitleLabel);
        
        headerPanel.add(titleBox, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);
        
        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ModernUI.BACKGROUND_LIGHT);
        
        // Statistics cards panel
        JPanel statsPanel = createStatsPanel();
        contentPanel.add(statsPanel);
        contentPanel.add(Box.createVerticalStrut(25));
        
        // Category breakdown panel
        JPanel categoryCard = ModernUI.createCard();
        categoryCard.setLayout(new BorderLayout(0, 15));
        
        JLabel categoryTitle = ModernUI.createModernLabel("Expenses by Category", 18, true);
        categoryCard.add(categoryTitle, BorderLayout.NORTH);
        
        categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(categoryPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        categoryCard.add(scrollPane, BorderLayout.CENTER);
        
        contentPanel.add(categoryCard);
        
        add(contentPanel, BorderLayout.CENTER);
        
        refresh();
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 0));
        panel.setBackground(ModernUI.BACKGROUND_LIGHT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        // Total expenses card
        totalLabel = new JLabel("$0.00");
        JPanel totalCard = ModernUI.createStatCard("Total Expenses", "$0.00", ModernUI.PRIMARY_COLOR, "💰");
        
        // Transaction count card
        countLabel = new JLabel("0");
        JPanel countCard = ModernUI.createStatCard("Transactions", "0", ModernUI.SUCCESS_COLOR, "📊");
        
        // Average expense card
        avgLabel = new JLabel("$0.00");
        JPanel avgCard = ModernUI.createStatCard("Average", "$0.00", ModernUI.ACCENT_COLOR, "📈");
        
        panel.add(totalCard);
        panel.add(countCard);
        panel.add(avgCard);
        
        return panel;
    }
    
    public void refresh() {
        // Update statistics
        double total = expenseManager.getTotalExpenses();
        int count = expenseManager.getAllExpenses().size();
        double avg = count > 0 ? total / count : 0;
        
        // Update stat cards by recreating the stats panel
        Component[] components = getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.getLayout() instanceof BoxLayout) {
                    Component[] innerComps = panel.getComponents();
                    if (innerComps.length > 0 && innerComps[0] instanceof JPanel) {
                        JPanel statsPanel = (JPanel) innerComps[0];
                        if (statsPanel.getLayout() instanceof GridLayout) {
                            // Remove old stats panel
                            panel.remove(0);
                            // Create new one with updated values
                            JPanel newStatsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
                            newStatsPanel.setBackground(ModernUI.BACKGROUND_LIGHT);
                            newStatsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
                            
                            newStatsPanel.add(ModernUI.createStatCard("Total Expenses", 
                                String.format("$%.2f", total), ModernUI.PRIMARY_COLOR, "💰"));
                            newStatsPanel.add(ModernUI.createStatCard("Transactions", 
                                String.valueOf(count), ModernUI.SUCCESS_COLOR, "📊"));
                            newStatsPanel.add(ModernUI.createStatCard("Average", 
                                String.format("$%.2f", avg), ModernUI.ACCENT_COLOR, "📈"));
                            
                            panel.add(newStatsPanel, 0);
                            break;
                        }
                    }
                }
            }
        }
        
        // Update category breakdown
        categoryPanel.removeAll();
        Map<Category, Double> categoryExpenses = expenseManager.getExpensesByCategories();
        
        if (categoryExpenses.isEmpty()) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
            emptyPanel.setBackground(Color.WHITE);
            emptyPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
            
            JLabel emptyIcon = new JLabel("📝");
            emptyIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
            emptyIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel noDataLabel = ModernUI.createModernLabel("No expenses yet", 16, false);
            noDataLabel.setForeground(ModernUI.TEXT_SECONDARY_LIGHT);
            noDataLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel hintLabel = ModernUI.createModernLabel("Start tracking by adding an expense", 12, false);
            hintLabel.setForeground(ModernUI.TEXT_SECONDARY_LIGHT);
            hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            emptyPanel.add(emptyIcon);
            emptyPanel.add(Box.createVerticalStrut(15));
            emptyPanel.add(noDataLabel);
            emptyPanel.add(Box.createVerticalStrut(5));
            emptyPanel.add(hintLabel);
            
            categoryPanel.add(emptyPanel);
        } else {
            categoryExpenses.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .forEach(entry -> {
                        JPanel itemPanel = createCategoryItem(entry.getKey(), entry.getValue(), total);
                        categoryPanel.add(itemPanel);
                        categoryPanel.add(Box.createRigidArea(new Dimension(0, 12)));
                    });
        }
        
        categoryPanel.revalidate();
        categoryPanel.repaint();
        revalidate();
        repaint();
    }
    
    private JPanel createCategoryItem(Category category, double amount, double total) {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        panel.setBackground(new Color(249, 250, 251));
        panel.setBorder(BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(new Color(229, 231, 235), 1, true),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // Icon circle
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new GridBagLayout());
        iconPanel.setPreferredSize(new Dimension(50, 50));
        iconPanel.setBackground(category.getColor());
        iconPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        JLabel iconLabel = new JLabel(category.getIcon());
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconPanel.add(iconLabel);
        
        // Category info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(249, 250, 251));
        
        JLabel nameLabel = ModernUI.createModernLabel(category.getName(), 15, true);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel amountLabel = ModernUI.createModernLabel(String.format("$%.2f", amount), 13, false);
        amountLabel.setForeground(ModernUI.TEXT_SECONDARY_LIGHT);
        amountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(amountLabel);
        
        // Percentage panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color(249, 250, 251));
        
        double percentage = (amount / total) * 100;
        JLabel percentLabel = ModernUI.createModernLabel(String.format("%.1f%%", percentage), 16, true);
        percentLabel.setForeground(category.getColor());
        percentLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        // Progress bar
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue((int) percentage);
        progressBar.setStringPainted(false);
        progressBar.setPreferredSize(new Dimension(100, 6));
        progressBar.setForeground(category.getColor());
        progressBar.setBackground(new Color(229, 231, 235));
        progressBar.setBorderPainted(false);
        
        rightPanel.add(percentLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(progressBar);
        
        panel.add(iconPanel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    public void applyTheme(boolean isDarkMode) {
        Color bgColor = isDarkMode ? ModernUI.BACKGROUND_DARK : ModernUI.BACKGROUND_LIGHT;
        setBackground(bgColor);
    }
}
