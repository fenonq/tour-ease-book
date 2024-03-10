package com.teb.teborchestrator.feign;

import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.request.BookingRequest;
import com.teb.teborchestrator.model.request.GetOffersRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("HOTEL-SERVICE")
public interface HotelClient {

    @PostMapping("/all")
    List<HotelDto> findAll(@RequestBody GetOffersRequest getOffersRequest);

    @GetMapping("/{id}")
    HotelDto findById(@PathVariable String id);

    @PostMapping
    HotelDto save(@RequestBody HotelDto hotelDto);

    @PutMapping("/{id}")
    HotelDto update(@RequestBody HotelDto hotelDto, @PathVariable String id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable String id);

    @PutMapping("/book/{id}")
    HotelDto book(@RequestBody BookingRequest bookingRequest, @PathVariable String id);

}
