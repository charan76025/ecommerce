package com.ecommerce.orders;

import com.ecommerce.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double quantity;

    private double price;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;
}
