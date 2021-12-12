package com.example.dtp.service;

import com.example.dtp.repository.DtpRepository;
import com.example.dtp.dto.DtpDto;
import com.example.dtp.dto.LocationDto;
import com.example.dtp.entity.DtpEntity;
import com.example.dtp.enums.PunishmentClass;
import com.example.dtp.mapper.DtpMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DtpOperationsService {
    private final DtpRepository repository;
    private final DtpMapper mapper;

    public List<DtpDto> getAllDtp() {
        return mapper.toDtpDtoList(repository.findAll());
    }

    public DtpDto getDtpById(UUID id) {
        return mapper.toDtpDto(getDtpEntityById(id));
    }

    public DtpDto createDtp(DtpDto dto) {
        DtpEntity createdDtp = repository.save(mapper.toDtpEntity(dto));
        DtpDto dtpDto = mapper.toDtpDto(createdDtp);
        return dtpDto;
    }

    public DtpDto updateDtp(UUID id, DtpDto dto) {
        DtpEntity dtp = getDtpEntityById(id);
        DtpEntity dtpUpdated = mapper.updateFromDto(dto, dtp);
        DtpEntity dtpUpdatedPersisted = repository.save(dtpUpdated);
        DtpDto dtpUpdatedDto = mapper.toDtpDto(dtpUpdatedPersisted);
        return dtpUpdatedDto;
    }

    public DtpDto setPunishment(UUID id, String punishment) {
        DtpEntity dtp = getDtpEntityById(id);
        dtp.setPunishment(PunishmentClass.convert(punishment));
        DtpEntity dtpSetPersisted = repository.save(dtp);
        DtpDto dtpSetDto = mapper.toDtpDto(dtpSetPersisted);
        return dtpSetDto;
    }

    public DtpDto setPenalty(UUID id, Double penalty) {
        DtpEntity dtp = getDtpEntityById(id);
        DtpDto dtpSetDto = mapper.toDtpDto(dtp);
        if (dtp.getPunishment().equals(PunishmentClass.PENALTY)) {
            dtp.setPenalty(penalty);
            DtpEntity dtpSetPersisted = repository.save(dtp);
            dtpSetDto = mapper.toDtpDto(dtpSetPersisted);
        }
        return dtpSetDto;
    }

    public DtpDto setPeriod(UUID id, Double period) {
        DtpEntity dtp = getDtpEntityById(id);
        DtpDto dtpSetDto = mapper.toDtpDto(dtp);
        if (dtp.getPunishment().equals(PunishmentClass.ARRESTING) || dtp.getPunishment().equals(PunishmentClass.LICENSE_DEPRIVATION)) {
            dtp.setPenalty(period);
            DtpEntity dtpSetPersisted = repository.save(dtp);
            dtpSetDto = mapper.toDtpDto(dtpSetPersisted);
        }
        return dtpSetDto;
    }

    public List<DtpDto> getDtpByLocation(LocationDto locationDto) {

        List<DtpEntity> dtpEntities = repository.findAllNotNullLocation();
        List<DtpEntity> dtpFiltered = null;

        var town = locationDto.getTown();
        var district = locationDto.getDistrict();
        var street = locationDto.getStreet();

       if (!town.isBlank()) {
            dtpFiltered = dtpEntities.stream().filter(DtpEntity -> DtpEntity.getLocation().getTown().equals(town)).collect(Collectors.toList());
        } else if (!district.isBlank()) {
            dtpFiltered = dtpEntities.stream().filter(DtpEntity -> DtpEntity.getLocation().getDistrict().equals(district)).collect(Collectors.toList());
        } else if (!street.isBlank()) {
            dtpFiltered = dtpEntities.stream().filter(DtpEntity -> DtpEntity.getLocation().getStreet().equals(street)).collect(Collectors.toList());
        }

        return mapper.toDtpDtoList(dtpFiltered);
    }

//    public List<DtpDto> getDtpByPeriod(LocalDate from, LocalDate to, List<DtpDto> dtpDtoList){
//        Timestamp dateFrom = Timestamp.valueOf(from.atTime(LocalTime.MIN));
//        Timestamp dateTo = Timestamp.valueOf(to.atTime(LocalTime.MAX));
//
//        List<DtpDto> dtpFiltered = null;
//        dtpFiltered = dtpDtoList.stream().filter(DtpDto -> DtpDto.getTimeOfDtp().isAfter(dateFrom.toLocalDateTime())).
//                filter(DtpDto -> DtpDto.getTimeOfDtp().isBefore(dateTo.toLocalDateTime())).collect(Collectors.toList());
//
//        return dtpFiltered;
//    }

    public double getMonthMidCountDtoByYear(int year) {
        List<DtpDto> dtp = getAllDtp();
        List<DtpDto> dtpFiltered = dtp.stream()
                .filter(DtpDto -> DtpDto.getTimeOfDtp().getYear() == year)
                .filter(DtpDto -> !DtpDto.getPunishment().equals(PunishmentClass.INNOCENT))
                .collect(Collectors.toList());
        return (double) dtpFiltered.size() / 12;
    }

    public String getPunishmentStatistics(LocationDto location) {
        List<DtpDto> DtpDtoByLocation = getDtpByLocation(location);
        Map<PunishmentClass, Integer> punishmentStatistics = new HashMap<>();
        {
            punishmentStatistics.put(PunishmentClass.INNOCENT, 0);
            punishmentStatistics.put(PunishmentClass.PENALTY, 0);
            punishmentStatistics.put(PunishmentClass.LICENSE_DEPRIVATION, 0);
            punishmentStatistics.put(PunishmentClass.ARRESTING, 0);
        }
        for (DtpDto dtpDto : DtpDtoByLocation) {
            switch (dtpDto.getPunishment()){
                case INNOCENT:
                    punishmentStatistics.put(PunishmentClass.values()[1], punishmentStatistics.get(PunishmentClass.values()[1]) + 1);
                    break;
                case PENALTY:
                    punishmentStatistics.put(PunishmentClass.values()[2], punishmentStatistics.get(PunishmentClass.values()[2]) + 1);
                    break;
                case LICENSE_DEPRIVATION:
                    punishmentStatistics.put(PunishmentClass.values()[3], punishmentStatistics.get(PunishmentClass.values()[3]) + 1);
                    break;
                case ARRESTING:
                    punishmentStatistics.put(PunishmentClass.values()[4], punishmentStatistics.get(PunishmentClass.values()[4]) + 1);
                    break;
                default:
                    break;
            }
        }
        return punishmentStatistics.toString();
    }

    public DtpEntity getDtpEntityById(UUID id) {
        Optional<DtpEntity> optionalDtp = repository.findById(id);
        if (optionalDtp.isEmpty()) {
            log.error("getDtpById.out - dtp with ID {} not found",id);
            throw new RuntimeException(String.format("dtp with id %s not found",id));
        }
        return optionalDtp.get();
    }
}
