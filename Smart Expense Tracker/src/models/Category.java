package models;

import java.awt.Color;
import java.io.Serializable;

/**
 * Represents an expense category
 */
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private Color color;
    private String icon;
    
    public Category(String id, String name, Color color, String icon) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.icon = icon;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    // Predefined categories
    public static Category[] getDefaultCategories() {
        return new Category[] {
            new Category("1", "Food", new Color(255, 99, 71), "🍔"),
            new Category("2", "Travel", new Color(65, 105, 225), "✈️"),
            new Category("3", "Entertainment", new Color(255, 215, 0), "🎬"),
            new Category("4", "Shopping", new Color(147, 112, 219), "🛍️"),
            new Category("5", "Healthcare", new Color(60, 179, 113), "🏥"),
            new Category("6", "Bills", new Color(220, 20, 60), "📄"),
            new Category("7", "Education", new Color(0, 191, 255), "📚"),
            new Category("8", "Others", new Color(128, 128, 128), "📦")
        };
    }
}
