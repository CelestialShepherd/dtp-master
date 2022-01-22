package com.example.dtp.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "location")
public class LocationEntity extends BaseEntity{
    private String region;
    private String town;
    private String district;
    private String street;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    @JoinColumn(name = "dtps_id")
    private List<DtpEntity> Dtps;
}
