package com.phonestore.app.controller;

import com.phonestore.app.model.Phone;
import com.phonestore.app.service.PhoneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phones")
@CrossOrigin(origins = "*")
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
    public ResponseEntity<List<Phone>> searchByBrand(@RequestParam String brand) {
        List<Phone> phones = phoneService.searchByBrand(brand);
        return ResponseEntity.ok(phones);
    }

    @GetMapping("/search/model")
    public ResponseEntity<List<Phone>> searchByModel(@RequestParam String model) {
        List<Phone> phones = phoneService.searchByModel(model);
        return ResponseEntity.ok(phones);
    }

    @GetMapping("/search/price")
    public ResponseEntity<List<Phone>> searchByPriceRange(
            @RequestParam Double minPrice, 
            @RequestParam Double maxPrice) {
        List<Phone> phones = phoneService.searchByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(phones);
    }
}
