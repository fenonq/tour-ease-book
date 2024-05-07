package com.teb.hotelservice.repository;

import com.teb.hotelservice.model.entity.Reviews;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewsRepository extends MongoRepository<Reviews, String> {

    Optional<Reviews> findByHotelId(String hotelId);

    List<Reviews> findByHotelIdIn(List<String> hotelIds);
}
