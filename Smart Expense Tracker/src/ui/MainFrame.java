package ui;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import managers.ExpenseManager;
import storage.FileStorage;
import ui.panels.*;
import ui.utils.ModernUI;

/**
 * Main application frame with modern UI
 */
public class MainFrame extends JFrame {
    private ExpenseManager expenseManager;
    private FileStorage fileStorage;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private boolean isDarkMode = false;
    
    // Modern color schemes
    private Color lightBackground = ModernUI.BACKGROUND_LIGHT;
    private Color darkBackground = ModernUI.BACKGROUND_DARK;
    private Color lightForeground = ModernUI.TEXT_PRIMARY_LIGHT;
    private Color darkForeground = ModernUI.TEXT_PRIMARY_DARK;
    
    // Panels
    private DashboardPanel dashboardPanel;
    private ExpenseListPanel expenseListPanel;
    private AddExpensePanel addExpensePanel;
    private ReportsPanel reportsPanel;
    
    public MainFrame() {
        expenseManager = new ExpenseManager();
        fileStorage = new FileStorage();
        
        // Load data
        loadData();
        
        // Setup frame with modern look
        setTitle("Smart Expense Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setBackground(lightBackground);
        
        // Initialize UI
        initializeUI();
        
        // Save data on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveData();
            }
        });
    }
    
    private void initializeUI() {
        // Create menu bar
        createMenuBar();
        
        // Create main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Initialize panels
        dashboardPanel = new DashboardPanel(expenseManager, this);
        expenseListPanel = new ExpenseListPanel(expenseManager, this);
        addExpensePanel = new AddExpensePanel(expenseManager, this);
        reportsPanel = new ReportsPanel(expenseManager, this);
        
        // Add panels to main panel
        mainPanel.add(dashboardPanel, "dashboard");
        mainPanel.add(expenseListPanel, "expenses");
        mainPanel.add(addExpensePanel, "add");
        mainPanel.add(reportsPanel, "reports");
        
        // Create sidebar
        JPanel sidebar = createSidebar();
        
        // Add components to frame
        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        
        // Show dashboard by default
        showPanel("dashboard");
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(260, getHeight()));
        sidebar.setBackground(ModernUI.SURFACE_LIGHT);
        sidebar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(229, 231, 235)),
            BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));
        
        // Logo/Title section
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBackground(ModernUI.SURFACE_LIGHT);
        logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel iconLabel = new JLabel("💰");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("Expense Tracker");
        titleLabel.setFont(new Font("SF Pro Display", Font.BOLD, 20));
        titleLabel.setForeground(ModernUI.TEXT_PRIMARY_LIGHT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Manage your finances");
        subtitleLabel.setFont(new Font("SF Pro Display", Font.PLAIN, 12));
        subtitleLabel.setForeground(ModernUI.TEXT_SECONDARY_LIGHT);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        logoPanel.add(iconLabel);
        logoPanel.add(Box.createVerticalStrut(10));
        logoPanel.add(titleLabel);
        logoPanel.add(Box.createVerticalStrut(5));
        logoPanel.add(subtitleLabel);
        
        sidebar.add(logoPanel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));
        
        // Navigation section label
        JLabel navLabel = new JLabel("NAVIGATION");
        navLabel.setFont(new Font("SF Pro Display", Font.BOLD, 11));
        navLabel.setForeground(ModernUI.TEXT_SECONDARY_LIGHT);
        navLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(navLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Navigation buttons
        sidebar.add(createNavButton("📊", "Dashboard", "dashboard", true));
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));
        sidebar.add(createNavButton("📝", "Expenses", "expenses", false));
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));
        sidebar.add(createNavButton("➕", "Add Expense", "add", false));
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));
        sidebar.add(createNavButton("📈", "Reports", "reports", false));
        
        sidebar.add(Box.createVerticalGlue());
        
        return sidebar;
    }
    
    private JButton createNavButton(String icon, String text, String panelName, boolean isSelected) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout(12, 0));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
        
        button.add(iconLabel, BorderLayout.WEST);
        button.add(textLabel, BorderLayout.CENTER);
        
        button.setMaximumSize(new Dimension(220, 44));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        if (isSelected) {
            button.setBackground(new Color(99, 102, 241, 30));
            button.setOpaque(true);
            textLabel.setForeground(ModernUI.PRIMARY_COLOR);
            textLabel.setFont(new Font("SF Pro Display", Font.BOLD, 14));
        } else {
            button.setOpaque(false);
            textLabel.setForeground(ModernUI.TEXT_SECONDARY_LIGHT);
        }
        
        button.addActionListener(e -> showPanel(panelName));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!isSelected) {
                    button.setBackground(new Color(243, 244, 246));
                    button.setOpaque(true);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!isSelected) {
                    button.setOpaque(false);
                }
            }
        });
        
        return button;
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportCSV = new JMenuItem("Export to CSV");
        JMenuItem exportJSON = new JMenuItem("Export to JSON");
        JMenuItem exit = new JMenuItem("Exit");
        
        exportCSV.addActionListener(e -> exportData("CSV"));
        exportJSON.addActionListener(e -> exportData("JSON"));
        exit.addActionListener(e -> {
            saveData();
            System.exit(0);
        });
        
        fileMenu.add(exportCSV);
        fileMenu.add(exportJSON);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        
        // View menu
        JMenu viewMenu = new JMenu("View");
        JCheckBoxMenuItem darkModeItem = new JCheckBoxMenuItem("Dark Mode");
        darkModeItem.addActionListener(e -> toggleDarkMode());
        viewMenu.add(darkModeItem);
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> showAboutDialog());
        helpMenu.add(about);
        
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    public void showPanel(String panelName) {
        // Refresh panels before showing
        switch (panelName) {
            case "dashboard":
                dashboardPanel.refresh();
                break;
            case "expenses":
                expenseListPanel.refresh();
                break;
            case "reports":
                reportsPanel.refresh();
                break;
        }
        cardLayout.show(mainPanel, panelName);
    }
    
    public void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        applyTheme();
    }
    
    private void applyTheme() {
        Color bgColor = isDarkMode ? darkBackground : lightBackground;
        Color fgColor = isDarkMode ? darkForeground : lightForeground;
        
        // Apply to all panels
        dashboardPanel.applyTheme(isDarkMode);
        expenseListPanel.applyTheme(isDarkMode);
        addExpensePanel.applyTheme(isDarkMode);
        reportsPanel.applyTheme(isDarkMode);
        
        SwingUtilities.updateComponentTreeUI(this);
    }
    
    public boolean isDarkMode() {
        return isDarkMode;
    }
    
    private void loadData() {
        try {
            expenseManager.setExpenses(fileStorage.loadExpenses());
            var categories = fileStorage.loadCategories();
            if (!categories.isEmpty()) {
                expenseManager.setCategories(categories);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Could not load data: " + e.getMessage(),
                    "Load Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void saveData() {
        try {
            fileStorage.saveExpenses(expenseManager.getAllExpenses());
            fileStorage.saveCategories(expenseManager.getCategories());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Could not save data: " + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportData(String format) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export to " + format);
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if (!path.toLowerCase().endsWith("." + format.toLowerCase())) {
                path += "." + format.toLowerCase();
            }
            
            try {
                if (format.equals("CSV")) {
                    fileStorage.exportToCSV(expenseManager.getAllExpenses(), path);
                } else {
                    fileStorage.exportToJSON(expenseManager.getAllExpenses(), path);
                }
                JOptionPane.showMessageDialog(this,
                        "Data exported successfully!",
                        "Export Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Export failed: " + e.getMessage(),
                        "Export Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "Smart Expense Tracker\nVersion 1.0\n\nA comprehensive expense tracking application",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
