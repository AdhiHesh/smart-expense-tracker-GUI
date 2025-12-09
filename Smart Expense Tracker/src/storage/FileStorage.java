package storage;

import models.Expense;
import models.Category;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.awt.Color;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles file storage operations for expenses
 */
public class FileStorage {
    private static final String DATA_DIR = "data";
    private static final String EXPENSES_FILE = DATA_DIR + "/expenses.json";
    private static final String CATEGORIES_FILE = DATA_DIR + "/categories.json";
    private static final Gson gson = createGson();
    
    public FileStorage() {
        createDataDirectory();
    }
    
    private void createDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    private static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .setPrettyPrinting()
                .create();
    }
    
    // Save expenses to JSON
    public void saveExpenses(List<Expense> expenses) throws IOException {
        try (Writer writer = new FileWriter(EXPENSES_FILE)) {
            gson.toJson(expenses, writer);
        }
    }
    
    // Load expenses from JSON
    public List<Expense> loadExpenses() throws IOException {
        File file = new File(EXPENSES_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Expense>>(){}.getType();
            List<Expense> expenses = gson.fromJson(reader, listType);
            return expenses != null ? expenses : new ArrayList<>();
        }
    }
    
    // Save categories to JSON
    public void saveCategories(List<Category> categories) throws IOException {
        try (Writer writer = new FileWriter(CATEGORIES_FILE)) {
            gson.toJson(categories, writer);
        }
    }
    
    // Load categories from JSON
    public List<Category> loadCategories() throws IOException {
        File file = new File(CATEGORIES_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Category>>(){}.getType();
            List<Category> categories = gson.fromJson(reader, listType);
            return categories != null ? categories : new ArrayList<>();
        }
    }
    
    // Export to CSV
    public void exportToCSV(List<Expense> expenses, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write header
            writer.println("ID,Description,Amount,Category,Date,Notes");
            
            // Write expenses
            for (Expense expense : expenses) {
                writer.printf("\"%s\",\"%s\",%.2f,\"%s\",\"%s\",\"%s\"%n",
                        expense.getId(),
                        expense.getDescription(),
                        expense.getAmount(),
                        expense.getCategory().getName(),
                        expense.getDate().toString(),
                        expense.getNotes() != null ? expense.getNotes() : "");
            }
        }
    }
    
    // Export to JSON
    public void exportToJSON(List<Expense> expenses, String filePath) throws IOException {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(expenses, writer);
        }
    }
    
    // Custom adapters for Gson
    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        
        @Override
        public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(formatter));
        }
        
        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }
    
    private static class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {
        @Override
        public JsonElement serialize(Color color, Type type, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.addProperty("r", color.getRed());
            obj.addProperty("g", color.getGreen());
            obj.addProperty("b", color.getBlue());
            return obj;
        }
        
        @Override
        public Color deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
            JsonObject obj = json.getAsJsonObject();
            return new Color(
                    obj.get("r").getAsInt(),
                    obj.get("g").getAsInt(),
                    obj.get("b").getAsInt()
            );
        }
    }
}
