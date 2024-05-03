package com.teb.orderservice.model.entity;

import com.teb.orderservice.model.enums.VendorType;
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
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
