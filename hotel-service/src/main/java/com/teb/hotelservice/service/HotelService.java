package com.teb.hotelservice.service;

import com.teb.hotelservice.dto.HotelDto;
import com.teb.hotelservice.request.BookingRequest;
import com.teb.hotelservice.request.GetHotelsRequest;

import java.util.List;

public interface HotelService {
    List<HotelDto> findAll();

    HotelDto findById(String id);

    HotelDto save(HotelDto hotelDto);

    HotelDto update(HotelDto hotelDto, String id);

    void delete(String id);

    HotelDto book(BookingRequest bookingRequest, String id);

    List<HotelDto> findHotelsByRoomAvailability(GetHotelsRequest getHotelsRequest);

    List<HotelDto> findBookingsByUserId(int userId);
}
