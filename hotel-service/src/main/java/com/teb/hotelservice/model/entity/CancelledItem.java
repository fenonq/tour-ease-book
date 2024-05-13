package com.teb.hotelservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelledItem {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int numberOfRooms;
    private String hotelId;
    private String roomId;
}
