package com.teb.orderservice.model.dto.hotel;

import com.teb.orderservice.model.enums.BedType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bed {
    private BedType type;
    private int number;
}
