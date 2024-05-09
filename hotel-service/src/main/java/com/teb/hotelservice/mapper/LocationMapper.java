package com.teb.hotelservice.mapper;

import com.teb.hotelservice.model.dto.LocationDto;
import com.teb.hotelservice.model.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    Location mapLocationDtoToLocation(LocationDto locationDto);

    LocationDto mapLocationToLocationDto(Location location);

}
