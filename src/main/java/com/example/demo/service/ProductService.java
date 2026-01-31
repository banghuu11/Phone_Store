package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepo.findByCategoryCategoryId(categoryId);
    }

    public List<Product> getProductsByParentCategory(Long parentId) {
        return productRepo.findByCategoryParentCategoryId(parentId);
    }

    public List<Product> getRelatedProducts(Long productId, int limit) {
        List<Product> products = productRepo.findRelatedProducts(productId);
        return products.stream().limit(limit).toList(); // Java 16+ hoặc dùng .collect(Collectors.toList()) cho Java 8+
    }

    public List<Product> searchProducts(String query, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice,
            String sortBy, String sortOrder) {
        return productRepo.searchProducts(query, categoryId, minPrice, maxPrice, sortBy, sortOrder);
    }

    public List<Product> getTop5Laptops() {
        return productRepo.findTop4ByNameContainingIgnoreCase("Laptop");
    }

    public List<Product> getTop10ProductsByCategory(Long categoryId) {
        return productRepo.findTop9ByCategory_CategoryIdOrderByProductIdDesc(categoryId);
    }

    public List<Product> getTop10ProductsByParentCategory(Long parentId) {
        return productRepo.findTop9ByCategory_Parent_CategoryIdOrderByProductIdDesc(parentId);
    }

    public List<Product> suggestProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty())
            return List.of();
        return productRepo.findTop4ByNameContainingIgnoreCase(keyword.trim());
    }

    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepo.findById(id).map(existingProduct -> {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setDiscount(updatedProduct.getDiscount());
            existingProduct.setStock(updatedProduct.getStock());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setImageUrl(updatedProduct.getImageUrl());
            existingProduct.setStatus(updatedProduct.getStatus());
            existingProduct.setUpdatedAt(java.time.LocalDateTime.now());
            return productRepo.save(existingProduct);
        }).orElse(null);
    }

    public boolean deleteProduct(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // TODO: Cần xóa hoặc triển khai đúng nếu dùng
    public Object getAllCategories() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllCategories'");
    }
}
