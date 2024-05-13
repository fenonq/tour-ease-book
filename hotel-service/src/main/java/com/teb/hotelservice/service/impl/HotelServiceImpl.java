package com.teb.hotelservice.service.impl;

import com.teb.hotelservice.mapper.HotelMapper;
import com.teb.hotelservice.mapper.LocationMapper;
import com.teb.hotelservice.model.dto.HotelDto;
import com.teb.hotelservice.model.dto.LocationDto;
import com.teb.hotelservice.model.entity.*;
import com.teb.hotelservice.model.request.BookingRequest;
import com.teb.hotelservice.model.request.CancelOrderRequest;
import com.teb.hotelservice.repository.BookingRepository;
import com.teb.hotelservice.repository.HotelRepository;
import com.teb.hotelservice.repository.LocationRepository;
import com.teb.hotelservice.repository.ReviewsRepository;
import com.teb.hotelservice.service.HotelService;
import com.teb.hotelservice.util.Utils;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ReviewsRepository reviewsRepository;
    private final BookingRepository bookingRepository;
    private final LocationRepository locationRepository;

    @Override
    public HotelDto findById(String id, LocalDate dateFrom, LocalDate dateTo) {
        log.info("Finding hotel with id {}..", id);

        List<LocalDate> bookedDatesFromRequest = Utils.generateDatesBetween(dateFrom, dateTo);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));

        List<Room> availableRooms = hotel.getRooms().stream()
                .peek(room -> {
                    long totalBooked = bookedDatesFromRequest.stream()
                            .mapToLong(date -> bookingRepository.findByHotelIdAndRoomId(hotel.getId(), room.getRoomId()).stream()
                                    .flatMap(booking -> booking.getBookedDays().stream())
                                    .filter(bookedDay -> bookedDay.getDate().isEqual(date))
                                    .mapToLong(bookedDay -> bookedDay.getUserIds().size())
                                    .sum())
                            .max()
                            .orElse(0);
                    int availableCount = room.getNumberOfRooms() - (int) totalBooked;
                    room.setNumberOfAvailableRooms(Math.max(0, availableCount));
                })
                .filter(room -> room.getNumberOfAvailableRooms() > 0)
                .toList();
        hotel.setRooms(availableRooms);

        List<Review> reviews = reviewsRepository.findByHotelId(id)
                .map(Reviews::getReviewList)
                .orElseGet(Collections::emptyList);

        HotelDto hotelDto = HotelMapper.INSTANCE.mapHotelToHotelDto(hotel);
        hotelDto.setReviews(reviews);

        LocationDto locationDto = LocationMapper.INSTANCE.mapLocationToLocationDto(locationRepository.findById(hotelDto.getLocationId()).orElse(null));
        hotelDto.setLocation(locationDto);

        return hotelDto;
    }

    @Override
    public List<HotelDto> findByIdIn(List<String> ids) {
        log.info("Finding hotels with ids {}..", ids);
        List<HotelDto> hotelsToReturn = hotelRepository.findByIdIn(ids).stream()
                .map(HotelMapper.INSTANCE::mapHotelToHotelDto)
                .toList();
        hotelsToReturn.forEach(hotel -> hotel.setLocation(LocationMapper.INSTANCE.mapLocationToLocationDto(locationRepository.findById(hotel.getLocationId()).orElse(null))));
        return hotelsToReturn;
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
        hotelRepository.findById(id).orElseThrow(() -> new NotFoundException("Hotel not found with id: " + id));
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

            List<Room> filteredRooms = hotel.getRooms().stream()
                    .filter(room -> room.getRoomId().equals(bookingRequest.getRoomId()))
                    .toList();
            hotel.setRooms(filteredRooms);

            HotelDto hotelToReturn = HotelMapper.INSTANCE.mapHotelToHotelDto(hotel);
            LocationDto locationDto = LocationMapper.INSTANCE.mapLocationToLocationDto(locationRepository.findById(hotelToReturn.getLocationId()).orElse(null));
            hotelToReturn.setLocation(locationDto);
            return hotelToReturn;
        } else {
            throw new NotFoundException("Room is not available for booking.");
        }
    }

    @Override
    public void cancelBooking(CancelOrderRequest cancelOrderRequest) {
        log.info("Cancelling bookings for order {}..", cancelOrderRequest.getOrderId());
        for (CancelledItem cancelledItem : cancelOrderRequest.getCancelledItems()) {
            List<LocalDate> bookedDatesFromRequest = Utils.generateDatesBetween(cancelledItem.getDateFrom(), cancelledItem.getDateTo());
            Booking bookingToCancel = bookingRepository.findByHotelIdAndRoomId(cancelledItem.getHotelId(), cancelledItem.getRoomId()).orElseThrow(NotFoundException::new);
            bookedDatesFromRequest.forEach(bookedDateFromRq -> bookingToCancel.getBookedDays().stream()
                    .filter(bookedDay -> bookedDateFromRq.equals(bookedDay.getDate()))
                    .forEach(bookedDay -> removeUserIdsFromBookedDate(bookedDay, cancelOrderRequest.getUserId(), cancelledItem.getNumberOfRooms())));
            bookingRepository.save(bookingToCancel);
        }
    }

    private void removeUserIdsFromBookedDate(BookedDay bookedDay, String userId, int numberOfRooms) {
        for (int i = 0; i < numberOfRooms; i++) {
            bookedDay.getUserIds().remove(userId);
        }
    }

    @Override
    public List<HotelDto> findHotelsByRoomAvailability(String locationId, LocalDate dateFrom, LocalDate dateTo) {
        log.info("Finding hotels by room availability with locationId {}, dateFrom {}, dateTo {}", locationId, dateFrom, dateTo);
        List<Hotel> allHotels = hotelRepository.findByLocationId(locationId);
        List<LocalDate> bookedDatesFromRequest = Utils.generateDatesBetween(dateFrom, dateTo);

        List<HotelDto> hotelsToReturn = allHotels.stream()
                .peek(hotel -> {
                    List<Room> availableRooms = hotel.getRooms().stream()
                            .peek(room -> {
                                long totalBooked = bookedDatesFromRequest.stream()
                                        .mapToLong(date -> bookingRepository.findByHotelIdAndRoomId(hotel.getId(), room.getRoomId()).stream()
                                                .flatMap(booking -> booking.getBookedDays().stream())
                                                .filter(bookedDay -> bookedDay.getDate().isEqual(date))
                                                .mapToLong(bookedDay -> bookedDay.getUserIds().size())
                                                .sum())
                                        .max()
                                        .orElse(0);
                                int availableCount = room.getNumberOfRooms() - (int) totalBooked;
                                room.setNumberOfAvailableRooms(Math.max(0, availableCount));
                            })
                            .filter(room -> room.getNumberOfAvailableRooms() > 0)
                            .collect(Collectors.toList());
                    hotel.setRooms(availableRooms);
                })
                .filter(hotel -> !hotel.getRooms().isEmpty())
                .map(HotelMapper.INSTANCE::mapHotelToHotelDto)
                .toList();

        List<String> hotelsToReturnIds = hotelsToReturn.stream().map(HotelDto::getId).toList();
        List<Reviews> reviews = reviewsRepository.findByHotelIdIn(hotelsToReturnIds);

        setReviewsToHotels(hotelsToReturn, reviews);
        hotelsToReturn.forEach(hotel -> hotel.setLocation(LocationMapper.INSTANCE.mapLocationToLocationDto(locationRepository.findById(hotel.getLocationId()).orElse(null))));

        return hotelsToReturn;
    }

    private void setReviewsToHotels(List<HotelDto> hotelsToReturn, List<Reviews> allReviews) {
        Map<String, List<Review>> reviewsMap = allReviews.stream()
                .collect(Collectors.toMap(
                        Reviews::getHotelId,
                        Reviews::getReviewList,
                        (existing, replacement) -> existing
                ));

        hotelsToReturn.forEach(hotel -> {
            List<Review> reviews = reviewsMap.getOrDefault(hotel.getId(), new ArrayList<>());
            hotel.setReviews(reviews);
        });
    }

}
