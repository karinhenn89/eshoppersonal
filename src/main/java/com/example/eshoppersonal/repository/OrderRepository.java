package com.example.eshoppersonal.repository;

import com.example.eshoppersonal.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
