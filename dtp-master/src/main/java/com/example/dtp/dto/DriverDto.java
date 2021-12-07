package com.example.dtp.dto;

import com.example.dtp.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDto {
    private String firstName;
    private String lastName;
    private String licenseNumber;
    private Integer drivingExp;
    private Category category;
}
