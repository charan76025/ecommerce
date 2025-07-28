package com.ecommerce.orders;


import com.ecommerce.cart.Cart;
import com.ecommerce.cart.CartRepository;
import com.ecommerce.user.User;
import com.ecommerce.userRepo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(Principal principal){
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("cart not found"));
        System.out.println("Cart ID: " + cart.getId());
        System.out.println("Cart Items: " + cart.getItems().size());
        cart.getItems().forEach(item ->
                System.out.println("Item -> ProductId: " + item.getProduct().getId() + ", Quantity: " + item.getQuantity()));

        if(cart.getItems().isEmpty()){
            Map<String,String> error = new HashMap<>();
            error.put("error","cart is empty. please add items before placing order");
            return ResponseEntity.badRequest().body(error);
        }
        OrderResponseDTO response = orderService.placeOrder(user.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/orders")
    public ResponseEntity<List<OrderResponseDTO>> getUserOrders(Principal principal){
        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        List<OrderResponseDTO> orders = orderService.getOrdersByUser(user.getId());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
