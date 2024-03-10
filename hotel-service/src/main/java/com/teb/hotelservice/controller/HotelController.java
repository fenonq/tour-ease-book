package com.teb.hotelservice.controller;

import com.teb.hotelservice.model.dto.HotelDto;
import com.teb.hotelservice.model.request.BookingRequest;
import com.teb.hotelservice.model.request.GetOffersRequest;
import com.teb.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("/all")
    public List<HotelDto> findAll(@RequestBody GetOffersRequest getOffersRequest) {
        return hotelService.findHotelsByRoomAvailability(getOffersRequest);
    }

    @GetMapping("/{id}")
    public HotelDto findById(@PathVariable String id) {
        return hotelService.findById(id);
    }

    @PostMapping
    public HotelDto save(@RequestBody HotelDto hotelDto) {
        return hotelService.save(hotelDto);
    }

    @PutMapping("/{id}")
    public HotelDto update(@RequestBody HotelDto hotelDto, @PathVariable String id) {
        return hotelService.update(hotelDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        hotelService.delete(id);
    }

    @PutMapping("/book/{id}")
    public HotelDto book(@RequestBody BookingRequest bookingRequest, @PathVariable String id) {
        return hotelService.book(bookingRequest, id);
    }

}
