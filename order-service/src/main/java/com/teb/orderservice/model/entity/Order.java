package com.teb.orderservice.model.entity;

import com.teb.orderservice.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;
    private OrderStatus status;
    private String userId;
    private double totalPrice;
    private List<OrderedItem> orderedItems;
    private LocalDateTime creationDateTime;
}
