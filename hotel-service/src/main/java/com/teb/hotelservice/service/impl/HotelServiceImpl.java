package com.teb.hotelservice.service.impl;

import com.teb.hotelservice.mapper.HotelMapper;
import com.teb.hotelservice.model.dto.HotelDto;
import com.teb.hotelservice.model.entity.Hotel;
import com.teb.hotelservice.repository.HotelRepository;
import com.teb.hotelservice.service.HotelService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public List<HotelDto> findAll() {
        log.info("Finding all hotels..");
        return hotelRepository.findAll().stream()
                .map(HotelMapper.INSTANCE::mapHotelToHotelDto)
                .toList();
    }

    @Override
    public HotelDto findById(Long id) {
        log.info("Finding hotel with id {}..", id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(NotFoundException::new);
        return HotelMapper.INSTANCE.mapHotelToHotelDto(hotel);
    }

    @Override
    public HotelDto save(HotelDto hotelDto) {
        log.info("Saving hotel with name {}", hotelDto.getName());
        Hotel hotelFromRq = HotelMapper.INSTANCE.mapHotelDtoToHotel(hotelDto);
        Hotel hotelToReturn = hotelRepository.save(hotelFromRq);
        return HotelMapper.INSTANCE.mapHotelToHotelDto(hotelToReturn);
    }

    @Override
    public HotelDto update(HotelDto hotelDto, Long id) {
        log.info("Updating hotel with id {}", id);
        findById(id);
        Hotel hotelFromRq = HotelMapper.INSTANCE.mapHotelDtoToHotel(hotelDto);
        hotelFromRq.setId(id);
        Hotel hotelToReturn = hotelRepository.save(hotelFromRq);
        return HotelMapper.INSTANCE.mapHotelToHotelDto(hotelToReturn);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting hotel with id {}..", id);
        hotelRepository.deleteById(id);
    }
}
