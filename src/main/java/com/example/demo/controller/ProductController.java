package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CartService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, CartService cartService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.cartService = cartService;
    }

    @GetMapping("/{productId}")
    public String getProductDetail(@PathVariable Long productId, Model model) {
        // Lấy thông tin sản phẩm
        Product product = productService.getProductById(productId);
        if (product == null) {
            return "redirect:/";
        }

        // Lấy danh mục cha cho header
        List<Category> parentCategories = categoryService.getParentCategories();
        model.addAttribute("categories", parentCategories);

        // Lấy sản phẩm liên quan (cùng danh mục)
        List<Product> relatedProducts = productService.getRelatedProducts(productId, 4);

        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);
        model.addAttribute("cartItemCount", cartService.getItemCount());

        return "product-detail";
    }

    @GetMapping("/detail/product/{productId}")
    public String getViewProductDetail(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);
        if (product == null)
            return "redirect:/";

        List<Category> parentCategories = categoryService.getParentCategories();
        List<Product> relatedProducts = productService.getRelatedProducts(productId, 4);
        // List<Comment> comments = commentService.getCommentsByProductId(productId); //
        // nếu có

        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);
        model.addAttribute("categories", parentCategories);
        model.addAttribute("cartItemCount", cartService.getItemCount());
        // model.addAttribute("comments",);

        return "product-detail"; // Tên file HTML không cần đuôi .html
    }

    // API endpoints
    @GetMapping("/api/all")
    @ResponseBody
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
