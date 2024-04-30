package com.teb.teborchestrator.service;

import com.teb.teborchestrator.model.dto.Offer;
import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.request.GetOffersRequest;

import java.util.List;

public interface TebService {

    List<?> findAll(GetOffersRequest getOffersRequest);

    Offer findById(String id);

    List<?> findByIdIn(List<String> ids);

    OrderDto createOrder(CreateOrderRequest createOrderRequest);

    List<OrderDto> findUserOrders();

}
