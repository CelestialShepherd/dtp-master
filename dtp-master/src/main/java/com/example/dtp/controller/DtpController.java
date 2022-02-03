package com.example.dtp.controller;

import com.example.dtp.dto.DtpDto;
import com.example.dtp.dto.LocationDto;
import com.example.dtp.entity.DtpEntity;
import com.example.dtp.service.DtpOperationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dtp")
public class DtpController {
    private final DtpOperationsService dtpOperationsService;

    @GetMapping("/all")
    public List<DtpDto> getAllDtp() {
        return dtpOperationsService.getAllDtp(); }

    @GetMapping("/{id}")
    public DtpDto getDtpById(@PathVariable("id") UUID id) {
        return dtpOperationsService.getDtpById(id); }

    @GetMapping("/month_mid_dtp/{year}")
    public double getMonthMidCountDtpByYear(@PathVariable("year") Integer year) {
        return dtpOperationsService.getMonthMidCountDtoByYear(year); }

    @GetMapping("/punishment_stat")
    public String getPunishmentStatistics(@RequestBody LocationDto locationDto) {
        return dtpOperationsService.getPunishmentStatistics(locationDto); }

    @PostMapping("/create")
    public DtpDto createDtp(@RequestBody DtpDto dto) {
        return dtpOperationsService.createDtp(dto); }

        //Для проверки отработки в Postman необходимо во вкладке Body в выпадающем меню выбрать JSON вместо Text
    @PutMapping("/update/{id}")
    public DtpDto updateDtp(@PathVariable("id") UUID id, @RequestBody DtpDto dto) {
        return dtpOperationsService.updateDtp(id, dto); }

    @PutMapping("/set/punishment/{id}")
    public DtpDto setPunishment(@PathVariable("id") UUID id, @RequestBody String punishment) {
        return dtpOperationsService.setPunishment(id, punishment); }

    @PutMapping("/set/penalty/{id}")
    public DtpDto setPenalty(@PathVariable("id") UUID id, @RequestBody Double penalty) {
        return dtpOperationsService.setPenalty(id, penalty); }

    @PutMapping("/set/period/{id}")
    public DtpDto setPeriod(@PathVariable("id") UUID id, @RequestBody Integer period) {
        return dtpOperationsService.setPeriod(id, period); }

}
