package com.teb.hotelservice.mapper;

import com.teb.hotelservice.model.dto.HotelDto;
import com.teb.hotelservice.model.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HotelMapper {

    HotelMapper INSTANCE = Mappers.getMapper(HotelMapper.class);

    Hotel mapHotelDtoToHotel(HotelDto hotelDto);

    HotelDto mapHotelToHotelDto(Hotel hotel);

}
