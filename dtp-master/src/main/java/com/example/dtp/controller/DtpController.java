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

    //works
    @GetMapping("/all")
    public List<DtpDto> getAllDtps() {
        return dtpOperationsService.getAllDtp(); }

    //works
    @GetMapping("/{id}")
    public DtpDto getDtpById(@PathVariable("id") UUID id) {
        return dtpOperationsService.getDtpById(id); }

    //works
    @GetMapping("/month_mid_dtp/{year}")
    public double getMonthMidCountDtpByYear(@PathVariable("year") Integer year) {
        return dtpOperationsService.getMonthMidCountDtoByYear(year); }

    //works
    @GetMapping("/punishment_stat/{id}")
    public String getPunismentStatistics(@PathVariable("id") UUID locationID) {
        return dtpOperationsService.getPunishmentStatistics(locationID); }

    //I don't understand the problem. Anthony, check it please.
    @PostMapping("/create")
    public DtpDto createDtp(@RequestBody DtpDto dto) {
        return dtpOperationsService.createDtp(dto); }

    //All POSTs don't work and the reason is known. I'll fix it tomorrow.
    @PostMapping("/set/punishment/{id}")
    public DtpDto setPunishment(@PathVariable("id") UUID id, @RequestBody String punishment) {
        return dtpOperationsService.setPunishment(id, punishment); }

    @PostMapping("/set/penalty/{id}")
    public DtpDto setPenalty(@PathVariable("id") UUID id, @RequestBody Double penalty) {
        return dtpOperationsService.setPenalty(id, penalty); }

    @PostMapping("/set/period/{id}")
    public DtpDto setPeriod(@PathVariable("id") UUID id, @RequestBody Double period) {
        return dtpOperationsService.setPeriod(id, period); }

//    @PutMapping("/update/{id}")
//    public DtpDto updateDtp(@PathVariable("id") UUID id, @RequestBody DtpDto dto) {
//        return dtpOperationsService.updateDtp(id, dto); }

}
