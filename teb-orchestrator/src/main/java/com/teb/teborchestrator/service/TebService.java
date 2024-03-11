package com.teb.teborchestrator.service;

import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.request.GetOffersRequest;

import java.util.List;

public interface TebService {

    List<?> findAll(GetOffersRequest getOffersRequest);

    OrderDto createOrder(CreateOrderRequest createOrderRequest);

    List<OrderDto> findUserOrders();

}
