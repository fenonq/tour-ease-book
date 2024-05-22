package com.teb.teborchestrator.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teb.teborchestrator.feign.AiAssistantClient;
import com.teb.teborchestrator.feign.HotelClient;
import com.teb.teborchestrator.feign.OrderClient;
import com.teb.teborchestrator.model.dto.LocationDto;
import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.dto.review.Review;
import com.teb.teborchestrator.model.dto.review.ReviewsDto;
import com.teb.teborchestrator.model.entity.Message;
import com.teb.teborchestrator.model.entity.User;
import com.teb.teborchestrator.model.request.BookingRequest;
import com.teb.teborchestrator.model.request.CancelOrderRequest;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.response.ChatResponse;
import com.teb.teborchestrator.service.TebService;
import com.teb.teborchestrator.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class TebServiceImpl implements TebService {

    @Value("${hotels.info.path}")
    private String pathToFile;

    private final HotelClient hotelClient;
    private final OrderClient orderClient;
    private final AiAssistantClient aiAssistantClient;

    @Override
    public List<HotelDto> findAll(String locationId, LocalDate dateFrom, LocalDate dateTo) {
        log.info("Finding hotels by room availability with locationId {}, dateFrom {}, dateTo {}", locationId, dateFrom, dateTo);
        return hotelClient.findAll(locationId, dateFrom, dateTo);
    }

    @Override
    public HotelDto findById(String id, LocalDate dateFrom, LocalDate dateTo) {
        log.info("Finding hotel with id {}..", id);
        return hotelClient.findById(id, dateFrom, dateTo);
    }

    @Override
    public List<HotelDto> findByIdIn(List<String> ids) {
        log.info("Finding hotels with ids {}..", ids);
        return hotelClient.findByIdIn(ids);
    }

    @Override
    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
        log.info("Creating order..");
        User user = Utils.getCurrentUser();
        List<HotelDto> bookedHotels = new ArrayList<>();

        createOrderRequest.getCart().setUserId(user.getId());
        createOrderRequest.getCart().getCartItems()
                .forEach(item -> IntStream.range(0, item.getNumberOfRooms())
                        .mapToObj(i -> BookingRequest.builder()
                                .userId(user.getId())
                                .roomId(item.getRoomId())
                                .from(item.getDateFrom())
                                .to(item.getDateTo())
                                .build())
                        .forEach(bookingRequest ->
                                bookedHotels.add(hotelClient.book(bookingRequest, item.getOfferId()))));

        createOrderRequest.setBookedHotels(bookedHotels);
        return orderClient.createOrder(createOrderRequest);
    }

    @Override
    public OrderDto cancelOrder(CancelOrderRequest cancelOrderRequest) {
        User user = Utils.getCurrentUser();
        cancelOrderRequest.setUserId(user.getId());
        OrderDto orderDto = orderClient.cancelOrder(cancelOrderRequest.getOrderId());
        hotelClient.cancelBooking(cancelOrderRequest);
        return orderDto;
    }

    @Override
    public List<OrderDto> findUserOrders() {
        log.info("Finding user orders..");
        User user = Utils.getCurrentUser();
        return orderClient.findUserOrders(user.getId());
    }

    @Override
    public ChatResponse chat(List<Message> prompt) {
        log.info("Sending request to ai assistant..");
        return aiAssistantClient.chat(prompt);
    }

    @Override
    public ReviewsDto findByHotelId(String hotelId) {
        log.info("Finding reviews by hotelId {}..", hotelId);
        return hotelClient.findByHotelId(hotelId);
    }

    @Override
    public ReviewsDto addReview(Review review, String hotelId) {
        log.info("Adding review to the hotel with id {}..", hotelId);
        User user = Utils.getCurrentUser();
        review.setUsername(user.getUsername());
        return hotelClient.addReview(review, hotelId);
    }

    @Override
    public List<LocationDto> findAllLocations() {
        return hotelClient.findAll();
    }

    @Override
    public void writeHotelsToFile() {
        try {
            List<HotelDto> hotels = hotelClient.findAllHotels();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(pathToFile), hotels);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
