package com.teb.orderservice.model.entity;

import com.teb.orderservice.model.dto.hotel.HotelDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderedItem {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int numberOfRooms;
    private HotelDto offer;
}
