package auca.ac.rw.restfullApiAssignment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.restfullApiAssignment.modal.Product;
import auca.ac.rw.restfullApiAssignment.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepo; 

    
    public String saveProduct(Product product){
        productRepo.save(product);
        return "Product saved successfully.";
    }

   
    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

 
    public Product getProductById(Long id){
        Optional<Product> product = productRepo.findById(id);
        return product.orElse(null);
    }

   
    public String updateProduct(Long id, Product productDetails){
        Optional<Product> optionalProduct = productRepo.findById(id);
        
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            
            if(productDetails.getName() != null){
                product.setName(productDetails.getName());
            }
            if(productDetails.getDescription() != null){
                product.setDescription(productDetails.getDescription());
            }
            if(productDetails.getPrice() > 0){
                product.setPrice(productDetails.getPrice());
            }
            if(productDetails.getCategory() != null){
                product.setCategory(productDetails.getCategory());
            }
            if(productDetails.getStockQuantity() > 0){
                product.setStockQuantity(productDetails.getStockQuantity());
            }
            
            productRepo.save(product);
            return "Product updated successfully.";
        }else{
            return "Product with id " + id + " not found.";
        }
    }

  
    public String deleteProduct(Long id){
        Optional<Product> optionalProduct = productRepo.findById(id);
        
        if(optionalProduct.isPresent()){
            productRepo.deleteById(id);
            return "Product deleted successfully.";
        }else{
            return "Product with id " + id + " not found.";
        }
    }
}
