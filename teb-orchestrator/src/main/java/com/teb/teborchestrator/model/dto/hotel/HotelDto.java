package com.teb.teborchestrator.model.dto.hotel;

import com.teb.teborchestrator.model.dto.LocationDto;
import com.teb.teborchestrator.model.dto.review.Review;
import com.teb.teborchestrator.model.enums.VendorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    private String id;
    private VendorType vendorType = VendorType.HOTEL;
    private String name;
    private String locationId;
    private LocationDto location;
    private String address;
    private String description;
    private int stars;
    private List<String> amenities;
    private List<Media> mediaList;
    private List<Room> rooms;
    private List<Review> reviews;
}
