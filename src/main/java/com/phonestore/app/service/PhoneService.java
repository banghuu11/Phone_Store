package com.phonestore.app.service;

import com.phonestore.app.exception.ResourceNotFoundException;
import com.phonestore.app.model.Phone;
import com.phonestore.app.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }

    public Phone getPhoneById(Long id) {
        return phoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phone not found with id: " + id));
    }

    public Phone createPhone(Phone phone) {
        return phoneRepository.save(phone);
    }

    public Phone updatePhone(Long id, Phone phoneDetails) {
        Phone phone = getPhoneById(id);
        
        phone.setBrand(phoneDetails.getBrand());
        phone.setModel(phoneDetails.getModel());
        phone.setPrice(phoneDetails.getPrice());
        phone.setDescription(phoneDetails.getDescription());
        phone.setStockQuantity(phoneDetails.getStockQuantity());
        phone.setImageUrl(phoneDetails.getImageUrl());
        
        return phoneRepository.save(phone);
    }

    public void deletePhone(Long id) {
        Phone phone = getPhoneById(id);
        phoneRepository.delete(phone);
    }

    public List<Phone> searchByBrand(String brand) {
        return phoneRepository.findByBrandContainingIgnoreCase(brand);
    }

    public List<Phone> searchByModel(String model) {
        return phoneRepository.findByModelContainingIgnoreCase(model);
    }

    public List<Phone> searchByPriceRange(Double minPrice, Double maxPrice) {
        return phoneRepository.findByPriceBetween(minPrice, maxPrice);
    }
}
