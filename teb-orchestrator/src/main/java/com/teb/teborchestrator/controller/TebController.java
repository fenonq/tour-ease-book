package com.teb.teborchestrator.controller;

import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.entity.Message;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.response.ChatResponse;
import com.teb.teborchestrator.service.TebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TebController {

    private final TebService tebService;

    @GetMapping("/offers")
    public List<HotelDto> findAllOffers(
            @RequestParam int locationId,
            @RequestParam LocalDate dateFrom,
            @RequestParam LocalDate dateTo
    ) {
        return tebService.findAll(locationId, dateFrom, dateTo);
    }

    @GetMapping("/offers/{id}")
    public HotelDto findById(@PathVariable String id) {
        return tebService.findById(id);
    }

    @GetMapping("/cartOffersDetails/{ids}")
    public List<HotelDto> findCartOffersDetails(@PathVariable List<String> ids) {
        return tebService.findByIdIn(ids);
    }

    @PostMapping("/createOrder")
    public OrderDto createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return tebService.createOrder(createOrderRequest);
    }

    @GetMapping("/userOrders")
    public List<OrderDto> findUserOrders() {
        return tebService.findUserOrders();
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody List<Message> prompt) {
        return tebService.chat(prompt);
    }

}
