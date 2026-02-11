package api.practice.q3_menu_api.menu;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private List<MenuItem> menu = new ArrayList<>();

    public MenuController() {
        
        menu.add(new MenuItem(1L, "Garlic Bread", "Toasted with garlic butter", 5.99, "Appetizer", true));
        menu.add(new MenuItem(2L, "Caesar Salad", "Romaine, parmesan, croutons", 8.50, "Appetizer", true));
        menu.add(new MenuItem(3L, "Grilled Chicken", "Herb-marinated breast", 14.99, "Main Course", true));
        menu.add(new MenuItem(4L, "Beef Burger", "Angus patty, cheese, fries", 12.99, "Main Course", false));
        menu.add(new MenuItem(5L, "Pasta Carbonara", "Creamy bacon sauce", 13.50, "Main Course", true));
        menu.add(new MenuItem(6L, "Chocolate Lava Cake", "Warm center, vanilla ice cream", 7.99, "Dessert", true));
        menu.add(new MenuItem(7L, "Cheesecake", "New York style", 6.99, "Dessert", true));
        menu.add(new MenuItem(8L, "Coke", "Classic cola", 2.99, "Beverage", true));
        menu.add(new MenuItem(9L, "Iced Tea", "Lemon flavored", 3.49, "Beverage", false));
    }

    @GetMapping
    public List<MenuItem> getAll() {
        return menu;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getById(@PathVariable Long id) {
        return menu.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public List<MenuItem> getByCategory(@PathVariable String category) {
        return menu.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @GetMapping("/available")
    public List<MenuItem> getAvailable(@RequestParam boolean available) {
        return menu.stream()
                .filter(item -> item.isAvailable() == available)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<MenuItem> searchByName(@RequestParam String name) {
        return menu.stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<MenuItem> addItem(@RequestBody MenuItem newItem) {
        menu.add(newItem);
        return ResponseEntity.status(201).body(newItem);
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {
        return menu.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .map(item -> {
                    item.setAvailable(!item.isAvailable());
                    return ResponseEntity.ok(item);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        boolean removed = menu.removeIf(item -> item.getId().equals(id));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
