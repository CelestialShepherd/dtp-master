package com.example.dtp.mapper;

import com.example.dtp.dto.DtpDto;
import com.example.dtp.dto.LocationDto;
import com.example.dtp.entity.DtpEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface DtpMapper {
    List<DtpDto> toDtpDtoList(List<DtpEntity> dtpEntities);
    DtpEntity toDtpEntity(DtpDto dtpDto);
    DtpDto toDtpDto(DtpEntity dtpEntity);
    DtpEntity updateFromDto(DtpDto source, @MappingTarget DtpEntity target);
}
