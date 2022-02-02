package com.example.dtp.connector;

import com.example.dtp.dto.DriverDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class DriverConnector {

    private final RestTemplate restTemplate;

    @Value("${external.driver-service.host}")
    private String driverServiceHost;

    @Value("${external.driver-service.port}")
    private String driverServicePort;

    public DriverDto getDriverLicense(String license) {
        var uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(driverServiceHost)
                .port(driverServicePort)
                .path("driver/license/{license}")
                .buildAndExpand(license)
                .toUriString();
        log.info("requesting driver_service uri: {}", uri);
        var result = restTemplate.getForEntity(uri, DriverDto.class);
        log.info("requested driver_service by uri: {}, result: {}", uri, result);
        return result.getBody();
    }
}
