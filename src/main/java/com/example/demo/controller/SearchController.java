package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CartService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;

    @Autowired
    public SearchController(ProductService productService, CategoryService categoryService, CartService cartService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.cartService = cartService;
    }

    @GetMapping
    public String searchProducts(@RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, defaultValue = "0") BigDecimal minPrice,
            @RequestParam(required = false, defaultValue = "999999999") BigDecimal maxPrice,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder,
            Model model) {

        // Lấy danh mục cha cho header
        List<Category> parentCategories = categoryService.getParentCategories();
        model.addAttribute("categories", parentCategories);

        // Tìm kiếm sản phẩm
        List<Product> products = productService.searchProducts(q, categoryId, minPrice, maxPrice, sortBy, sortOrder);
        model.addAttribute("products", products);
        model.addAttribute("searchQuery", q);
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);

        // Thông tin giỏ hàng
        model.addAttribute("cartItemCount", cartService.getItemCount());

        return "search-results";
    }
}