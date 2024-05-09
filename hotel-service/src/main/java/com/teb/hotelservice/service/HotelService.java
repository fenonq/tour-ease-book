package com.teb.hotelservice.service;

import com.teb.hotelservice.model.dto.HotelDto;
import com.teb.hotelservice.model.request.BookingRequest;

import java.time.LocalDate;
import java.util.List;

public interface HotelService {

    HotelDto findById(String id, LocalDate dateFrom, LocalDate dateTo);

    List<HotelDto> findByIdIn(List<String> ids);

    HotelDto save(HotelDto hotelDto);

    HotelDto update(HotelDto hotelDto, String id);

    void delete(String id);

    HotelDto book(BookingRequest bookingRequest, String id);

    List<HotelDto> findHotelsByRoomAvailability(String locationId, LocalDate dateFrom, LocalDate dateTo);

}
