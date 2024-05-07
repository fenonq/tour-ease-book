package com.teb.hotelservice.service;

import com.teb.hotelservice.model.dto.HotelDto;
import com.teb.hotelservice.model.dto.ReviewsDto;
import com.teb.hotelservice.model.entity.Review;
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

    List<HotelDto> findHotelsByRoomAvailability(int locationId, LocalDate dateFrom, LocalDate dateTo);

    ReviewsDto addReview(Review review, String hotelId);

}
