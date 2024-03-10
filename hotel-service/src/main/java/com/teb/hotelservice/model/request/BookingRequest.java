package com.teb.hotelservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private int roomId;
    private int userId;
    private LocalDate from;
    private LocalDate to;
}
