package com.example.demo.controller;

import com.example.demo.model.Image;
import com.example.demo.service.ImageService;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/fragment/images")
public class AdminImageController {

    private final ImageService imageService;
    private final ProductService productService;

    public AdminImageController(ImageService imageService, ProductService productService) {
        this.imageService = imageService;
        this.productService = productService;
    }

    // Danh sách ảnh
    @GetMapping
    public String imagesFragment(Model model) {
        model.addAttribute("images", imageService.getAllImages());
        return "fragments/images :: content";
    }

    // Form tạo ảnh mới
    @GetMapping("/create")
    public String createImageFragment(Model model) {
        model.addAttribute("image", new Image());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("isEdit", false);
        return "fragments/create_image :: content";
    }

    // Xử lý tạo ảnh
    @PostMapping("/create")
    public String createImageSubmit(@ModelAttribute("image") Image image, Model model) {
        try {
            imageService.saveImage(image);
            model.addAttribute("images", imageService.getAllImages());
            model.addAttribute("success", "Tạo ảnh thành công!");
            return "fragments/images :: content";
        } catch (Exception e) {
            model.addAttribute("image", image);
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("error", "Lỗi khi tạo ảnh: " + e.getMessage());
            return "fragments/create_image :: content";
        }
    }

    // Xóa ảnh
    @PostMapping("/delete/{id}")
    public String deleteImage(@PathVariable Long id, Model model) {
        imageService.deleteImage(id);
        model.addAttribute("images", imageService.getAllImages());
        model.addAttribute("success", "Đã xoá ảnh thành công!");
        return "fragments/images :: content";
    }
}