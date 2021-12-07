package com.example.dtp.Repository;

import com.example.dtp.entity.DtpEntity;
import com.example.dtp.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, UUID> {
}
