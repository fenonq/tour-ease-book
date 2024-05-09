package com.teb.hotelservice.controller;

import com.teb.hotelservice.model.dto.HotelDto;
import com.teb.hotelservice.model.request.BookingRequest;
import com.teb.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/all")
    public List<HotelDto> findAll(
            @RequestParam String locationId,
            @RequestParam LocalDate dateFrom,
            @RequestParam LocalDate dateTo
    ) {
        return hotelService.findHotelsByRoomAvailability(locationId, dateFrom, dateTo);
    }

    @GetMapping("/{id}")
    public HotelDto findById(@PathVariable String id,
                             @RequestParam LocalDate dateFrom,
                             @RequestParam LocalDate dateTo
    ) {
        return hotelService.findById(id, dateFrom, dateTo);
    }

    @GetMapping("/ids/{ids}")
    public List<HotelDto> findByIdIn(@PathVariable List<String> ids) {
        return hotelService.findByIdIn(ids);
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
