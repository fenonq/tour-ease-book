package com.teb.orderservice.model.request;

import com.teb.orderservice.model.dto.hotel.HotelDto;
import com.teb.orderservice.model.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private Cart cart;
    private List<HotelDto> bookedHotels;
}
