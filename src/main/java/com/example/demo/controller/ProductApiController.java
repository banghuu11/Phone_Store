package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// DTO record for suggestion
record ProductSuggestion(Long id, String name) {
}

@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    // Endpoint gợi ý tên sản phẩm
    @GetMapping("/suggest")
    public List<ProductSuggestion> suggest(@RequestParam("q") String query) {
        List<Product> products = productService.suggestProducts(query);
        return products.stream()
                .map(p -> new ProductSuggestion(p.getProductId(), p.getName()))
                .toList();
    }
}