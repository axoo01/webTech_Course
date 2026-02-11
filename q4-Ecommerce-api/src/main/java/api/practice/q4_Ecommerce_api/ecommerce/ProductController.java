package api.practice.q4_Ecommerce_api.ecommerce;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private List<Product> products = new ArrayList<>();

    public ProductController() {
        
        products.add(new Product(1L, "iPhone 14", "Latest Apple smartphone", 999.99, "Smartphone", 45, "Apple"));
        products.add(new Product(2L, "Galaxy S23", "Samsung flagship", 899.99, "Smartphone", 30, "Samsung"));
        products.add(new Product(3L, "MacBook Air M2", "Lightweight laptop", 1199.99, "Laptop", 20, "Apple"));
        products.add(new Product(4L, "Gaming Laptop RTX 4070", "High-performance gaming", 1499.99, "Laptop", 8, "Asus"));
        products.add(new Product(5L, "Wireless Earbuds", "Noise cancelling", 149.99, "Accessories", 120, "Sony"));
        products.add(new Product(6L, "4K Smart TV 55\"", "OLED display", 799.99, "TV", 15, "LG"));
        products.add(new Product(7L, "Mechanical Keyboard", "RGB backlit", 89.99, "Accessories", 65, "Logitech"));
        products.add(new Product(8L, "AirPods Pro", "Premium earbuds", 249.99, "Accessories", 50, "Apple"));
        products.add(new Product(9L, "Nikon Z6 Camera", "Mirrorless full-frame", 1599.99, "Camera", 5, "Nikon"));
        products.add(new Product(10L, "Fitness Tracker", "Heart rate + GPS", 79.99, "Wearable", 200, "Fitbit"));
        products.add(new Product(11L, "Wireless Mouse", "Ergonomic design", 39.99, "Accessories", 0, "Logitech")); // out of stock example
    }

    @GetMapping
    public List<Product> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {

        List<Product> result = new ArrayList<>(products);

        if (page != null && limit != null && page >= 0 && limit > 0) {
            int start = page * limit;
            int end = Math.min(start + limit, result.size());
            if (start >= result.size()) return List.of();
            return result.subList(start, end);
        }
        return result;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getById(@PathVariable Long productId) {
        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public List<Product> getByCategory(@PathVariable String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @GetMapping("/brand/{brand}")
    public List<Product> getByBrand(@PathVariable String brand) {
        return products.stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String keyword) {
        String lower = keyword.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lower) ||
                             p.getDescription().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    @GetMapping("/price-range")
    public List<Product> getByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        return products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    @GetMapping("/in-stock")
    public List<Product> getInStock() {
        return products.stream()
                .filter(p -> p.getStockQuantity() > 0)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product newProduct) {
        products.add(newProduct);
        return ResponseEntity.status(201).body(newProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updated) {
        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .map(p -> {
                    p.setName(updated.getName());
                    p.setDescription(updated.getDescription());
                    p.setPrice(updated.getPrice());
                    p.setCategory(updated.getCategory());
                    p.setStockQuantity(updated.getStockQuantity());
                    p.setBrand(updated.getBrand());
                    return ResponseEntity.ok(p);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long productId, @RequestParam int quantity) {
        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .map(p -> {
                    p.setStockQuantity(quantity);
                    return ResponseEntity.ok(p);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean removed = products.removeIf(p -> p.getProductId().equals(productId));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
