package com.teb.hotelservice.service;

import com.teb.hotelservice.model.dto.ReviewsDto;
import com.teb.hotelservice.model.entity.Review;

public interface ReviewsService {

    ReviewsDto addReview(Review review, String hotelId);

}
