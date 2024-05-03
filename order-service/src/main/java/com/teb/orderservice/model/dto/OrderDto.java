package com.teb.orderservice.model.dto;

import com.teb.orderservice.model.entity.OrderedItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String id;
    private String userId;
    private double totalPrice;
    private List<OrderedItem> orderedItems;
    private LocalDateTime creationDateTime;
}
