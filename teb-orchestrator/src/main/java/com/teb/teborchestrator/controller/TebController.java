package com.teb.teborchestrator.controller;

import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import com.teb.teborchestrator.model.request.GetOffersRequest;
import com.teb.teborchestrator.service.TebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TebController {

    private final TebService tebService;

    @PostMapping("/getOffers") // todo probably can cause error, change to POST
    public List<?> findAllOffers(@RequestBody GetOffersRequest getOffersRequest) {
        return tebService.findAll(getOffersRequest);
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