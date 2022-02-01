package com.example.dtp.mapper;

import com.example.dtp.dto.LocationDto;
import com.example.dtp.entity.LocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface LocationMapper {
    List<LocationDto> toLocationDtoList(Collection<LocationEntity> locationEntities);
    LocationEntity toLocationEntity(LocationDto locationDto);
    LocationDto toLocationDto(LocationEntity locationEntity);
    LocationEntity updateFromDto(LocationDto source, @MappingTarget LocationEntity target);
}
