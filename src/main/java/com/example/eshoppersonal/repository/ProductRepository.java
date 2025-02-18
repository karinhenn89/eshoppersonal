package com.example.eshoppersonal.repository;

import com.example.eshoppersonal.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
