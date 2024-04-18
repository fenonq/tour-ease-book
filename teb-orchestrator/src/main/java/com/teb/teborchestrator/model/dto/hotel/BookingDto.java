package com.teb.teborchestrator.model.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private String id;
    private String hotelId;
    private String roomId;
    private List<BookedDay> bookedDays;
}
