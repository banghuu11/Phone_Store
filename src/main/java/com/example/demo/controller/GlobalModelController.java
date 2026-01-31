package com.example.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CartService;
import java.util.List;

@ControllerAdvice
public class GlobalModelController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final CartService cartService;

    public GlobalModelController(UserService userService, CategoryService categoryService, CartService cartService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.cartService = cartService;
    }

    @ModelAttribute("currentUser")
    public User currentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails userDetails) {
            return userService.findByUsername(userDetails.getUsername());
        }
        return null;
    }

    // Danh mục cha + con (dùng cho header)
    @ModelAttribute("categories")
    public List<com.example.demo.model.Category> categories() {
        return categoryService.getParentCategories();
    }

    // Số lượng sản phẩm trong giỏ (hiển thị trên icon 🛒)
    @ModelAttribute("cartItemCount")
    public int cartItemCount() {
        return cartService.getItemCount();
    }
}
