package com.teb.teborchestrator.model.dto;

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
    private List<Offer> orderedItems;
    private LocalDateTime creationDateTime;
}
