package com.teb.hotelservice.repository;

import com.teb.hotelservice.model.entity.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {

    List<Hotel> findByLocation_LocationId(int locationId);

    List<Hotel> findByIdIn(List<String> ids);

    @Query("{'rooms.bookings.userId': ?0}")
    List<Hotel> findByUserId(int userId); // todo change id type
}
