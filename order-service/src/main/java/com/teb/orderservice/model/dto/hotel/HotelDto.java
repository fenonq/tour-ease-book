package com.teb.orderservice.model.dto.hotel;

import com.teb.orderservice.model.enums.VendorType;
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
    private Location location;
    private String address;
    private String description;
    private int stars;
    private List<String> amenities;
    private List<Media> mediaList;
    private List<Room> rooms;
    private List<Review> reviews;
}
