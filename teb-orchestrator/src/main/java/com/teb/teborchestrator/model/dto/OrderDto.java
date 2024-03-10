package com.teb.teborchestrator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
}
