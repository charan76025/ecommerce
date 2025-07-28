package com.ecommerce.orders;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private List<OrderItemDTO>items;

    public OrderResponseDTO(Long orderId, LocalDateTime orderDate, Double totalAmount, List<OrderItemDTO> items){
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.items = items;
    }
}
