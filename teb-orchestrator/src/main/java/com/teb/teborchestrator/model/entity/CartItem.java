package com.teb.teborchestrator.model.entity;

import com.teb.teborchestrator.model.enums.VendorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private VendorType vendorType;
    private String offerId;
    private String roomId;
    private int numberOfRooms;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
