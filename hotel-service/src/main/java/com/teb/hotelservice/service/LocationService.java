package com.teb.hotelservice.service;

import com.teb.hotelservice.model.dto.LocationDto;

import java.util.List;

public interface LocationService {

    List<LocationDto> findAll();

    LocationDto save(LocationDto locationDto);

}
