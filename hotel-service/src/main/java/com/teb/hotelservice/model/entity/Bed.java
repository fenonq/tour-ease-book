package com.teb.hotelservice.model.entity;

import com.teb.hotelservice.model.enums.BedType;
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
