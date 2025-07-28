package com.ecommerce.cart;


import com.ecommerce.user.User;
import com.ecommerce.userRepo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(
            @RequestBody AddToCartRequest request,
            Principal principal
    ){
        Long productId = request.getProductId();
        int quantity = request.getQuantity();
        String email = principal.getName();
        cartService.addToCart(email,productId,quantity);
        return ResponseEntity.ok("Item added to cart");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(
            @RequestParam Long userId,
            @RequestParam Long productId
    ){
        cartService.removeFromCart(userId,productId);
        return ResponseEntity.ok("Item removed from cart");
    }

    @GetMapping("/view")
    public ResponseEntity<Cart> viewCart(Principal principal){
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        Cart cart = cartService.getCartByUser(user.getId());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(Principal principal){
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        cartService.clearCart(user.getId());
        return ResponseEntity.ok("cart cleared");
    }
}
