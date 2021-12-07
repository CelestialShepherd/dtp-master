package com.example.dtp.Repository;

import com.example.dtp.dto.DtpDto;
import com.example.dtp.entity.DtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DtpRepository extends JpaRepository<DtpEntity, UUID> {

}
