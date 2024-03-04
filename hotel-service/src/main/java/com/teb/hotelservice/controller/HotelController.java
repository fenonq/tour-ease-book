package com.teb.hotelservice.controller;

import com.teb.hotelservice.model.dto.HotelDto;
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
    public List<HotelDto> findAll() {
        return hotelService.findAll();
    }

    @GetMapping("/{id}")
    public HotelDto findById(@PathVariable Long id) {
        return hotelService.findById(id);
    }

    @PostMapping
    public HotelDto save(@RequestBody HotelDto hotelDto) {
        return hotelService.save(hotelDto);
    }

    @PutMapping("/{id}")
    public HotelDto update(@RequestBody HotelDto hotelDto, @PathVariable Long id) {
        return hotelService.update(hotelDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        hotelService.delete(id);
    }

}
