package com.teb.hotelservice.dto;

import com.teb.hotelservice.entity.Location;
import com.teb.hotelservice.entity.Review;
import com.teb.hotelservice.entity.Room;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class HotelDto {
    private String id;
    private String name;
    private Location location;
    private String address;
    private String description;
    private List<String> amenities;
    private List<Room> rooms;
    private List<Review> reviews;
}
