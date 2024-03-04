package com.teb.teborchestrator.controller;

import com.teb.teborchestrator.feign.HotelClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final HotelClient hotelClient;

    @GetMapping("/test")
    public String test() {
        return "teb " + hotelClient.test();
    }

    @GetMapping("/test1")
    public String test1() {
        return "teb ";
    }

    @GetMapping("/getOffers")
    public String getOffers(@RequestBody String rq) {

        return "teb ";
    }

}
