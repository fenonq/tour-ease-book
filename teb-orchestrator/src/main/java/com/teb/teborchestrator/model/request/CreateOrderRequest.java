package com.teb.teborchestrator.model.request;

import com.teb.teborchestrator.model.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private BookingRequest bookingRequest;
    private Cart cart;
}
