package ui.panels;

import managers.ExpenseManager;
import models.Expense;
import models.Category;
import ui.MainFrame;
import ui.utils.ModernUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel for viewing and managing expenses
 */
public class ExpenseListPanel extends JPanel {
    private ExpenseManager expenseManager;
    private MainFrame mainFrame;
    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    
    public ExpenseListPanel(ExpenseManager expenseManager, MainFrame mainFrame) {
        this.expenseManager = expenseManager;
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout(15, 15));
        setBackground(ModernUI.BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        initializeUI();
    }
    
    private void initializeUI() {
        // Top panel with title and actions
        JPanel topPanel = new JPanel(new BorderLayout(15, 15));
        topPanel.setOpaque(false);
        
        // Title section
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("💳 All Expenses");
        titleLabel.setFont(new Font("SF Pro Display", Font.BOLD, 28));
        titleLabel.setForeground(ModernUI.TEXT_PRIMARY);
        
        titlePanel.add(titleLabel);
        topPanel.add(titlePanel, BorderLayout.WEST);
        
        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);
        
        JButton refreshButton = ModernUI.createModernButton("🔄 Refresh", ModernUI.TEXT_SECONDARY, false);
        JButton editButton = ModernUI.createModernButton("✏️ Edit", ModernUI.PRIMARY_COLOR, false);
        JButton deleteButton = ModernUI.createModernButton("🗑️ Delete", ModernUI.DANGER_COLOR, false);
        
        refreshButton.addActionListener(e -> refresh());
        editButton.addActionListener(e -> editExpense());
        deleteButton.addActionListener(e -> deleteExpense());
        
        actionPanel.add(refreshButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        
        topPanel.add(actionPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        // Table container with card style
        JPanel tableContainer = ModernUI.createCard();
        tableContainer.setLayout(new BorderLayout());
        
        // Table
        String[] columnNames = {"📅 Date", "📝 Description", "🏷️ Category", "💰 Amount", "📄 Notes"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        expenseTable = new JTable(tableModel);
        expenseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        expenseTable.setRowHeight(45);
        expenseTable.setShowGrid(false);
        expenseTable.setIntercellSpacing(new Dimension(0, 0));
        expenseTable.setBackground(Color.WHITE);
        expenseTable.setSelectionBackground(new Color(ModernUI.PRIMARY_COLOR.getRed(), 
                                                       ModernUI.PRIMARY_COLOR.getGreen(), 
                                                       ModernUI.PRIMARY_COLOR.getBlue(), 40));
        expenseTable.setSelectionForeground(ModernUI.TEXT_PRIMARY);
        expenseTable.setFont(new Font("SF Pro Text", Font.PLAIN, 13));
        
        // Modern table header
        JTableHeader header = expenseTable.getTableHeader();
        header.setBackground(ModernUI.BG_COLOR);
        header.setForeground(ModernUI.TEXT_SECONDARY);
        header.setFont(new Font("SF Pro Text", Font.BOLD, 12));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ModernUI.BORDER_COLOR));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        
        // Cell renderer for alternating rows
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                }
                
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                
                return c;
            }
        };
        
        for (int i = 0; i < expenseTable.getColumnCount(); i++) {
            expenseTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(expenseTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        
        add(tableContainer, BorderLayout.CENTER);
        
        refresh();
    }
    
    public void refresh() {
        tableModel.setRowCount(0);
        
        List<Expense> expenses = expenseManager.getAllExpenses();
        expenses.sort((e1, e2) -> e2.getDate().compareTo(e1.getDate()));
        
        for (Expense expense : expenses) {
            Object[] row = {
                    expense.getDate().format(dateFormatter),
                    expense.getDescription(),
                    expense.getCategory().getName(),
                    String.format("$%.2f", expense.getAmount()),
                    expense.getNotes()
            };
            tableModel.addRow(row);
        }
    }
    
    private void editExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select an expense to edit",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<Expense> expenses = expenseManager.getAllExpenses();
        expenses.sort((e1, e2) -> e2.getDate().compareTo(e1.getDate()));
        Expense expense = expenses.get(selectedRow);
        
        // Create modern edit dialog
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Expense", true);
        dialog.setLayout(new BorderLayout(20, 20));
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(ModernUI.BG_COLOR);
        
        // Dialog content
        JPanel contentPanel = ModernUI.createCard();
        contentPanel.setLayout(new BorderLayout(15, 15));
        
        // Title
        JLabel dialogTitle = new JLabel("✏️ Edit Expense");
        dialogTitle.setFont(new Font("SF Pro Display", Font.BOLD, 22));
        dialogTitle.setForeground(ModernUI.TEXT_PRIMARY);
        dialogTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        contentPanel.add(dialogTitle, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.weightx = 1.0;
        
        JTextField descField = ModernUI.createModernTextField("Description");
        descField.setText(expense.getDescription());
        JTextField amountField = ModernUI.createModernTextField("Amount");
        amountField.setText(String.valueOf(expense.getAmount()));
        JComboBox<Category> categoryBox = new JComboBox<>(expenseManager.getCategories().toArray(new Category[0]));
        categoryBox.setSelectedItem(expense.getCategory());
        categoryBox.setFont(new Font("SF Pro Text", Font.PLAIN, 14));
        JTextField notesField = ModernUI.createModernTextField("Notes");
        notesField.setText(expense.getNotes());
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createFieldLabel("Description"), gbc);
        gbc.gridy = 1;
        formPanel.add(descField, gbc);
        
        gbc.gridy = 2;
        formPanel.add(createFieldLabel("Amount"), gbc);
        gbc.gridy = 3;
        formPanel.add(amountField, gbc);
        
        gbc.gridy = 4;
        formPanel.add(createFieldLabel("Category"), gbc);
        gbc.gridy = 5;
        formPanel.add(categoryBox, gbc);
        
        gbc.gridy = 6;
        formPanel.add(createFieldLabel("Notes"), gbc);
        gbc.gridy = 7;
        formPanel.add(notesField, gbc);
        
        contentPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        
        JButton cancelButton = ModernUI.createModernButton("Cancel", ModernUI.TEXT_SECONDARY, false);
        JButton saveButton = ModernUI.createModernButton("💾 Save Changes", ModernUI.SUCCESS_COLOR, true);
        
        saveButton.addActionListener(e -> {
            try {
                String description = descField.getText().trim();
                double amount = Double.parseDouble(amountField.getText().trim());
                Category category = (Category) categoryBox.getSelectedItem();
                String notes = notesField.getText().trim();
                
                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Description cannot be empty");
                    return;
                }
                
                Expense updatedExpense = new Expense(
                        expense.getId(),
                        description,
                        amount,
                        category,
                        expense.getDate(),
                        notes
                );
                
                expenseManager.updateExpense(expense.getId(), updatedExpense);
                refresh();
                dialog.dispose();
                
                JOptionPane.showMessageDialog(this, "✅ Expense updated successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "❌ Invalid amount");
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dialog.setVisible(true);
    }
    
    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SF Pro Text", Font.PLAIN, 13));
        label.setForeground(ModernUI.TEXT_SECONDARY);
        return label;
    }
    
    private void deleteExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select an expense to delete",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this expense?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            List<Expense> expenses = expenseManager.getAllExpenses();
            expenses.sort((e1, e2) -> e2.getDate().compareTo(e1.getDate()));
            Expense expense = expenses.get(selectedRow);
            
            expenseManager.deleteExpense(expense.getId());
            refresh();
            
            JOptionPane.showMessageDialog(this, "Expense deleted successfully!");
        }
    }
    
    public void applyTheme(boolean isDarkMode) {
        Color bgColor = isDarkMode ? new Color(45, 45, 45) : Color.WHITE;
        Color fgColor = isDarkMode ? new Color(220, 220, 220) : Color.BLACK;
        
        setBackground(bgColor);
        setForeground(fgColor);
    }
}
