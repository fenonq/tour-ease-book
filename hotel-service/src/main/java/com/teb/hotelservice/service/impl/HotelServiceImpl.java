package com.teb.hotelservice.service.impl;

import com.teb.hotelservice.dto.HotelDto;
import com.teb.hotelservice.entity.Booking;
import com.teb.hotelservice.entity.Hotel;
import com.teb.hotelservice.mapper.HotelMapper;
import com.teb.hotelservice.repository.HotelRepository;
import com.teb.hotelservice.request.BookingRequest;
import com.teb.hotelservice.request.GetHotelsRequest;
import com.teb.hotelservice.service.HotelService;
import com.teb.hotelservice.util.Utils;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public List<HotelDto> findAll() {
        log.info("Finding all hotels..");
        return hotelRepository.findAll().stream()
                .map(HotelMapper.INSTANCE::mapHotelToHotelDto)
                .toList();
    }

    @Override
    public HotelDto findById(String id) {
        log.info("Finding hotel with id {}..", id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(NotFoundException::new);
        return HotelMapper.INSTANCE.mapHotelToHotelDto(hotel);
    }

    @Override
    public HotelDto save(HotelDto hotelDto) {
        log.info("Saving hotel with name {}", hotelDto.getName());
        Hotel hotelFromRq = HotelMapper.INSTANCE.mapHotelDtoToHotel(hotelDto);
        Hotel hotelToReturn = hotelRepository.save(hotelFromRq);
        return HotelMapper.INSTANCE.mapHotelToHotelDto(hotelToReturn);
    }

    @Override
    public HotelDto update(HotelDto hotelDto, String id) {
        log.info("Updating hotel with id {}", id);
        findById(id);
        Hotel hotelFromRq = HotelMapper.INSTANCE.mapHotelDtoToHotel(hotelDto);
        hotelFromRq.setId(id);
        Hotel hotelToReturn = hotelRepository.save(hotelFromRq);
        return HotelMapper.INSTANCE.mapHotelToHotelDto(hotelToReturn);
    }

    @Override
    public void delete(String id) {
        log.info("Deleting hotel with id {}..", id);
        hotelRepository.deleteById(id);
    }

    @Override
    public HotelDto book(BookingRequest bookingRequest, String id) {
        log.info("Booking room {} of hotel {}..", bookingRequest.getRoomId(), id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(NotFoundException::new);
        List<LocalDate> bookedDates = Utils.generateDatesBetween(bookingRequest.getFrom(), bookingRequest.getTo());
        hotel.getRooms().stream()
                .filter(room -> room.getRoomId() == bookingRequest.getRoomId())
                .findFirst()
                .orElseThrow(NotFoundException::new)
                .getBookings()
                .add(new Booking(bookingRequest.getUserId(), bookedDates));
        Hotel hotelToReturn = hotelRepository.save(hotel);
        return HotelMapper.INSTANCE.mapHotelToHotelDto(hotelToReturn);
    }

    @Override
    public List<HotelDto> findHotelsByRoomAvailability(GetHotelsRequest getHotelsRequest) {
        List<Hotel> allHotels = hotelRepository.findByLocation_LocationId(getHotelsRequest.getLocationId());
        List<LocalDate> bookedDates = Utils.generateDatesBetween(getHotelsRequest.getFrom(), getHotelsRequest.getTo());

        return allHotels.stream()
                .filter(hotel -> hotel.getRooms().stream()
                        .noneMatch(room -> room.getBookings().stream()
                                .flatMap(booking -> booking.getBookedDays().stream())
                                .anyMatch(bookedDates::contains)))
                .map(HotelMapper.INSTANCE::mapHotelToHotelDto)
                .toList();
    }

    @Override
    public List<HotelDto> findBookingsByUserId(int userId) {
        log.info("Finding bookings of user {}..", userId);
        return hotelRepository.findByUserId(userId).stream()
                .map(HotelMapper.INSTANCE::mapHotelToHotelDto)
                .toList();
    }
}
