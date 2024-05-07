package com.teb.hotelservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private String roomId;
    private String roomType;
    private int capacity;
    private int numberOfRooms;
    private int numberOfAvailableRooms;
    private double price;
    private List<Bed> beds;
}
