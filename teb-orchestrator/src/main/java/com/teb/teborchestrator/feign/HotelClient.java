package com.teb.teborchestrator.feign;

import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.request.BookingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient("HOTEL-SERVICE")
public interface HotelClient {

    @GetMapping("/all")
    List<HotelDto> findAll(
            @RequestParam int locationId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo
    );

    @GetMapping("/{id}")
    HotelDto findById(@PathVariable String id);

    @GetMapping("/ids/{ids}")
    List<HotelDto> findByIdIn(@PathVariable List<String> ids);

    @PostMapping
    HotelDto save(@RequestBody HotelDto hotelDto);

    @PutMapping("/{id}")
    HotelDto update(@RequestBody HotelDto hotelDto, @PathVariable String id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable String id);

    @PutMapping("/book/{id}")
    HotelDto book(@RequestBody BookingRequest bookingRequest, @PathVariable String id);

}
