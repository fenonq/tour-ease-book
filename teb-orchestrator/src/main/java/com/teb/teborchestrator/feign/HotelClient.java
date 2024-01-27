package com.teb.teborchestrator.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("HOTEL-SERVICE")
public interface HotelClient {

    @GetMapping("/test")
    String test();

}
