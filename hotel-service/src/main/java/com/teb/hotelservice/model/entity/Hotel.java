package com.teb.hotelservice.model.entity;

import com.teb.hotelservice.model.enums.VendorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    private String id;
    private VendorType vendorType = VendorType.HOTEL;
    private String name;
    private Location location;
    private String address;
    private String description;
    private List<String> amenities = new ArrayList<>();
    private List<Media> mediaList = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
}
