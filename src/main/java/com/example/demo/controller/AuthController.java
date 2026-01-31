package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Home")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 👉 Trang đăng nhập
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";  // /templates/auth/login.html
    }

    // 👉 Trang đăng ký
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";  // /templates/auth/register.html
    }

    // 👉 Xử lý đăng ký người dùng
    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("user") User user, Model model) {
        try {
            // Gợi ý: bạn nên hash mật khẩu tại đây nếu chưa làm
            userService.createUser(user);
            model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi đăng ký: " + e.getMessage());
            return "auth/register";
        }
    }
}
