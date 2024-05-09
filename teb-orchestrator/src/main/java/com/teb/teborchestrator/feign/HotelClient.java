package com.teb.teborchestrator.feign;

import com.teb.teborchestrator.model.dto.LocationDto;
import com.teb.teborchestrator.model.dto.hotel.HotelDto;
import com.teb.teborchestrator.model.dto.review.Review;
import com.teb.teborchestrator.model.dto.review.ReviewsDto;
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
            @RequestParam String locationId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo
    );

    @GetMapping("/{id}")
    HotelDto findById(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo
    );

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

    @GetMapping("/hotelReviews/{hotelId}")
    ReviewsDto findByHotelId(@PathVariable String hotelId);

    @PutMapping("/hotelReviews/add/{hotelId}")
    ReviewsDto addReview(@RequestBody Review review, @PathVariable String hotelId);

    @GetMapping("/location/all")
    List<LocationDto> findAll();

    @PostMapping("/location")
    LocationDto save(@RequestBody LocationDto locationDto);

}
