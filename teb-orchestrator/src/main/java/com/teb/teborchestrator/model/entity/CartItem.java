package com.teb.teborchestrator.model.entity;

import com.teb.teborchestrator.model.enums.VendorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private VendorType vendorType;
    private String id;
    private int roomId;
}
