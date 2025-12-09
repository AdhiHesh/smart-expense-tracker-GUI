package ui.panels;

import managers.ExpenseManager;
import models.Expense;
import models.Category;
import ui.MainFrame;
import ui.utils.ModernUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Modern panel for adding new expenses
 */
public class AddExpensePanel extends JPanel {
    private ExpenseManager expenseManager;
    private MainFrame mainFrame;
    
    private JTextField descriptionField;
    private JTextField amountField;
    private JComboBox<Category> categoryBox;
    private JSpinner dateSpinner;
    private JTextArea notesArea;
    
    public AddExpensePanel(ExpenseManager expenseManager, MainFrame mainFrame) {
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
        
        JLabel titleLabel = ModernUI.createModernLabel("Add New Expense", 28, true);
        JLabel subtitleLabel = ModernUI.createModernLabel("Track your spending", 14, false);
        subtitleLabel.setForeground(ModernUI.TEXT_SECONDARY_LIGHT);
        
        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setBackground(ModernUI.BACKGROUND_LIGHT);
        titleBox.add(titleLabel);
        titleBox.add(Box.createVerticalStrut(5));
        titleBox.add(subtitleLabel);
        
        headerPanel.add(titleBox, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);
        
        // Form card
        JPanel formCard = ModernUI.createCard();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        
        // Description field
        JPanel descGroup = createFieldGroup("Description", "e.g., Lunch at restaurant");
        formCard.add(descGroup);
        descriptionField = findTextField(descGroup);
        formCard.add(Box.createVerticalStrut(20));
        
        // Amount field
        JPanel amountGroup = createFieldGroup("Amount", "0.00");
        formCard.add(amountGroup);
        amountField = findTextField(amountGroup);
        formCard.add(Box.createVerticalStrut(20));
        
        // Category dropdown
        JPanel categoryGroup = new JPanel();
        categoryGroup.setLayout(new BoxLayout(categoryGroup, BoxLayout.Y_AXIS));
        categoryGroup.setBackground(Color.WHITE);
        categoryGroup.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel categoryLabel = ModernUI.createModernLabel("Category", 14, true);
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        categoryBox = new JComboBox<>(expenseManager.getCategories().toArray(new Category[0]));
        categoryBox.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
        categoryBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        categoryBox.setPreferredSize(new Dimension(categoryBox.getPreferredSize().width, 44));
        categoryBox.setBorder(BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(new Color(229, 231, 235), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        categoryBox.setBackground(Color.WHITE);
        categoryBox.setRenderer(new CategoryCellRenderer());
        categoryBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Make the dropdown popup wider and taller
        categoryBox.setPrototypeDisplayValue(null);
        categoryBox.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
                Object comp = categoryBox.getUI().getAccessibleChild(categoryBox, 0);
                if (comp instanceof javax.swing.plaf.basic.ComboPopup) {
                    JList<?> list = ((javax.swing.plaf.basic.ComboPopup) comp).getList();
                    list.setFixedCellHeight(40);
                }
            }
            @Override
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {}
            @Override
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {}
        });
        
        categoryGroup.add(categoryLabel);
        categoryGroup.add(Box.createVerticalStrut(8));
        categoryGroup.add(categoryBox);
        
        formCard.add(categoryGroup);
        formCard.add(Box.createVerticalStrut(20));
        
        // Date picker
        JPanel dateGroup = new JPanel();
        dateGroup.setLayout(new BoxLayout(dateGroup, BoxLayout.Y_AXIS));
        dateGroup.setBackground(Color.WHITE);
        dateGroup.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel dateLabel = ModernUI.createModernLabel("Date", 14, true);
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "MMM dd, yyyy");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
        dateSpinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        dateSpinner.setBorder(BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(new Color(229, 231, 235), 1, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        dateSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        dateGroup.add(dateLabel);
        dateGroup.add(Box.createVerticalStrut(8));
        dateGroup.add(dateSpinner);
        
        formCard.add(dateGroup);
        formCard.add(Box.createVerticalStrut(20));
        
        // Notes area
        JPanel notesGroup = new JPanel();
        notesGroup.setLayout(new BoxLayout(notesGroup, BoxLayout.Y_AXIS));
        notesGroup.setBackground(Color.WHITE);
        notesGroup.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel notesLabel = ModernUI.createModernLabel("Notes (Optional)", 14, true);
        notesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        notesArea = new JTextArea(4, 20);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
        notesArea.setBorder(BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(new Color(229, 231, 235), 1, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        JScrollPane notesScroll = new JScrollPane(notesArea);
        notesScroll.setBorder(null);
        notesScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        notesGroup.add(notesLabel);
        notesGroup.add(Box.createVerticalStrut(8));
        notesGroup.add(notesScroll);
        
        formCard.add(notesGroup);
        
        add(formCard, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setBackground(ModernUI.BACKGROUND_LIGHT);
        
        JButton clearButton = ModernUI.createModernButton("Clear", new Color(107, 114, 128));
        clearButton.addActionListener(e -> clearForm());
        
        JButton addButton = ModernUI.createModernButton("Add Expense", ModernUI.PRIMARY_COLOR);
        addButton.addActionListener(e -> addExpense());
        
        buttonPanel.add(clearButton);
        buttonPanel.add(addButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createFieldGroup(String label, String placeholder) {
        JPanel group = new JPanel();
        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
        group.setBackground(Color.WHITE);
        group.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel fieldLabel = ModernUI.createModernLabel(label, 14, true);
        fieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField field = ModernUI.createModernTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Placeholder effect
        field.setForeground(ModernUI.TEXT_SECONDARY_LIGHT);
        field.setText(placeholder);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(ModernUI.TEXT_PRIMARY_LIGHT);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setForeground(ModernUI.TEXT_SECONDARY_LIGHT);
                    field.setText(placeholder);
                }
            }
        });
        
        group.add(fieldLabel);
        group.add(Box.createVerticalStrut(8));
        group.add(field);
        
        return group;
    }
    
    /**
     * Helper method to find JTextField in a component hierarchy
     */
    private JTextField findTextField(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JTextField) {
                return (JTextField) comp;
            }
            if (comp instanceof Container) {
                JTextField field = findTextField((Container) comp);
                if (field != null) {
                    return field;
                }
            }
        }
        return null;
    }
    
    private void addExpense() {
        try {
            String description = descriptionField.getText().trim();
            String amountText = amountField.getText().trim();
            Category category = (Category) categoryBox.getSelectedItem();
            java.util.Date date = (java.util.Date) dateSpinner.getValue();
            LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
            String notes = notesArea.getText().trim();
            
            // Validation
            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a description",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter an amount",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Amount must be greater than zero",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create expense
            Expense expense = new Expense(description, amount, category, localDate, notes);
            expenseManager.addExpense(expense);
            
            JOptionPane.showMessageDialog(this,
                    "Expense added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            
            clearForm();
            mainFrame.showPanel("dashboard");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid amount",
                    "Invalid Amount",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        descriptionField.setText("");
        amountField.setText("");
        categoryBox.setSelectedIndex(0);
        dateSpinner.setValue(new java.util.Date());
        notesArea.setText("");
    }
    
    public void applyTheme(boolean isDarkMode) {
        Color bgColor = isDarkMode ? new Color(45, 45, 45) : Color.WHITE;
        Color fgColor = isDarkMode ? new Color(220, 220, 220) : Color.BLACK;
        
        setBackground(bgColor);
        setForeground(fgColor);
    }
    
    // Custom renderer for category combo box
    private class CategoryCellRenderer extends JLabel implements ListCellRenderer<Category> {
        
        public CategoryCellRenderer() {
            setOpaque(true);
        }
        
        @Override
        public Component getListCellRendererComponent(JList<? extends Category> list, Category value,
                                                     int index, boolean isSelected, boolean cellHasFocus) {
            
            if (value == null) {
                setText("");
                return this;
            }
            
            // For the dropdown list items
            if (index >= 0) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
                panel.setOpaque(true);
                
                // Icon with colored background circle
                JLabel iconLabel = new JLabel(value.getIcon());
                iconLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                iconLabel.setOpaque(true);
                iconLabel.setBackground(value.getColor());
                iconLabel.setForeground(Color.WHITE);
                iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
                iconLabel.setPreferredSize(new Dimension(28, 28));
                iconLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(value.getColor().darker(), 1),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)
                ));
                
                // Category name
                JLabel nameLabel = new JLabel(value.getName());
                nameLabel.setFont(new Font("SF Pro Text", Font.PLAIN, 13));
                
                panel.add(iconLabel);
                panel.add(nameLabel);
                
                // Selection colors
                if (isSelected) {
                    panel.setBackground(ModernUI.PRIMARY_COLOR);
                    nameLabel.setForeground(Color.WHITE);
                } else {
                    panel.setBackground(Color.WHITE);
                    nameLabel.setForeground(ModernUI.TEXT_PRIMARY);
                }
                
                return panel;
            } 
            // For the selected item shown in the combo box
            else {
                setFont(new Font("SF Pro Text", Font.PLAIN, 14));
                setText(value.getIcon() + "  " + value.getName());
                setForeground(ModernUI.TEXT_PRIMARY);
                setBackground(Color.WHITE);
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return this;
            }
        }
    }
}
