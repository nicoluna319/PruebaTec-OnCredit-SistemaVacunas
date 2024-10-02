package com.oncredit.OnVacunas.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oncredit.OnVacunas.domain.entities.ChildEntity;

@Repository
public interface ChildRepository extends JpaRepository<ChildEntity, Long> {

    
    List<ChildEntity> findByMunicipalityId(Long municipalityId);

    
}