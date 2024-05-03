package com.teb.teborchestrator.service;

import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.entity.Message;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.response.ChatResponse;

import java.time.LocalDate;
import java.util.List;

public interface TebService {

    List<HotelDto> findAll(int locationId, LocalDate dateFrom, LocalDate dateTo);

    HotelDto findById(String id);

    List<HotelDto> findByIdIn(List<String> ids);

    OrderDto createOrder(CreateOrderRequest createOrderRequest);

    List<OrderDto> findUserOrders();

    ChatResponse chat(List<Message> prompt);

}
