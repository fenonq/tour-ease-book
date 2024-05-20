package com.teb.orderservice.service;

import com.teb.orderservice.model.dto.OrderDto;
import com.teb.orderservice.model.request.CreateOrderRequest;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(CreateOrderRequest createOrderRequest);

    OrderDto cancelOrder(String id);

    List<OrderDto> findUserOrders(String userId);

}
