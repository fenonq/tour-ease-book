package com.teb.hotelservice.controller;

import com.teb.hotelservice.dto.HotelDto;
import com.teb.hotelservice.request.BookingRequest;
import com.teb.hotelservice.request.GetHotelsRequest;
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

    @GetMapping()
    public List<HotelDto> findAll(@RequestBody GetHotelsRequest getHotelsRequest) {
        return hotelService.findHotelsByRoomAvailability(getHotelsRequest);
    }

    @GetMapping("/{id}")
    public HotelDto findById(@PathVariable String id) {
        System.out.println(hotelService.findBookingsByUserId(1));
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

    @PatchMapping("book/{id}")
    public HotelDto book(@RequestBody BookingRequest bookingRequest, @PathVariable String id) {
        return hotelService.book(bookingRequest, id);
    }

}
