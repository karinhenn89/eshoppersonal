package com.example.eshoppersonal.service;


import com.example.eshoppersonal.entity.Cart;
import com.example.eshoppersonal.entity.Product;
import com.example.eshoppersonal.repository.CartRepository;
import com.example.eshoppersonal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public Cart addToCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        cart.getProducts().add(product);
        return cartRepository.save(cart);
    }

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Kasutaja ostukorvi ei leitud"));
    }
}
