package com.example.demo.controller;

import com.example.demo.model.Coupon;
import com.example.demo.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/coupons")
public class AdminCouponController {

    private final CouponRepository couponRepository;

    @Autowired
    public AdminCouponController(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    // ======================= [1] Danh sách phiếu =======================

    @GetMapping
    public String showCouponList(Model model) {
        model.addAttribute("coupons", couponRepository.findAll());
        return "fragments/coupons :: content";
    }

    // ======================= [2] Form tạo mới =======================

    @GetMapping("/create")
    public String createCouponForm(Model model) {
        model.addAttribute("coupon", new Coupon());
        return "fragments/create_coupon :: content";
    }

    // ======================= [3] Xử lý tạo mới =======================

    @PostMapping("/create")
    public String createCoupon(@ModelAttribute("coupon") Coupon coupon, Model model) {
        try {
            if (coupon.getDiscountPercent() <= 0 || coupon.getDiscountPercent() > 100) {
                model.addAttribute("error", "Phần trăm giảm giá phải từ 1 đến 100.");
                model.addAttribute("coupon", coupon);
                return "fragments/create_coupon :: content";
            }

            couponRepository.save(coupon);
            model.addAttribute("success", "Tạo phiếu giảm giá thành công!");
            model.addAttribute("coupons", couponRepository.findAll());
            return "fragments/coupons :: content";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tạo phiếu: " + e.getMessage());
            model.addAttribute("coupon", coupon);
            return "fragments/create_coupon :: content";
        }
    }

    // ======================= [4] Form sửa =======================

    @GetMapping("/edit/{id}")
    public String editCouponForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Coupon> couponOpt = couponRepository.findById(id);
        if (couponOpt.isPresent()) {
            model.addAttribute("coupon", couponOpt.get());
            return "fragments/create_coupon :: content";
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy phiếu giảm giá.");
            return "redirect:/admin/coupons";
        }
    }

    // ======================= [5] Xử lý cập nhật =======================

    @PostMapping("/edit/{id}")
    public String updateCoupon(@PathVariable Long id,
            @ModelAttribute("coupon") Coupon coupon, Model model) {
        try {
            coupon.setCouponId(id);

            if (coupon.getDiscountPercent() <= 0 || coupon.getDiscountPercent() > 100) {
                model.addAttribute("error", "Phần trăm giảm giá phải từ 1 đến 100.");
                model.addAttribute("coupon", coupon);
                return "fragments/create_coupon :: content";
            }

            couponRepository.save(coupon);
            model.addAttribute("success", "Cập nhật phiếu thành công!");
            model.addAttribute("coupons", couponRepository.findAll());
            return "fragments/coupons :: content";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi cập nhật: " + e.getMessage());
            model.addAttribute("coupon", coupon);
            return "fragments/create_coupon :: content";
        }
    }

    // ======================= [6] Xoá phiếu =======================

    @PostMapping("/delete/{id}")
    public String deleteCoupon(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            couponRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xoá phiếu thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xoá phiếu: " + e.getMessage());
        }
        return "redirect:/admin/coupons";
    }
}
