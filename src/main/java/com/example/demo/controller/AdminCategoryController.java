package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ======================= Quản lý Danh mục =======================

    @GetMapping
    public String categoriesFragment(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "fragments/categories :: content";
    }

    @GetMapping("/create")
    public String createCategoryFragment(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.getParentCategories());
        return "fragments/create_category :: content";
    }

    @GetMapping("/edit/{categoryId}")
    public String editCategoryFragment(@PathVariable Long categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("error", "Không tìm thấy danh mục.");
            return "fragments/categories :: content";
        }
        model.addAttribute("category", category);
        model.addAttribute("categories", categoryService.getParentCategories());
        return "fragments/create_category :: content";
    }

    @PostMapping("/create")
    public String createCategorySubmit(@ModelAttribute("category") Category category, Model model) {
        try {
            categoryService.createCategory(category);
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("success", "Tạo danh mục thành công!");
            return "fragments/categories :: content";
        } catch (Exception e) {
            model.addAttribute("category", category);
            model.addAttribute("categories", categoryService.getParentCategories());
            model.addAttribute("error", "Lỗi khi tạo danh mục: " + e.getMessage());
            return "fragments/create_category :: content";
        }
    }

    @PostMapping("/edit/{categoryId}")
    public String editCategorySubmit(@PathVariable Long categoryId, @ModelAttribute("category") Category category,
            Model model) {
        try {
            categoryService.updateCategory(categoryId, category);
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("success", "Cập nhật danh mục thành công!");
            return "fragments/categories :: content";
        } catch (Exception e) {
            model.addAttribute("category", category);
            model.addAttribute("categories", categoryService.getParentCategories());
            model.addAttribute("error", "Lỗi khi cập nhật danh mục: " + e.getMessage());
            return "fragments/create_category :: content";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        categoryService.deleteCategory(id);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("success", "Đã xoá danh mục thành công!");
        return "fragments/categories :: content";
    }
}