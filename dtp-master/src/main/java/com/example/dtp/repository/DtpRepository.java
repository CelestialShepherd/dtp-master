package com.example.dtp.repository;

import com.example.dtp.entity.DtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DtpRepository extends JpaRepository<DtpEntity, UUID> {
    @Query(value = "SELECT * FROM dtp WHERE dtp.location_id IS NOT NULL", nativeQuery = true)
    List<DtpEntity> findAllNotNullLocation();
}
