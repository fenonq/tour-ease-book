package com.teb.hotelservice.model.entity;

import com.teb.hotelservice.model.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    private MediaType type = MediaType.IMG;
    private String source;
}