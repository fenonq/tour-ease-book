package com.teb.hotelservice.repository;

import com.teb.hotelservice.model.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {

    Optional<Booking> findByHotelIdAndRoomId(String hotelId, String roomId);

}
