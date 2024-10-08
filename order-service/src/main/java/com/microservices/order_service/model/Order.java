package com.microservices.order_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.math.BigDecimal;

@Entity
@Table(name = "t_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_number", length = 255)
    private String orderNumber;
    @Column(name = "sku_code", length = 255)
    private String skuCode;
    @Column(name = "price", precision = 19, scale = 2)
    private BigDecimal price;
    @Column(name = "quantity")
    private Integer quantity;
}
