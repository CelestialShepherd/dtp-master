package com.example.dtp.dto;

import com.example.dtp.enums.PunishmentClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtpDto {
    private String driverLicense;
    private LocalDateTime timeOfDtp;
    private Boolean active;
    private PunishmentClass punishment;
    private Double penalty;
}
