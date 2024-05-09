package com.teb.hotelservice.service.impl;

import com.teb.hotelservice.mapper.LocationMapper;
import com.teb.hotelservice.model.dto.LocationDto;
import com.teb.hotelservice.model.entity.Location;
import com.teb.hotelservice.repository.LocationRepository;
import com.teb.hotelservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public List<LocationDto> findAll() {
        log.info("Finding all locations..");
        return locationRepository.findAll().stream()
                .map(LocationMapper.INSTANCE::mapLocationToLocationDto)
                .toList();
    }

    @Override
    public LocationDto save(LocationDto locationDto) {
        log.info("Saving hotel with city {}", locationDto.getCity());
        Location locationFromRq = LocationMapper.INSTANCE.mapLocationDtoToLocation(locationDto);
        Location locationToReturn = locationRepository.save(locationFromRq);
        return LocationMapper.INSTANCE.mapLocationToLocationDto(locationToReturn);
    }

}
