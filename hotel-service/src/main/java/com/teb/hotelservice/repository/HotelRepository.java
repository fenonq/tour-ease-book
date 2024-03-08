package com.teb.hotelservice.repository;

import com.teb.hotelservice.entity.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {

    List<Hotel> findByLocation_LocationId(int locationId);

    @Query("{'rooms.bookings.userId': ?0}")
    List<Hotel> findByUserId(int userId);
}
