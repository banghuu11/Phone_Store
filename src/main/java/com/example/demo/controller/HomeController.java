package com.example.demo.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.Wishlist;
import com.example.demo.service.CartService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.WishlistService;
import com.example.demo.service.UserService;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final CartService cartService;
    private final WishlistService wishlistService;
    private final UserService userService;

    @Autowired
    public HomeController(CategoryService categoryService, ProductService productService,
                          CartService cartService, WishlistService wishlistService, UserService userService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.cartService = cartService;
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        System.out.println("=== HOME CONTROLLER DEBUG ===");
        System.out.println("Current user login: banghuu11");
        System.out.println("Authentication: " + (authentication != null ? authentication.getName() : "null"));

        try {
            // 1. Lấy danh mục cha
            List<Category> parentCategories = categoryService.getParentCategories();
            System.out.println("Parent categories: " + parentCategories.size());
            model.addAttribute("parentCategories", parentCategories);

            // 2. Cart count
            int cartCount = 0;
            try {
                cartCount = cartService.getItemCount();
            } catch (Exception e) {
                System.out.println("Cart service error: " + e.getMessage());
            }
            model.addAttribute("cartItemCount", cartCount);
            model.addAttribute("showBanner", true);

            // 3. Xử lý wishlist
            List<Product> wishlistProducts = new ArrayList<>();
            Set<Long> wishlistProductIds = new HashSet<>();

            // Sử dụng getCurrentUserId() thay vì xử lý authentication phức tạp
            Long currentUserId = userService.getCurrentUserId();
            System.out.println("Current user ID: " + currentUserId);

            if (currentUserId != null) {
                try {
                    List<Wishlist> userWishlists = wishlistService.getWishlistByUser(currentUserId);
                    System.out.println("User wishlists: " + userWishlists.size());

                    for (Wishlist wishlist : userWishlists) {
                        Product product = productService.getProductById(wishlist.getProductId());
                        if (product != null) {
                            wishlistProducts.add(product);
                            wishlistProductIds.add(product.getProductId());
                        }
                    }
                    System.out.println("Wishlist products loaded: " + wishlistProducts.size());
                } catch (Exception e) {
                    System.out.println("Wishlist error: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            model.addAttribute("wishlistProducts", wishlistProducts.size() > 8 ?
                    wishlistProducts.subList(0, 8) : wishlistProducts);
            model.addAttribute("wishlistProductIds", wishlistProductIds);

            // 4. Showcase products
            Map<Category, List<Product>> showcase = new LinkedHashMap<>();
            for (Category parent : parentCategories) {
                try {
                    List<Product> topProducts = productService.getTop10ProductsByParentCategory(parent.getCategoryId());
                    System.out.println("Products for '" + parent.getName() + "': " + topProducts.size());
                    if (!topProducts.isEmpty()) {
                        showcase.put(parent, topProducts);
                    }
                } catch (Exception e) {
                    System.out.println("Error loading products for " + parent.getName() + ": " + e.getMessage());
                }
            }
            model.addAttribute("showcase", showcase);
            System.out.println("Showcase categories: " + showcase.size());

            System.out.println("=== DEBUG END ===");
            return "index";

        } catch (Exception e) {
            System.err.println("Fatal error in home controller: " + e.getMessage());
            e.printStackTrace();

            // Fallback
            model.addAttribute("parentCategories", new ArrayList<>());
            model.addAttribute("showcase", new LinkedHashMap<>());
            model.addAttribute("wishlistProducts", new ArrayList<>());
            model.addAttribute("wishlistProductIds", new HashSet<>());
            model.addAttribute("cartItemCount", 0);
            model.addAttribute("showBanner", true);

            return "index";
        }
    }
}