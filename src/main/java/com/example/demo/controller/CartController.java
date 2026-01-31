package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final com.example.demo.repository.CouponRepository couponRepository;

    @Autowired
    public CartController(CartService cartService, ProductService productService,
            com.example.demo.repository.CouponRepository couponRepository) {
        this.cartService = cartService;
        this.productService = productService;
        this.couponRepository = couponRepository;
    }

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("total", cartService.getTotal());
        return "cart";
    }

    @PostMapping("/add/{productId}")
    @ResponseBody
    public java.util.Map<String, Object> addToCart(@PathVariable Long productId,
            @RequestParam(defaultValue = "1") int quantity) {
        java.util.Map<String, Object> resp = new java.util.HashMap<>();
        Product product = productService.getProductById(productId);
        if (product == null) {
            resp.put("success", false);
            resp.put("message", "Sản phẩm không tồn tại");
            return resp;
        }
        cartService.addToCart(product, quantity);
        resp.put("success", true);
        resp.put("itemCount", cartService.getItemCount());
        resp.put("message", "Đã thêm sản phẩm vào giỏ hàng!");
        return resp;
    }

    @PostMapping("/update/{productId}")
    @ResponseBody
    public String updateQuantity(@PathVariable Long productId, @RequestParam int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "success";
    }

    @PostMapping("/remove/{productId}")
    @ResponseBody
    public String removeFromCart(@PathVariable Long productId) {
        cartService.removeFromCart(productId);
        return "success";
    }

    @PostMapping("/clear")
    @ResponseBody
    public String clearCart() {
        cartService.clearCart();
        return "success";
    }

    // ================= Coupon apply =================

    @PostMapping("/apply-coupon")
    @ResponseBody
    public java.util.Map<String, Object> applyCoupon(@RequestParam String code) {
        java.util.Map<String, Object> resp = new java.util.HashMap<>();

        java.util.Optional<com.example.demo.model.Coupon> couponOpt = couponRepository.findByCode(code.trim());
        if (couponOpt.isEmpty()) {
            resp.put("success", false);
            resp.put("message", "Mã giảm giá không tồn tại");
            return resp;
        }

        com.example.demo.model.Coupon coupon = couponOpt.get();
        java.math.BigDecimal total = cartService.getTotal();
        java.time.LocalDate today = java.time.LocalDate.now();

        boolean valid = coupon.getStatus() &&
                (today.isEqual(coupon.getStartDate()) || today.isAfter(coupon.getStartDate())) &&
                (today.isEqual(coupon.getEndDate()) || today.isBefore(coupon.getEndDate())) &&
                total.compareTo(coupon.getMinOrderValue()) >= 0 &&
                (coupon.getUsageLimit() == null || coupon.getUsedCount() < coupon.getUsageLimit());

        if (!valid) {
            resp.put("success", false);
            resp.put("message", "Mã giảm giá không hợp lệ hoặc không áp dụng cho đơn hàng này");
            return resp;
        }

        java.math.BigDecimal discount = total
                .multiply(java.math.BigDecimal.valueOf(coupon.getDiscountPercent())
                        .divide(java.math.BigDecimal.valueOf(100)));
        if (discount.compareTo(coupon.getMaxDiscount()) > 0) {
            discount = coupon.getMaxDiscount();
        }
        java.math.BigDecimal finalTotal = total.subtract(discount);

        resp.put("success", true);
        resp.put("message", "Áp dụng mã giảm giá thành công");
        resp.put("discountAmount", discount);
        resp.put("finalTotal", finalTotal);
        return resp;
    }
}