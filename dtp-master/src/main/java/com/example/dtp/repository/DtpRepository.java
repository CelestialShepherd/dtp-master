package com.example.dtp.repository;

import com.example.dtp.entity.DtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DtpRepository extends JpaRepository<DtpEntity, UUID> {
    // TODO: Переписать запрос, чтобы была проведена проверка не является ли поле region в location пустым
    @Query(value = "SELECT * FROM dtp d WHERE d.location_id <> NULL", nativeQuery = true)
    List<DtpEntity> findAllNotNullLocation();
}
