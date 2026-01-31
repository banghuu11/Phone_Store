package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(CartService cartService, OrderService orderService, UserService userService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam String fullName,
            @RequestParam String phone,
            @RequestParam String addressDetail,
            @RequestParam String province,
            @RequestParam String district,
            @RequestParam String ward,
            @RequestParam String paymentMethod,
            @RequestParam(required = false) String note,
            RedirectAttributes redirectAttributes) {
        if (cartService.getCartItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng trống");
            return "redirect:/cart";
        }

        Order order = new Order();
        // Lấy user hiện tại nếu đăng nhập
        User currentUser = userService.getCurrentAuthenticatedUser();
        order.setUser(currentUser);
        order.setShippingAddress(addressDetail + ", " + ward + ", " + district + ", " + province);
        order.setShippingMethod("STANDARD");
        order.setShippingFee(BigDecimal.ZERO);
        order.setStatus("PENDING");
        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus("UNPAID");
        order.setNote(note);

        // Thêm các mặt hàng từ giỏ hàng vào đơn hàng
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartService.CartItem cartItem : cartService.getCartItems()) {
            com.example.demo.model.Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();
            BigDecimal price = product.getPrice();
            BigDecimal discountPercent = product.getDiscount() != null ? product.getDiscount() : BigDecimal.ZERO;

            BigDecimal priceAfterDiscount = price
                    .multiply(BigDecimal.ONE.subtract(discountPercent.divide(new BigDecimal("100"))));
            BigDecimal subtotal = priceAfterDiscount.multiply(BigDecimal.valueOf(quantity));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(price);
            orderItem.setDiscount(discountPercent);
            orderItem.setSubtotal(subtotal);

            order.addItem(orderItem);

            totalAmount = totalAmount.add(subtotal);
        }

        order.setTotalAmount(totalAmount);

        orderService.createOrder(order);
        // Clear cart after checkout
        cartService.clearCart();

        redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công! Đơn hàng đang chờ xác nhận.");
        return "redirect:/";
    }



}