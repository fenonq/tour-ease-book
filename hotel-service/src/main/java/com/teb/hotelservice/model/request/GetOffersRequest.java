package com.teb.hotelservice.model.request;

import com.teb.hotelservice.model.enums.VendorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOffersRequest {
    private VendorType vendorType;
    private int locationId;
    private LocalDate from;
    private LocalDate to;
}
