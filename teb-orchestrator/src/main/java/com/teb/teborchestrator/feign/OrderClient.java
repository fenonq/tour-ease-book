package com.teb.teborchestrator.feign;

import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.request.CreateOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("ORDER-SERVICE")
public interface OrderClient {

    @PostMapping("/createOrder")
    OrderDto createOrder(@RequestBody CreateOrderRequest createOrderRequest);

    @GetMapping("/userOrders/{userId}")
    List<OrderDto> findUserOrders(@PathVariable String userId);

}
