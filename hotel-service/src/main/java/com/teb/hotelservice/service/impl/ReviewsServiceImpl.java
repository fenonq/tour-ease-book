package com.teb.hotelservice.service.impl;

import com.teb.hotelservice.mapper.ReviewsMapper;
import com.teb.hotelservice.model.dto.ReviewsDto;
import com.teb.hotelservice.model.entity.Review;
import com.teb.hotelservice.model.entity.Reviews;
import com.teb.hotelservice.repository.ReviewsRepository;
import com.teb.hotelservice.service.ReviewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewsServiceImpl implements ReviewsService {

    private final ReviewsRepository reviewsRepository;

    @Override
    public ReviewsDto addReview(Review review, String hotelId) {
        log.info("Adding review to the hotel with id {}..", hotelId);
        Reviews reviews = reviewsRepository.findByHotelId(hotelId).orElse(Reviews.builder()
                .hotelId(hotelId)
                .reviewList(new ArrayList<>())
                .build());
        reviews.getReviewList().add(review);
        return ReviewsMapper.INSTANCE.mapReviewsToReviewsDto(reviewsRepository.save(reviews));
    }

}
