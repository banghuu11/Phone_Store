package com.phonestore.app.controller;

import com.phonestore.app.model.Phone;
import com.phonestore.app.service.PhoneService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phones")
public class PhoneController {

    private final PhoneService phoneService;

    @Autowired
    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping
    public ResponseEntity<List<Phone>> getAllPhones() {
        List<Phone> phones = phoneService.getAllPhones();
        return ResponseEntity.ok(phones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Phone> getPhoneById(@PathVariable Long id) {
        Phone phone = phoneService.getPhoneById(id);
        return ResponseEntity.ok(phone);
    }

    @PostMapping
    public ResponseEntity<Phone> createPhone(@Valid @RequestBody Phone phone) {
        Phone createdPhone = phoneService.createPhone(phone);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPhone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Phone> updatePhone(@PathVariable Long id, @Valid @RequestBody Phone phoneDetails) {
        Phone updatedPhone = phoneService.updatePhone(id, phoneDetails);
        return ResponseEntity.ok(updatedPhone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id) {
        phoneService.deletePhone(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/brand")
    public ResponseEntity<List<Phone>> searchByBrand(@RequestParam @jakarta.validation.constraints.NotBlank(message = "Brand parameter cannot be blank") String brand) {
        List<Phone> phones = phoneService.searchByBrand(brand);
        return ResponseEntity.ok(phones);
    }

    @GetMapping("/search/model")
    public ResponseEntity<List<Phone>> searchByModel(@RequestParam @jakarta.validation.constraints.NotBlank(message = "Model parameter cannot be blank") String model) {
        List<Phone> phones = phoneService.searchByModel(model);
        return ResponseEntity.ok(phones);
    }

    @GetMapping("/search/price")
    public ResponseEntity<List<Phone>> searchByPriceRange(
            @RequestParam @Positive(message = "Minimum price must be positive") Double minPrice, 
            @RequestParam @Positive(message = "Maximum price must be positive") Double maxPrice) {
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Minimum price cannot be greater than maximum price");
        }
        List<Phone> phones = phoneService.searchByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(phones);
    }
}
