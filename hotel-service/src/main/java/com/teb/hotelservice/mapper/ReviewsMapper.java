package com.teb.hotelservice.mapper;

import com.teb.hotelservice.model.dto.ReviewsDto;
import com.teb.hotelservice.model.entity.Reviews;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewsMapper {

    ReviewsMapper INSTANCE = Mappers.getMapper(ReviewsMapper.class);

    Reviews mapReviewsDtoToReviews(ReviewsDto reviewsDto);

    ReviewsDto mapReviewsToReviewsDto(Reviews reviews);

}
