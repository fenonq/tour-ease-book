package com.teb.orderservice.controller;

import com.teb.orderservice.model.dto.OrderDto;
import com.teb.orderservice.model.request.CreateOrderRequest;
import com.teb.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/createOrder")
    public OrderDto createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(createOrderRequest);
    }

    @PutMapping("/cancelOrder/{id}")
    public OrderDto cancelOrder(@PathVariable String id) {
        return orderService.cancelOrder(id);
    }

    @GetMapping("/userOrders/{userId}")
    public List<OrderDto> findUserOrders(@PathVariable String userId) {
        return orderService.findUserOrders(userId);
    }

}
