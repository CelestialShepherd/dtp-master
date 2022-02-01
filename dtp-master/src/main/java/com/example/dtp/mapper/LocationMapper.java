package com.example.dtp.mapper;

import com.example.dtp.dto.LocationDto;
import com.example.dtp.entity.LocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocationMapper {
    LocationDto toLocationDto(LocationEntity locationEntity);
    List<LocationDto> toLocationDtoList(List<LocationEntity> locationEntities);
    LocationEntity toLocationEntity(LocationDto locationDto);
    LocationEntity updateFromDto(LocationDto source, @MappingTarget LocationEntity target);
}
