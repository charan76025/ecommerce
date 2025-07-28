package com.ecommerce.orders;

import java.util.List;

public interface OrderService {
    OrderResponseDTO placeOrder(Long userId);
    List<OrderResponseDTO>getOrdersByUser(Long userId);
    List<OrderResponseDTO>getAllOrders();
}
