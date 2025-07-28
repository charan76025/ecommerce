package com.ecommerce.cart;

import java.security.Principal;

public interface CartService {
    void addToCart(String email,Long productId,int quantity);
    void removeFromCart(Long userId,Long productId);
    Cart getCartByUser(Long userId);
    void clearCart(Long userId);
}
