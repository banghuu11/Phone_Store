package com.example.demo.controller;

import com.example.demo.model.Wishlist;
import com.example.demo.service.WishlistService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ResponseBody
    public String addToWishlist(@RequestParam Long productId, Authentication authentication) {
        System.out.println("=== ADD TO WISHLIST ===");
        System.out.println("Product ID: " + productId);
        System.out.println("User: " + (authentication != null ? authentication.getName() : "null"));

        try {
            Long userId = userService.getCurrentUserId();
            System.out.println("Current user ID: " + userId);

            if (userId == null) {
                System.out.println("User not authenticated");
                return "not_logged_in";
            }

            Wishlist wishlist = wishlistService.createWishlist(userId, productId);
            if (wishlist == null) {
                System.out.println("Product already exists in wishlist");
                return "exists";
            }

            System.out.println("Added to wishlist successfully");
            return "success";

        } catch (Exception e) {
            System.err.println("Error adding to wishlist: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/remove")
    @ResponseBody
    public String removeFromWishlist(@RequestParam Long productId, Authentication authentication) {
        System.out.println("=== REMOVE FROM WISHLIST ===");
        System.out.println("Product ID: " + productId);

        try {
            Long userId = userService.getCurrentUserId();
            System.out.println("Current user ID: " + userId);

            if (userId == null) {
                return "not_logged_in";
            }

            wishlistService.removeWishlistByUserAndProduct(userId, productId);
            System.out.println("Removed from wishlist successfully");
            return "success";

        } catch (Exception e) {
            System.err.println("Error removing from wishlist: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }
}