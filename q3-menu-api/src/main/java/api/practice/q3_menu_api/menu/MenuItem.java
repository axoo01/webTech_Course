package api.practice.q3_menu_api.menu;

public class MenuItem {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category; // Appetizer, Main Course, Dessert, Beverage
    private boolean available;

    public MenuItem() {}

    public MenuItem(Long id, String name, String description, Double price, String category, boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.available = available;
    }

   
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return available; }

    
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(Double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setAvailable(boolean available) { this.available = available; }
}
