package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }// Nếu bạn cần xử lý GET request

    // Nếu bạn muốn cho phép POST request (ví dụ, khi htmx gửi form hoặc cập nhật dữ liệu)
    @PostMapping
    public String updateDashboard() {
        // Xử lý logic cập nhật dashboard
        return "admin/dashboard"; // hoặc trả về fragment nếu dùng htmx
    }
}