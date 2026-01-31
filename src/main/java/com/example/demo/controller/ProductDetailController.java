package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CartService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CommentService;
import com.example.demo.service.ImageService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/detail")
public class ProductDetailController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;
    private final CommentService commentService;
    private final ImageService imageService;

    @Autowired
    public ProductDetailController(ProductService productService,
            CategoryService categoryService,
            CartService cartService,
            CommentService commentService,
            ImageService imageService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.commentService = commentService;
        this.imageService = imageService;
    }

    @GetMapping("/product/{productId}")
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

        // Lấy danh sách ảnh phụ cho sản phẩm
        model.addAttribute("images", imageService.getImagesByProduct(productId));

        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);
        model.addAttribute("comments", commentService.getCommentsByProductId(productId));
        model.addAttribute("cartItemCount", cartService.getItemCount());

        return "product-detail"; // Tên file HTML (product-detail.html)
    }

    @PostMapping("/product/{productId}/comment")
    public String postComment(@PathVariable Long productId,
            @RequestParam String content,
            @RequestParam(defaultValue = "Ẩn danh") String username) {
        commentService.addComment(productId, username, content);
        return "redirect:/detail/product/" + productId;
    }
}