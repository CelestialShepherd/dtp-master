package com.example.dtp.service;

import com.example.dtp.Repository.DtpRepository;
import com.example.dtp.dto.DtpDto;
import com.example.dtp.dto.LocationDto;
import com.example.dtp.entity.DtpEntity;
import com.example.dtp.enums.PunishmentClass;
import com.example.dtp.mapper.DtpMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        List<DtpEntity> dtpEntities = repository.findAll();
        List<DtpEntity> dtpFiltered = null;

        var region = locationDto.getRegion();
        var town = locationDto.getTown();
        var district = locationDto.getDistrict();
        var street = locationDto.getStreet();

        if (!region.isBlank()) {
            dtpFiltered = dtpEntities.stream().filter(DtpEntity -> DtpEntity.getLocation().getRegion().equals(region)).collect(Collectors.toList());
        } else if (!town.isBlank()) {
            dtpFiltered = dtpEntities.stream().filter(DtpEntity -> DtpEntity.getLocation().getTown().equals(town)).collect(Collectors.toList());
        } else if (!district.isBlank()) {
            dtpFiltered = dtpEntities.stream().filter(DtpEntity -> DtpEntity.getLocation().getDistrict().equals(district)).collect(Collectors.toList());
        } else if (!street.isBlank()) {
            dtpFiltered = dtpEntities.stream().filter(DtpEntity -> DtpEntity.getLocation().getStreet().equals(street)).collect(Collectors.toList());
        }

        return mapper.toDtpDtoList(dtpFiltered);
    }

    public List<DtpDto> getDtpByPeriod(LocalDate from, LocalDate to, List<DtpDto> dtpDtoList){
        Timestamp dateFrom = Timestamp.valueOf(from.atTime(LocalTime.MIN));
        Timestamp dateTo = Timestamp.valueOf(to.atTime(LocalTime.MAX));

        List<DtpDto> dtpFiltered = null;
        dtpFiltered = dtpDtoList.stream().filter(DtpDto -> DtpDto.getTimeOfDtp().isAfter(dateFrom.toLocalDateTime())).
                filter(DtpDto -> DtpDto.getTimeOfDtp().isBefore(dateTo.toLocalDateTime())).collect(Collectors.toList());

        return dtpFiltered;
    }

    public double getMidCountDtoByMonth(int year, List<DtpDto> ListDtpDto) {
        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.set(year, 1, 1, 0, 0, 0);
        Calendar calendarTo = Calendar.getInstance();
        calendarTo.set(year, 12, 31, 23, 59, 59);
        LocalDate localDateFrom = LocalDateTime.ofInstant(calendarFrom.toInstant(), calendarFrom.getTimeZone().toZoneId()).toLocalDate();
        LocalDate localDateTo = LocalDateTime.ofInstant(calendarTo.toInstant(), calendarTo.getTimeZone().toZoneId()).toLocalDate();
        List<DtpDto> ListFiltered = getDtpByPeriod(localDateFrom, localDateTo, ListDtpDto);
        ListFiltered = ListFiltered.stream().filter(DtpDto -> (DtpDto.getPunishment().equals(PunishmentClass.PENALTY) || DtpDto.getPunishment().equals(PunishmentClass.LICENSE_DEPRIVATION) || DtpDto.getPunishment().equals(PunishmentClass.ARRESTING))).collect(Collectors.toList());
        ListFiltered = ListFiltered.stream().s

        return (double) ListFiltered.size() / 12;
    }

    public String getPunishmentStatistics(List<DtpDto> ListDtpDto) {
        Map<PunishmentClass, Integer> punishmentStatistics = new HashMap<>();
        {
            punishmentStatistics.put(PunishmentClass.INNOCENT, 0);
            punishmentStatistics.put(PunishmentClass.PENALTY, 0);
            punishmentStatistics.put(PunishmentClass.LICENSE_DEPRIVATION, 0);
            punishmentStatistics.put(PunishmentClass.ARRESTING, 0);
        }
        for (DtpDto dtpDto : ListDtpDto) {
            if (dtpDto.getPunishment().equals(PunishmentClass.values()[1]))
                punishmentStatistics.put(PunishmentClass.values()[1], punishmentStatistics.get(PunishmentClass.values()[1]) + 1);
            else if (dtpDto.getPunishment().equals(PunishmentClass.values()[2]))
                punishmentStatistics.put(PunishmentClass.values()[2], punishmentStatistics.get(PunishmentClass.values()[2]) + 1);
            else if (dtpDto.getPunishment().equals(PunishmentClass.values()[3]))
                punishmentStatistics.put(PunishmentClass.values()[3], punishmentStatistics.get(PunishmentClass.values()[3]) + 1);
            else if (dtpDto.getPunishment().equals(PunishmentClass.values()[4]))
                punishmentStatistics.put(PunishmentClass.values()[4], punishmentStatistics.get(PunishmentClass.values()[4]) + 1);
        }
        return punishmentStatistics.toString();
    }

    public DtpEntity getDtpEntityById(UUID id) {
        Optional<DtpEntity> optionalDtp = repository.findById(id);
        if (optionalDtp.isEmpty()) {
            log.error("getDtpById.out - dtp with ID {} not found", id);
        }
        return optionalDtp.get(); //comment
    }
}
