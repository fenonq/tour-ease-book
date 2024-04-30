package com.teb.hotelservice.service;

import com.teb.hotelservice.model.dto.HotelDto;
import com.teb.hotelservice.model.entity.Hotel;
import com.teb.hotelservice.model.request.BookingRequest;
import com.teb.hotelservice.model.request.GetOffersRequest;

import java.util.List;

public interface HotelService {
    List<HotelDto> findAll();

    HotelDto findById(String id);

    List<HotelDto> findByIdIn(List<String> ids);

    HotelDto save(HotelDto hotelDto);

    HotelDto update(HotelDto hotelDto, String id);

    void delete(String id);

    HotelDto book(BookingRequest bookingRequest, String id);

    List<HotelDto> findHotelsByRoomAvailability(GetOffersRequest getHotelsRequest);

    List<HotelDto> findBookingsByUserId(int userId);
}
