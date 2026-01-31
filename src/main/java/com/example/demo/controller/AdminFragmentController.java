package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.service.CommentService;
import com.example.demo.service.FeedbackService;
import com.example.demo.service.OrderService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Order;

@Controller
@RequestMapping("/admin/fragment")
public class AdminFragmentController {

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final FeedbackService feedbackService;
    private final OrderService orderService;

    public AdminFragmentController(UserService userService, ProductService productService,
                                   CategoryService categoryService, CommentService commentService, FeedbackService feedbackService,
                                   OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.feedbackService = feedbackService;
        this.orderService = orderService;
    }

    // --- Custom Binder để chuyển categoryId thành Category ---

    // ======================= Quản lý Người dùng =======================

    @GetMapping("/users")
    public String usersFragment(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "fragments/users :: content";
    }

    @GetMapping("/users/create")
    public String createUserFragment(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("isEdit", false);
        return "fragments/create_user :: content";
    }

    @GetMapping("/users/edit/{userId}")
    public String editUserFragment(@PathVariable Long userId, Model model) {
        User user = userService.getUserById(userId);
        if (user == null) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("error", "Không tìm thấy người dùng.");
            return "fragments/users :: content";
        }
        model.addAttribute("user", user);
        model.addAttribute("isEdit", true);
        return "fragments/create_user :: content";
    }

    @PostMapping("/users/create")
    public String createUserSubmit(@ModelAttribute("user") User user, Model model) {
        try {
            userService.createUser(user);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("success", "Tạo người dùng thành công!");
            return "fragments/users :: content";
        } catch (Exception e) {
            model.addAttribute("user", user);
            model.addAttribute("isEdit", false);
            model.addAttribute("error", "Lỗi khi tạo người dùng: " + e.getMessage());
            return "fragments/create_user :: content";
        }
    }

    @PostMapping("/users/edit/{userId}")
    public String editUserSubmit(@PathVariable Long userId, @ModelAttribute("user") User user, Model model) {
        try {
            userService.updateUser(userId, user);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("success", "Cập nhật người dùng thành công!");
            return "fragments/users :: content";
        } catch (Exception e) {
            model.addAttribute("user", user);
            model.addAttribute("isEdit", true);
            model.addAttribute("error", "Lỗi khi cập nhật người dùng: " + e.getMessage());
            return "fragments/create_user :: content";
        }
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("success", "Đã xoá người dùng thành công!");
        return "fragments/users :: content";
    }

    // ======================= Quản lý Sản phẩm =======================

    @GetMapping("/products")
    public String productsFragment(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "fragments/products :: content";
    }

    @GetMapping("/products/create")
    public String createProductFragment(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("isEdit", false);
        return "fragments/create_product :: content";
    }

    @GetMapping("/products/edit/{productId}")
    public String editProductFragment(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("error", "Không tìm thấy sản phẩm.");
            return "fragments/products :: content";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("isEdit", true);
        return "fragments/create_product :: content";
    }

    @PostMapping("/products/create")
    public String createProductSubmit(@ModelAttribute("product") Product product, Model model) {
        try {
            productService.createProduct(product);
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("success", "Tạo sản phẩm thành công!");
            return "fragments/products :: content";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("isEdit", false);
            model.addAttribute("error", "Lỗi khi tạo sản phẩm: " + e.getMessage());
            return "fragments/create_product :: content";
        }
    }

    @PostMapping("/products/edit/{productId}")
    public String editProductSubmit(@PathVariable Long productId, @ModelAttribute("product") Product product,
                                    Model model) {
        try {
            productService.updateProduct(productId, product);
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("success", "Cập nhật sản phẩm thành công!");
            return "fragments/products :: content";
        } catch (Exception e) {
            model.addAttribute("product", product);
            model.addAttribute("isEdit", true);
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("error", "Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            return "fragments/create_product :: content";
        }
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        productService.deleteProduct(id);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("success", "Đã xoá sản phẩm thành công!");
        return "fragments/products :: content";
    }

    // ======================= Quản lý Đơn hàng =======================

    @GetMapping("/orders")
    public String ordersFragment(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "fragments/orders :: content";
    }

    @GetMapping("/orders/create")
    public String createOrderFragment(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("isEdit", false);
        return "fragments/create_order :: content";
    }

    @PostMapping("/orders/create")
    public String createOrderSubmit(@ModelAttribute("order") Order order, @RequestParam Long userId, Model model) {
        order.setUser(userService.getUserById(userId));
        orderService.createOrder(order);
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("success", "Tạo đơn hàng thành công!");
        return "fragments/orders :: content";
    }

    @GetMapping("/orders/edit/{id}")
    public String editOrderFragment(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("error", "Không tìm thấy đơn hàng.");
            return "fragments/orders :: content";
        }
        model.addAttribute("order", order);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("isEdit", true);
        return "fragments/create_order :: content";
    }

    @PostMapping("/orders/edit/{id}")
    public String editOrderSubmit(@PathVariable Long id, @ModelAttribute("order") Order orderForm,
                                  @RequestParam Long userId, Model model) {
        Order existing = orderService.getOrderById(id);
        if (existing == null) {
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("error", "Không tìm thấy đơn hàng.");
            return "fragments/orders :: content";
        }
        existing.setUser(userService.getUserById(userId));
        existing.setShippingAddress(orderForm.getShippingAddress());
        existing.setTotalAmount(orderForm.getTotalAmount());
        existing.setStatus(orderForm.getStatus());
        existing.setPaymentMethod(orderForm.getPaymentMethod());
        existing.setPaymentStatus(orderForm.getPaymentStatus());
        existing.setNote(orderForm.getNote());
        orderService.updateOrder(existing);
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("success", "Cập nhật đơn hàng thành công!");
        return "fragments/orders :: content";
    }

    @PostMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id, Model model) {
        orderService.deleteOrder(id);
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("success", "Đã xoá đơn hàng!");
        return "fragments/orders :: content";
    }

    @PostMapping("/orders/confirm/{id}")
    public String confirmOrder(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            order.setStatus("CONFIRMED");
            orderService.updateOrder(order);
            model.addAttribute("success", "Đã xác nhận đơn hàng!");
        }
        model.addAttribute("orders", orderService.getAllOrders());
        return "fragments/orders :: content";
    }

    // ======================= Quản lý Bình luận =======================
    @GetMapping("/comments")
    public String commentsFragment(Model model) {
        model.addAttribute("comments", commentService.getAllComments());
        return "fragments/comments :: content";
    }

    @PostMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable Long id, Model model) {
        commentService.deleteComment(id);
        model.addAttribute("comments", commentService.getAllComments());
        model.addAttribute("success", "Đã xoá bình luận!");
        return "fragments/comments :: content";
    }

    @PostMapping("/comments/reply/{id}")
    public String replyComment(@PathVariable Long id, @RequestParam String reply, Model model) {
        commentService.replyComment(id, reply);
        model.addAttribute("comments", commentService.getAllComments());
        model.addAttribute("success", "Đã phản hồi bình luận!");
        return "fragments/comments :: content";
    }

    // ======================= Feedback =======================
    @GetMapping("/feedbacks")
    public String feedbackFragment(Model model) {
        model.addAttribute("feedbacks", feedbackService.getAll());
        return "fragments/feedbacks :: content";
    }

    @PostMapping("/feedbacks/reply/{id}")
    public String replyFeedback(@PathVariable Long id, @RequestParam String reply, Model model) {
        feedbackService.reply(id, reply);
        model.addAttribute("feedbacks", feedbackService.getAll());
        model.addAttribute("success", "Đã phản hồi!");
        return "fragments/feedbacks :: content";
    }
}
