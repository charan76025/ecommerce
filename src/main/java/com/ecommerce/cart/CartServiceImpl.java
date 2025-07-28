package com.ecommerce.cart;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.user.User;
import com.ecommerce.userRepo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService{
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Override
    public void addToCart(String email, Long productId, int quantity){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product not found"));

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId)).findFirst();

        if(existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }
        cartRepository.save(cart);
    }

    @Override
    public void removeFromCart(Long userId,Long productId){
        Cart cart = cartRepository.findByUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"))
        ).orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        return cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("cart not found"));
    }

    @Override
    public void clearCart(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
