package com.teb.hotelservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
