package com.teb.hotelservice.model.dto;

import com.teb.hotelservice.model.entity.Location;
import com.teb.hotelservice.model.entity.Media;
import com.teb.hotelservice.model.entity.Review;
import com.teb.hotelservice.model.entity.Room;
import com.teb.hotelservice.model.enums.VendorType;
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
