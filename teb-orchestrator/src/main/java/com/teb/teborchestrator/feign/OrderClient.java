package com.teb.teborchestrator.feign;

import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("ORDER-SERVICE")
public interface OrderClient {

    @PostMapping("/createOrder")
    OrderDto createOrder(@RequestBody CreateOrderRequest createOrderRequest);

    @PutMapping("/cancelOrder/{id}")
    OrderDto cancelOrder(@PathVariable String id);

    @GetMapping("/userOrders/{userId}")
    List<OrderDto> findUserOrders(@PathVariable String userId);

}
