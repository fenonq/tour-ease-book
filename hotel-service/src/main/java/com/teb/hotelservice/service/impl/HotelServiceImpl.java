package com.teb.hotelservice.service.impl;

import com.teb.hotelservice.mapper.HotelMapper;
import com.teb.hotelservice.model.dto.HotelDto;
import com.teb.hotelservice.model.entity.BookedDay;
import com.teb.hotelservice.model.entity.Booking;
import com.teb.hotelservice.model.entity.Hotel;
import com.teb.hotelservice.model.entity.Room;
import com.teb.hotelservice.model.request.BookingRequest;
import com.teb.hotelservice.model.request.GetOffersRequest;
import com.teb.hotelservice.repository.BookingRepository;
import com.teb.hotelservice.repository.HotelRepository;
import com.teb.hotelservice.service.HotelService;
import com.teb.hotelservice.util.Utils;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;

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
        hotelFromRq.getRooms().forEach(room -> room.setRoomId(UUID.randomUUID().toString()));
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

        Room roomToBook = hotel.getRooms().stream()
                .filter(room -> room.getRoomId().equals(bookingRequest.getRoomId()))
                .findFirst()
                .orElseThrow(NotFoundException::new);
        List<LocalDate> bookedDatesFromRequest = Utils.generateDatesBetween(bookingRequest.getFrom(), bookingRequest.getTo());

        Booking booking = bookingRepository.findByHotelIdAndRoomId(hotel.getId(), roomToBook.getRoomId())
                .orElse(Booking.builder()
                        .hotelId(hotel.getId())
                        .roomId(roomToBook.getRoomId())
                        .bookedDays(new ArrayList<>())
                        .build());

        boolean isRoomAvailable = bookedDatesFromRequest.stream()
                .noneMatch(date -> booking.getBookedDays().stream()
                        .filter(bookedDay -> bookedDay.getDate().isEqual(date))
                        .mapToLong(bookedDay -> bookedDay.getUserIds().size())
                        .sum() >= roomToBook.getNumberOfRooms());

        if (isRoomAvailable) {
            List<LocalDate> existingDates = booking.getBookedDays().stream()
                    .map(BookedDay::getDate)
                    .toList();

            for (LocalDate date : bookedDatesFromRequest) {
                if (existingDates.contains(date)) {
                    BookedDay existingBookedDay = booking.getBookedDays().stream()
                            .filter(bookedDay -> bookedDay.getDate().equals(date))
                            .findFirst()
                            .orElseThrow(NotFoundException::new);
                    existingBookedDay.getUserIds().add(bookingRequest.getUserId());
                } else {
                    booking.getBookedDays().add(new BookedDay(date, Collections.singletonList(bookingRequest.getUserId())));
                }
            }
            bookingRepository.save(booking);
            return HotelMapper.INSTANCE.mapHotelToHotelDto(hotel);
        } else {
//            throw new RoomNotAvailableException("Room is not available for booking.");
            throw new NotFoundException("Room is not available for booking.");
        }
    }

    @Override
    public List<HotelDto> findHotelsByRoomAvailability(GetOffersRequest getOffersRequest) {
        List<Hotel> allHotels = hotelRepository.findByLocation_LocationId(getOffersRequest.getLocationId());
        List<LocalDate> bookedDatesFromRequest = Utils.generateDatesBetween(getOffersRequest.getFrom(), getOffersRequest.getTo());

        List<Booking> allBookings = bookingRepository.findAll();

        return allHotels.stream()
                .peek(hotel -> {
                    List<Room> availableRooms = hotel.getRooms().stream()
                            .filter(room -> room.getNumberOfRooms() >= bookedDatesFromRequest.stream()
                                    .flatMap(date -> allBookings.stream()
                                            .filter(booking -> booking.getHotelId().equals(hotel.getId()) && booking.getRoomId().equals(room.getRoomId()))
                                            .flatMap(booking -> booking.getBookedDays().stream()
                                                    .filter(bookedDay -> bookedDay.getDate().isEqual(date))
                                                    .flatMap(bookedDay -> bookedDay.getUserIds().stream())))
                                    .count())
                            .toList();

                    hotel.setRooms(availableRooms);
                })
                .filter(hotel -> !hotel.getRooms().isEmpty())
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
