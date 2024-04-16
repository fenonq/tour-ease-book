package com.teb.hotelservice.service.impl;

import com.teb.hotelservice.mapper.HotelMapper;
import com.teb.hotelservice.model.dto.HotelDto;
import com.teb.hotelservice.model.entity.BookedDay;
import com.teb.hotelservice.model.entity.Hotel;
import com.teb.hotelservice.model.entity.Room;
import com.teb.hotelservice.model.request.BookingRequest;
import com.teb.hotelservice.model.request.GetOffersRequest;
import com.teb.hotelservice.repository.HotelRepository;
import com.teb.hotelservice.service.HotelService;
import com.teb.hotelservice.util.Utils;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
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

        Room roomToBook = hotel.getRooms().stream()
                .filter(room -> room.getRoomId() == bookingRequest.getRoomId())
                .findFirst()
                .orElseThrow(NotFoundException::new);

        List<LocalDate> bookedDates = Utils.generateDatesBetween(bookingRequest.getFrom(), bookingRequest.getTo());

        boolean isRoomAvailable = bookedDates.stream()
                .noneMatch(date -> roomToBook.getBookedDays().stream()
                        .filter(bookedDay -> bookedDay.getDate().isEqual(date))
                        .mapToLong(bookedDay -> bookedDay.getUserIds().size())
                        .sum() >= roomToBook.getNumberOfRooms());

        if (isRoomAvailable) {
            List<LocalDate> existingDates = roomToBook.getBookedDays().stream()
                    .map(BookedDay::getDate)
                    .toList();

            for (LocalDate date : bookedDates) {
                if (existingDates.contains(date)) {
                    BookedDay existingBookedDay = roomToBook.getBookedDays().stream()
                            .filter(bookedDay -> bookedDay.getDate().equals(date))
                            .findFirst()
                            .orElseThrow(NotFoundException::new);
                    existingBookedDay.getUserIds().add(bookingRequest.getUserId());
                } else {
                    roomToBook.getBookedDays().add(new BookedDay(date, Collections.singletonList(bookingRequest.getUserId())));
                }
            }
            Hotel bookedHotel = hotelRepository.save(hotel);
            return HotelMapper.INSTANCE.mapHotelToHotelDto(bookedHotel);
        } else {
//            throw new RoomNotAvailableException("Room is not available for booking.");
            throw new NotFoundException("Room is not available for booking.");
        }
    }

    @Override
    public List<HotelDto> findHotelsByRoomAvailability(GetOffersRequest getOffersRequest) {
        List<Hotel> allHotels = hotelRepository.findByLocation_LocationId(getOffersRequest.getLocationId());
        List<LocalDate> bookedDates = Utils.generateDatesBetween(getOffersRequest.getFrom(), getOffersRequest.getTo());

        return allHotels.stream()
                .filter(hotel -> hotel.getRooms().stream()
                        .anyMatch(room -> room.getNumberOfRooms() >= bookedDates.stream()
                                .flatMap(date -> room.getBookedDays().stream()
                                        .filter(bookedDay -> bookedDay.getDate().isEqual(date))
                                        .flatMap(bookedDay -> bookedDay.getUserIds().stream()))
                                .count()))
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
