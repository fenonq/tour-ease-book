package com.teb.orderservice.model.dto.hotel;

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
    private double price;
    private List<Bed> beds;
}
