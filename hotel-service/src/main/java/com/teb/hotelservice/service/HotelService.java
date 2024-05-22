package com.teb.hotelservice.service;

import com.teb.hotelservice.model.dto.HotelDto;
import com.teb.hotelservice.model.request.BookingRequest;
import com.teb.hotelservice.model.request.CancelOrderRequest;

import java.time.LocalDate;
import java.util.List;

public interface HotelService {

    List<HotelDto> findAll();

    HotelDto findById(String id, LocalDate dateFrom, LocalDate dateTo);

    List<HotelDto> findByIdIn(List<String> ids);

    HotelDto save(HotelDto hotelDto);

    HotelDto update(HotelDto hotelDto, String id);

    void delete(String id);

    HotelDto book(BookingRequest bookingRequest, String id);

    void cancelBooking(CancelOrderRequest cancelOrderRequest);

    List<HotelDto> findHotelsByRoomAvailability(String locationId, LocalDate dateFrom, LocalDate dateTo);

}
