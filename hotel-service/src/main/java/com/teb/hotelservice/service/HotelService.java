package com.teb.hotelservice.service;

import com.teb.hotelservice.model.dto.HotelDto;

import java.util.List;

public interface HotelService {
    List<HotelDto> findAll();

    HotelDto findById(Long id);

    HotelDto save(HotelDto hotelDto);

    HotelDto update(HotelDto hotelDto, Long id);

    void delete(Long id);
}
