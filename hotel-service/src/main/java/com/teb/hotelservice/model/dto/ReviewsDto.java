package com.teb.hotelservice.model.dto;

import com.teb.hotelservice.model.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsDto {
    private String id;
    private String hotelId;
    private List<Review> reviewList;
}
