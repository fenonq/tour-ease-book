package com.teb.teborchestrator.service.impl;

import com.teb.aiassistantservice.model.ChatResponse;
import com.teb.aiassistantservice.model.Message;
import com.teb.teborchestrator.feign.AiAssistantClient;
import com.teb.teborchestrator.feign.HotelClient;
import com.teb.teborchestrator.mapper.OrderMapper;
import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.entity.*;
import com.teb.teborchestrator.model.request.BookingRequest;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.request.GetOffersRequest;
import com.teb.teborchestrator.repository.OrderRepository;
import com.teb.teborchestrator.service.TebService;
import com.teb.teborchestrator.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.teb.teborchestrator.util.Utils.getNumberOfNights;

@Slf4j
@Service
@RequiredArgsConstructor
public class TebServiceImpl implements TebService {

    private final HotelClient hotelClient;
    private final AiAssistantClient aiAssistantClient;
    private final OrderRepository orderRepository;

    @Override
    public List<HotelDto> findAll(GetOffersRequest getOffersRequest) {
        return hotelClient.findAll(getOffersRequest);
    }

    @Override
    public HotelDto findById(String id) {
        return hotelClient.findById(id);
    }

    @Override
    public List<HotelDto> findByIdIn(List<String> ids) {
        return hotelClient.findByIdIn(ids);
    }

    @Override
    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
        User user = Utils.getCurrentUser();
        List<OrderedItem> bookedHotels = new ArrayList<>();

        createOrderRequest.getCart().setUserId(user.getId());

        for (CartItem item : createOrderRequest.getCart().getCartItems()) {
            BookingRequest bookingRequest = BookingRequest.builder()
                    .userId(user.getId())
                    .roomId(item.getRoomId())
                    .from(item.getDateFrom())
                    .to(item.getDateTo())
                    .build();

            bookedHotels.add(OrderedItem.builder()
                    .dateFrom(item.getDateFrom())
                    .dateTo(item.getDateTo())
                    .offer(hotelClient.book(bookingRequest, item.getOfferId()))
                    .build());
        }

        Order order = Order.builder()
                .userId(user.getId())
                .totalPrice(
                        bookedHotels.stream().map(OrderedItem::getOffer).toList().stream()
                                .flatMapToDouble(hotel -> hotel.getRooms().stream()
                                        .mapToDouble(room -> createOrderRequest.getCart().getCartItems().stream()
                                                .filter(cartItem -> cartItem.getOfferId().equals(hotel.getId()) && cartItem.getRoomId().equals(room.getRoomId()))
                                                .findFirst()
                                                .map(item -> room.getPrice() * getNumberOfNights(item.getDateFrom(), item.getDateTo()))
                                                .orElse(0.0))).sum())
                .orderedItems(new ArrayList<>(bookedHotels))
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

    @Override
    public ChatResponse chat(List<Message> prompt) {
        return aiAssistantClient.chat(prompt);
    }

}
