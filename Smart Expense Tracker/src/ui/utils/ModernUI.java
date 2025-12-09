package ui.utils;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Modern UI utilities and styling
 */
public class ModernUI {
    
    // Modern color palette
    public static final Color PRIMARY_COLOR = new Color(99, 102, 241); // Indigo
    public static final Color PRIMARY_DARK = new Color(79, 70, 229);
    public static final Color ACCENT_COLOR = new Color(236, 72, 153); // Pink
    public static final Color SUCCESS_COLOR = new Color(34, 197, 94); // Green
    public static final Color WARNING_COLOR = new Color(251, 146, 60); // Orange
    public static final Color DANGER_COLOR = new Color(239, 68, 68); // Red
    
    // Layout colors
    public static final Color BG_COLOR = new Color(249, 250, 251);
    public static final Color SURFACE_COLOR = Color.WHITE;
    public static final Color BORDER_COLOR = new Color(229, 231, 235);
    
    // Text colors
    public static final Color TEXT_PRIMARY = new Color(17, 24, 39);
    public static final Color TEXT_SECONDARY = new Color(107, 114, 128);
    
    // Legacy theme colors
    public static final Color BACKGROUND_LIGHT = new Color(249, 250, 251);
    public static final Color SURFACE_LIGHT = Color.WHITE;
    public static final Color TEXT_PRIMARY_LIGHT = new Color(17, 24, 39);
    public static final Color TEXT_SECONDARY_LIGHT = new Color(107, 114, 128);
    
    public static final Color BACKGROUND_DARK = new Color(17, 24, 39);
    public static final Color SURFACE_DARK = new Color(31, 41, 55);
    public static final Color TEXT_PRIMARY_DARK = new Color(243, 244, 246);
    public static final Color TEXT_SECONDARY_DARK = new Color(156, 163, 175);
    
    /**
     * Creates a modern styled button (2-param version)
     */
    public static JButton createModernButton(String text, Color bgColor) {
        return createModernButton(text, bgColor, true);
    }
    
    /**
     * Creates a modern styled button (3-param version with filled option)
     */
    public static JButton createModernButton(String text, Color bgColor, boolean filled) {
        JButton button = new JButton(text);
        button.setFont(new Font("SF Pro Display", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(12, 24, 12, 24));
        
        if (filled) {
            button.setForeground(Color.WHITE);
            button.setBackground(bgColor);
            button.setBorderPainted(false);
            button.setOpaque(true);
        } else {
            button.setForeground(bgColor);
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor, 2),
                BorderFactory.createEmptyBorder(10, 22, 10, 22)
            ));
            button.setOpaque(true);
        }
        
        // Rounded corners
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (filled) {
                    if (button.getModel().isPressed()) {
                        g2.setColor(bgColor.darker());
                    } else if (button.getModel().isRollover()) {
                        g2.setColor(bgColor.brighter());
                    } else {
                        g2.setColor(bgColor);
                    }
                    g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10);
                } else {
                    if (button.getModel().isPressed()) {
                        g2.setColor(new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 40));
                        g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10);
                    } else if (button.getModel().isRollover()) {
                        g2.setColor(new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 20));
                        g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10);
                    }
                    g2.setColor(bgColor);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawRoundRect(1, 1, c.getWidth() - 3, c.getHeight() - 3, 10, 10);
                }
                
                g2.dispose();
                super.paint(g, c);
            }
        });
        
        return button;
    }
    
    /**
     * Creates a modern card panel with shadow
     */
    public static JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(5, 5, 5, 5),
            BorderFactory.createCompoundBorder(
                new ShadowBorder(),
                new EmptyBorder(20, 20, 20, 20)
            )
        ));
        return card;
    }
    
    /**
     * Creates a modern text field (no args)
     */
    public static JTextField createModernTextField() {
        return createModernTextField("");
    }
    
    /**
     * Creates a modern text field with placeholder
     */
    public static JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(229, 231, 235), 1, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        field.setBackground(Color.WHITE);
        
        // Set placeholder as tool tip
        if (!placeholder.isEmpty()) {
            field.setToolTipText(placeholder);
        }
        
        // Focus border
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(PRIMARY_COLOR, 2, true),
                    new EmptyBorder(9, 14, 9, 14)
                ));
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(229, 231, 235), 1, true),
                    new EmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        
        return field;
    }
    
    /**
     * Creates a modern label
     */
    public static JLabel createModernLabel(String text, int size, boolean bold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SF Pro Display", bold ? Font.BOLD : Font.PLAIN, size));
        label.setForeground(TEXT_PRIMARY_LIGHT);
        return label;
    }
    
    /**
     * Creates a stat card for dashboard
     */
    public static JPanel createStatCard(String title, String value, Color color, String icon) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new ShadowBorder(),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        iconLabel.setForeground(color);
        
        // Content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_SECONDARY_LIGHT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SF Pro Display", Font.BOLD, 32));
        valueLabel.setForeground(TEXT_PRIMARY_LIGHT);
        
        content.add(titleLabel);
        content.add(Box.createVerticalStrut(5));
        content.add(valueLabel);
        
        card.add(iconLabel, BorderLayout.WEST);
        card.add(content, BorderLayout.CENTER);
        
        return card;
    }
    
    /**
     * Shadow border for cards
     */
    static class ShadowBorder extends AbstractBorder {
        private static final Color SHADOW_COLOR = new Color(0, 0, 0, 20);
        private static final int SHADOW_SIZE = 8;
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw shadow
            for (int i = 0; i < SHADOW_SIZE; i++) {
                int alpha = 20 - (i * 2);
                g2.setColor(new Color(0, 0, 0, Math.max(alpha, 0)));
                g2.drawRoundRect(x + i, y + i, width - (i * 2) - 1, height - (i * 2) - 1, 12, 12);
            }
            
            g2.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(SHADOW_SIZE, SHADOW_SIZE, SHADOW_SIZE, SHADOW_SIZE);
        }
    }
}
