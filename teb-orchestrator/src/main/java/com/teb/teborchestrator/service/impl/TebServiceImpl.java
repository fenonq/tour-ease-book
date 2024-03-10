package com.teb.teborchestrator.service.impl;

import com.teb.teborchestrator.feign.HotelClient;
import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.entity.Order;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.request.GetOffersRequest;
import com.teb.teborchestrator.repository.OrderRepository;
import com.teb.teborchestrator.service.TebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TebServiceImpl implements TebService {

    private final HotelClient hotelClient;
    private final OrderRepository orderRepository;

    @Override
    public List<?> findAll(GetOffersRequest getOffersRequest) {
        return hotelClient.findAll(getOffersRequest);
    }

    @Override
    public Order<?> createOrder(CreateOrderRequest createOrderRequest) {
        HotelDto bookedHotel = hotelClient.book(
                createOrderRequest.getBookingRequest(),
                createOrderRequest.getCart().getCartItems().get(0).getId()
        );

        Order<Object> order = Order.builder()
                .userId(createOrderRequest.getBookingRequest().getUserId())
                .totalPrice(bookedHotel.getRooms().get(0).getPrice())
                .orderedItems(List.of(bookedHotel))
                .creationDateTime(LocalDateTime.now())
                .build();

        return orderRepository.save(order);
    }

}
