package com.teb.hotelservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetHotelsRequest {
    private int locationId;
    private LocalDate from;
    private LocalDate to;
}
