package auca.ac.rw.restfullApiAssignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.rw.restfullApiAssignment.modal.Product;
import auca.ac.rw.restfullApiAssignment.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    
    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            String result = productService.saveProduct(product);
            if ("Product saved successfully.".equals(result)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error saving product: " + e.getMessage());
        }
    }

   
    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                     .body("No products found.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving products: " + e.getMessage());
        }
    }

    
    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product != null) {
                return ResponseEntity.status(HttpStatus.OK).body(product);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Product with id " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving product: " + e.getMessage());
        }
    }

    
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            String result = productService.updateProduct(id, product);
            if ("Product updated successfully.".equals(result)) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error updating product: " + e.getMessage());
        }
    }

   
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            String result = productService.deleteProduct(id);
            if ("Product deleted successfully.".equals(result)) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error deleting product: " + e.getMessage());
        }
    }
}