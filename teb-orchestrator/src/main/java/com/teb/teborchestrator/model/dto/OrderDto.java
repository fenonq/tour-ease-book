package com.teb.teborchestrator.model.dto;

import com.teb.teborchestrator.model.entity.OrderedItem;
import com.teb.teborchestrator.model.enums.OrderStatus;
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
    private OrderStatus status;
    private String userId;
    private double totalPrice;
    private List<OrderedItem> orderedItems;
    private LocalDateTime creationDateTime;
}
