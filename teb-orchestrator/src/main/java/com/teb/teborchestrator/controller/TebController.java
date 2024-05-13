package com.teb.teborchestrator.controller;

import com.teb.teborchestrator.model.dto.LocationDto;
import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.dto.review.Review;
import com.teb.teborchestrator.model.dto.review.ReviewsDto;
import com.teb.teborchestrator.model.entity.Message;
import com.teb.teborchestrator.model.request.CancelOrderRequest;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.response.ChatResponse;
import com.teb.teborchestrator.service.TebService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TebController {

    private final TebService tebService;

    @GetMapping("/offers")
    public List<HotelDto> findAllOffers(
            @RequestParam String locationId,
            @RequestParam LocalDate dateFrom,
            @RequestParam LocalDate dateTo
    ) {
        return tebService.findAll(locationId, dateFrom, dateTo);
    }

    @GetMapping("/offers/{id}")
    public HotelDto findById(
            @PathVariable String id,
            @RequestParam LocalDate dateFrom,
            @RequestParam LocalDate dateTo
    ) {
        return tebService.findById(id, dateFrom, dateTo);
    }

    @GetMapping("/cartOffersDetails/{ids}")
    public List<HotelDto> findCartOffersDetails(@PathVariable List<String> ids) {
        return tebService.findByIdIn(ids);
    }

    @PostMapping("/createOrder")
    public OrderDto createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return tebService.createOrder(createOrderRequest);
    }

    @PutMapping("/cancelOrder")
    public OrderDto cancelOrder(@RequestBody CancelOrderRequest cancelOrderRequest) {
        return tebService.cancelOrder(cancelOrderRequest);
    }

    @GetMapping("/userOrders")
    public List<OrderDto> findUserOrders() {
        return tebService.findUserOrders();
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody List<Message> prompt) {
        return tebService.chat(prompt);
    }

    @PutMapping("/offerReviews/add/{hotelId}")
    public ReviewsDto addReview(@RequestBody Review review, @PathVariable String hotelId) {
        return tebService.addReview(review, hotelId);
    }

    @GetMapping("/locations")
    public List<LocationDto> findAllLocations() {
        return tebService.findAllLocations();
    }

}
