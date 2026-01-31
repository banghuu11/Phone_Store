package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public ProfileController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    // Trang hiển thị hồ sơ
    @GetMapping
    public String viewProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        if (user != null) {
            model.addAttribute("orders", orderService.getOrdersByUser(user));
        }
        model.addAttribute("showBanner", false);
        return "auth/profile";
    }

    // Form chỉnh sửa hồ sơ
    @GetMapping("/edit")
    public String editProfileForm(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "auth/edit_profile";
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute("user") User formUser, Principal principal, Model model) {
        User currentUser = userService.findByUsername(principal.getName());

        if (currentUser == null) {
            model.addAttribute("error", "Không tìm thấy người dùng.");
            return "auth/edit_profile";
        }

        // Cập nhật thông tin cho phép
        currentUser.setFullName(formUser.getFullName());
        currentUser.setPhone(formUser.getPhone());
        currentUser.setAddress(formUser.getAddress());
        currentUser.setAvatar(formUser.getAvatar());

        userService.save(currentUser);
        return "redirect:/profile";
    }
}