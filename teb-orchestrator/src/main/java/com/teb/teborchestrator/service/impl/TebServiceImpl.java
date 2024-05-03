package com.teb.teborchestrator.service.impl;

import com.teb.teborchestrator.feign.AiAssistantClient;
import com.teb.teborchestrator.feign.HotelClient;
import com.teb.teborchestrator.feign.OrderClient;
import com.teb.teborchestrator.mapper.OrderMapper;
import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.entity.CartItem;
import com.teb.teborchestrator.model.entity.Message;
import com.teb.teborchestrator.model.entity.User;
import com.teb.teborchestrator.model.request.BookingRequest;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.request.GetOffersRequest;
import com.teb.teborchestrator.model.response.ChatResponse;
import com.teb.teborchestrator.repository.OrderRepository;
import com.teb.teborchestrator.service.TebService;
import com.teb.teborchestrator.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TebServiceImpl implements TebService {

    private final HotelClient hotelClient;
    private final OrderClient orderClient;
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
        List<HotelDto> bookedHotels = new ArrayList<>();

        createOrderRequest.getCart().setUserId(user.getId());

        for (CartItem item : createOrderRequest.getCart().getCartItems()) {
            BookingRequest bookingRequest = BookingRequest.builder()
                    .userId(user.getId())
                    .roomId(item.getRoomId())
                    .from(item.getDateFrom())
                    .to(item.getDateTo())
                    .build();

            bookedHotels.add(hotelClient.book(bookingRequest, item.getOfferId()));
        }

        createOrderRequest.setBookedHotels(bookedHotels);
        return orderClient.createOrder(createOrderRequest);
    }

    @Override
    public List<OrderDto> findUserOrders() {
        User user = Utils.getCurrentUser();
        return orderClient.findUserOrders(user.getId());
    }

    @Override
    public ChatResponse chat(List<Message> prompt) {
        return aiAssistantClient.chat(prompt);
    }

}
