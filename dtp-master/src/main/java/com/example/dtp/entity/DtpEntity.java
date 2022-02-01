package com.example.dtp.entity;

import com.example.dtp.enums.PunishmentClass;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dtp", schema = "dtp")
public class DtpEntity extends BaseEntity {
        private String driverLicense;
        @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinColumn(name = "location_id")
        private LocationEntity location;
        private LocalDateTime timeOfDtp;
        private Boolean active;
        private PunishmentClass punishment;
        private Double penalty;
        private Integer period;
}
