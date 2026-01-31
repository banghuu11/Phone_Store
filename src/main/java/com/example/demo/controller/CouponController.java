package com.example.demo.controller;

import com.example.demo.model.Coupon;
import com.example.demo.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin(origins = "*") // Cho phép gọi từ frontend (có thể giới hạn domain cụ thể)
public class CouponController {

    @Autowired
    private CouponRepository couponRepository;

    // Lấy tất cả coupon
    @GetMapping
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    // Lấy coupon theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
        Optional<Coupon> coupon = couponRepository.findById(id);
        return coupon.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Thêm mới coupon
    @PostMapping
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        return ResponseEntity.ok(couponRepository.save(coupon));
    }

    // Cập nhật coupon
    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody Coupon updatedCoupon) {
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);

        if (optionalCoupon.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Coupon existingCoupon = optionalCoupon.get();
        existingCoupon.setCode(updatedCoupon.getCode());
        existingCoupon.setDescription(updatedCoupon.getDescription());
        existingCoupon.setDiscountPercent(updatedCoupon.getDiscountPercent());
        existingCoupon.setMaxDiscount(updatedCoupon.getMaxDiscount());
        existingCoupon.setMinOrderValue(updatedCoupon.getMinOrderValue());
        existingCoupon.setStartDate(updatedCoupon.getStartDate());
        existingCoupon.setEndDate(updatedCoupon.getEndDate());
        existingCoupon.setUsageLimit(updatedCoupon.getUsageLimit());
        existingCoupon.setUsedCount(updatedCoupon.getUsedCount());
        existingCoupon.setStatus(updatedCoupon.getStatus());

        return ResponseEntity.ok(couponRepository.save(existingCoupon));
    }

    // Xóa coupon
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        if (!couponRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        couponRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
