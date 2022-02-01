package com.example.dtp.controller;

import com.example.dtp.dto.LocationDto;
import com.example.dtp.entity.LocationEntity;
import com.example.dtp.service.LocationOperationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {
    private final LocationOperationsService LocationOperationsService;

    //works
    @GetMapping("/all")
    public List<LocationDto> getAllLocations() {
        return LocationOperationsService.getAllLocations(); }

    //works
    @GetMapping("/{id}")
    public LocationDto getLocationById(@PathVariable("id") UUID id) {
        return LocationOperationsService.getLocationById(id); }

    //works
    @PostMapping("/create")
    public LocationDto createLocation(@RequestBody LocationDto dto) {
        return LocationOperationsService.createLocation(dto); }

    //works
    @PutMapping("/update/{id}")
    public LocationDto updateLocation(@PathVariable("id") UUID id, @RequestBody LocationDto dto) {
        return LocationOperationsService.updateLocation(id, dto); }
}
