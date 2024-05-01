package com.teb.teborchestrator.service;

import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.request.GetOffersRequest;

import java.util.List;

public interface TebService {

    List<HotelDto> findAll(GetOffersRequest getOffersRequest);

    HotelDto findById(String id);

    List<HotelDto> findByIdIn(List<String> ids);

    OrderDto createOrder(CreateOrderRequest createOrderRequest);

    List<OrderDto> findUserOrders();

}
