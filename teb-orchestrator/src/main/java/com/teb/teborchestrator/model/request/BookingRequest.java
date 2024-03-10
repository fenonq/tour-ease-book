package com.teb.teborchestrator.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private int roomId;
    private String userId;
    private LocalDate from;
    private LocalDate to;
}
