package com.example.dtp.controller;

import com.example.dtp.dto.DtpDto;
import com.example.dtp.dto.LocationDto;
import com.example.dtp.entity.DtpEntity;
import com.example.dtp.service.DtpOperationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<DtpDto> getAllRTAs() {
        return dtpOperationsService.getAllDtp(); }

    @GetMapping("/{id}")
    public DtpDto getRTAById(@PathVariable("id") UUID id) {
        return dtpOperationsService.getDtpById(id); }

    @GetMapping("/mid_dtp_month/{year}")
    public double getMidCountDtpByMonth(@RequestBody LocationDto locationDto, @PathVariable("year") Integer year) {
        return dtpOperationsService.getMidCountDtoByMonth(year, dtpOperationsService.getDtpByLocation(locationDto)); }

    @GetMapping("/punishment_stat")
    public String getMidCountRTAByMonth(@RequestBody LocationDto locationDto) {
        return dtpOperationsService.getPunishmentStatistics(dtpOperationsService.getDtpByLocation(locationDto)); }

        //TODO: Переписать под DtpDto

    @PostMapping("/create")
    public DtpDto createDtp(@RequestBody DtpDto dto) {
        return dtpOperationsService.createDtp(dto); }

    @PutMapping("/update/{id}")
    public DtpDto updateDtp(@PathVariable("id") UUID id, @RequestBody DtpDto dto) {
        return dtpOperationsService.updateDtp(id, dto); }

    @PostMapping("/set/punishment/{id}")
    public DtpDto setPunishment(@PathVariable("id") UUID id, @RequestBody String punishment) {
        return dtpOperationsService.setPunishment(id, punishment); }

    @PostMapping("/set/penalty/{id}")
    public DtpDto setPenalty(@PathVariable("id") UUID id, @RequestBody Double penalty) {
        return dtpOperationsService.setPenalty(id, penalty); }

    @PostMapping("/set/period/{id}")
    public DtpDto setPeriod(@PathVariable("id") UUID id, @RequestBody Double period) {
        return dtpOperationsService.setPeriod(id, period); }

}
