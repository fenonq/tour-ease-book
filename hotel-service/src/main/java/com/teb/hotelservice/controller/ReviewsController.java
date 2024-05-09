package com.teb.hotelservice.controller;

import com.teb.hotelservice.model.dto.ReviewsDto;
import com.teb.hotelservice.model.entity.Review;
import com.teb.hotelservice.service.ReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewsController {

    private final ReviewsService reviewService;

    @PutMapping("/hotelReviews/add/{hotelId}")
    public ReviewsDto addReview(@RequestBody Review review, @PathVariable String hotelId) {
        return reviewService.addReview(review, hotelId);
    }

}
