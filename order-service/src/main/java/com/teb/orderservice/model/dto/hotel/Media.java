package com.teb.orderservice.model.dto.hotel;

import com.teb.orderservice.model.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    private MediaType type;
    private String source;
}