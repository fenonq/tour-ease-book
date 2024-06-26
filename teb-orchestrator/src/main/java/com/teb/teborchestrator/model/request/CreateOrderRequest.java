package com.teb.teborchestrator.model.request;

import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.entity.Cart;
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
