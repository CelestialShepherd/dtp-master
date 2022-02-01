package com.example.dtp.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDto {
    @NotBlank
    private String region;
    @NotBlank
    private String town;
    @NotBlank
    private String district;
    @NotBlank
    private String street;
}
