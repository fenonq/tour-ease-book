package com.teb.teborchestrator.controller;

import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.request.GetOffersRequest;
import com.teb.teborchestrator.service.TebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TebController {

    private final TebService tebService;

    @PostMapping("/offers") // todo probably can cause error, change to POST
    public List<HotelDto> findAllOffers(@RequestBody GetOffersRequest getOffersRequest) {
        return tebService.findAll(getOffersRequest);
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

}
