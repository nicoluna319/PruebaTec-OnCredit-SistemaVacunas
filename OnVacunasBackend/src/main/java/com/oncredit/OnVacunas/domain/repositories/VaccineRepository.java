package com.oncredit.OnVacunas.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oncredit.OnVacunas.domain.entities.VaccineEntity;

@Repository
public interface VaccineRepository extends JpaRepository<VaccineEntity, Long> {

}
