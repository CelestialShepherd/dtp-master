package com.example.dtp.service;

import com.example.dtp.Repository.DtpRepository;
import com.example.dtp.Repository.LocationRepository;
import com.example.dtp.dto.DtpDto;
import com.example.dtp.dto.LocationDto;
import com.example.dtp.entity.DtpEntity;
import com.example.dtp.entity.LocationEntity;
import com.example.dtp.mapper.DtpMapper;
import com.example.dtp.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationOperationsService {
    private final LocationRepository repository;
    private final LocationMapper mapper;

    public List<LocationDto> getAllLocations() {
        return mapper.toLocationDtoList(repository.findAll());
    }

    public LocationDto getLocationById(UUID id) {
        return mapper.toLocationDto(getLocationEntityById(id));
    }

    public LocationDto createLocation(LocationDto dto) {
        LocationEntity createdLocation = repository.save(mapper.toLocationEntity(dto));
        LocationDto locationDto = mapper.toLocationDto(createdLocation);
        return locationDto;
    }

    public LocationDto updateLocation(UUID id, LocationDto dto) {
        LocationEntity location = getLocationEntityById(id);
        LocationEntity locationUpdated = mapper.updateFromDto(dto, location);
        LocationEntity locationUpdatedPersisted = repository.save(locationUpdated);
        LocationDto Location = mapper.toLocationDto(locationUpdatedPersisted);
        return Location;
    }

    public LocationEntity getLocationEntityById(UUID id) {
        Optional<LocationEntity> optionalLocation = repository.findById(id);
        if (optionalLocation.isEmpty()) {
            log.error("getLocationById.out - Location with ID {} not found", id);
        }
        return optionalLocation.get();
    }
}
