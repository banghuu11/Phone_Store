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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final CartService cartService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService, CartService cartService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping("/{categoryId}")
    public String getProductsByCategory(@PathVariable Long categoryId, Model model) {
        // Lấy thông tin danh mục
        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            return "redirect:/";
        }

        // Lấy danh mục cha cho header
        List<Category> parentCategories = categoryService.getParentCategories();
        model.addAttribute("categories", parentCategories);

        // Lấy sản phẩm theo danh mục
        List<Product> products = productService.getProductsByCategory(categoryId);
        model.addAttribute("products", products);
        model.addAttribute("currentCategory", category);

        // Thông tin giỏ hàng
        model.addAttribute("cartItemCount", cartService.getItemCount());

        return "category-products";
    }

    @GetMapping("/parent/{parentId}")
    public String getProductsByParentCategory(@PathVariable Long parentId, Model model) {
        // Lấy thông tin danh mục cha
        Category parentCategory = categoryService.getCategoryById(parentId);
        if (parentCategory == null || parentCategory.getParent() != null) {
            return "redirect:/";
        }

        // Lấy danh mục cha cho header
        List<Category> parentCategories = categoryService.getParentCategories();
        model.addAttribute("categories", parentCategories);

        // Lấy tất cả sản phẩm của danh mục cha và các danh mục con
        List<Product> products = productService.getProductsByParentCategory(parentId);
        model.addAttribute("products", products);
        model.addAttribute("currentCategory", parentCategory);

        // Thông tin giỏ hàng
        model.addAttribute("cartItemCount", cartService.getItemCount());

        return "category-products";
    }
}
