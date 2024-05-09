package com.teb.hotelservice.controller;

import com.teb.hotelservice.model.dto.LocationDto;
import com.teb.hotelservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/location/all")
    public List<LocationDto> findAll() {
        return locationService.findAll();
    }

    @PostMapping("/location")
    public LocationDto save(@RequestBody LocationDto locationDto) {
        return locationService.save(locationDto);
    }

}
