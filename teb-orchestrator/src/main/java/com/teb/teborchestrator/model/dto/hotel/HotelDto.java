package com.teb.teborchestrator.model.dto.hotel;

import com.teb.teborchestrator.model.dto.Offer;
import com.teb.teborchestrator.model.enums.VendorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto implements Offer {
    private String id;
    private VendorType vendorType = VendorType.HOTEL;
    private String name;
    private Location location;
    private String address;
    private String description;
    private List<String> amenities;
    private List<Room> rooms;
    private List<Review> reviews;
}
