package com.oncredit.OnVacunas.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oncredit.OnVacunas.domain.entities.MunicipalityEntity;

@Repository
public interface MunicipalityRepository extends JpaRepository<MunicipalityEntity, Long> {


    List<MunicipalityEntity> findByDepartmentId(Long departmentId);
}
