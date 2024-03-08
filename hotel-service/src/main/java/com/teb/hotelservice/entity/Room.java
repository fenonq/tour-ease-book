package com.teb.hotelservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private int roomId;
    private String roomType;
    private int capacity;
    private double price;
    private List<Booking> bookings;
}
