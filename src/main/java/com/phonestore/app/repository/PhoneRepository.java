package com.phonestore.app.repository;

import com.phonestore.app.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    
    List<Phone> findByBrandContainingIgnoreCase(String brand);
    
    List<Phone> findByModelContainingIgnoreCase(String model);
    
    List<Phone> findByPriceBetween(Double minPrice, Double maxPrice);
}
