package com.teb.teborchestrator.service.impl;

import com.teb.teborchestrator.feign.HotelClient;
import com.teb.teborchestrator.mapper.OrderMapper;
import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.entity.Order;
import com.teb.teborchestrator.model.entity.User;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.request.GetOffersRequest;
import com.teb.teborchestrator.repository.OrderRepository;
import com.teb.teborchestrator.service.TebService;
import com.teb.teborchestrator.util.Utils;
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
    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
        User user = Utils.getCurrentUser();
        createOrderRequest.getBookingRequest().setUserId(user.getId());

        HotelDto bookedHotel = hotelClient.book(
                createOrderRequest.getBookingRequest(),
                createOrderRequest.getCart().getCartItems().get(0).getId()
        );

        Order order = Order.builder()
                .userId(createOrderRequest.getBookingRequest().getUserId())
                .totalPrice(bookedHotel.getRooms().get(0).getPrice())
                .orderedItems(List.of(bookedHotel))
                .creationDateTime(LocalDateTime.now())
                .build();

        Order orderToReturn = orderRepository.save(order);
        return OrderMapper.INSTANCE.mapOrderToOrderDto(orderToReturn);
    }

    @Override
    public List<OrderDto> findUserOrders() {
        User user = Utils.getCurrentUser();
        return orderRepository.findByUserId(user.getId()).stream()
                .map(OrderMapper.INSTANCE::mapOrderToOrderDto)
                .toList();
    }

}
