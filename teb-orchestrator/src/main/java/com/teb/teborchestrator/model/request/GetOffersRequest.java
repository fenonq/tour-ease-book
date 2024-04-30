package com.teb.teborchestrator.model.request;

import com.teb.teborchestrator.model.enums.VendorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOffersRequest {
    private VendorType vendorType;
    private int location;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
