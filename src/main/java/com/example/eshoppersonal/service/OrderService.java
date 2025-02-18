package com.example.eshoppersonal.service;


import com.example.eshoppersonal.entity.Cart;
import com.example.eshoppersonal.entity.Order;
import com.example.eshoppersonal.entity.OrderStatus;
import com.example.eshoppersonal.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setProducts(new ArrayList<>(cart.getProducts()));
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }
}
