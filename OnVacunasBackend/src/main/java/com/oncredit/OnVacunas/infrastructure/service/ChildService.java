package com.oncredit.OnVacunas.infrastructure.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oncredit.OnVacunas.api.dto.request.ChildReq;
import com.oncredit.OnVacunas.api.dto.response.ChildResp;
import com.oncredit.OnVacunas.api.dto.response.MunicipalityChildrenSummaryResp;
import com.oncredit.OnVacunas.domain.entities.ChildEntity;
import com.oncredit.OnVacunas.domain.entities.MunicipalityEntity;
import com.oncredit.OnVacunas.domain.entities.VaccineEntity;
import com.oncredit.OnVacunas.domain.repositories.ChildRepository;
import com.oncredit.OnVacunas.domain.repositories.MunicipalityRepository;
import com.oncredit.OnVacunas.domain.repositories.VaccineRepository;
import com.oncredit.OnVacunas.infrastructure.abstract_Services.IChildService;
import com.oncredit.OnVacunas.utils.enums.SortType;
import com.oncredit.OnVacunas.utils.exception.BadRequestException;
import com.oncredit.OnVacunas.utils.messages.ErrorMessages;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChildService implements IChildService {

    private static final String FIELD_BY_SORT = "name";

    @Autowired
    private final ChildRepository childRepository;

    @Autowired
    private final MunicipalityRepository municipalityRepository;

    @Autowired
    private VaccineRepository vaccineRepository;

    @Override
    public ChildResp create(ChildReq request) {
        // Asociar el municipio correctamente
        MunicipalityEntity municipality = municipalityRepository.findById(request.getMunicipalityId())
                .orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Municipality")));

        // Convertir el DTO en entidad y asociar el municipio
        ChildEntity child = this.requestToEntity(request);
        child.setMunicipality(municipality);
        child.setVaccines(new ArrayList<>());

        return this.entityToResponse(this.childRepository.save(child));
    }

    @Override
    public ChildResp get(Long id) {
    return this.entityToResponse(this.find(id));
    }

    @Override
    public ChildResp update(ChildReq request, Long id) {
        // Buscar el niño existente
        ChildEntity existingChild = this.find(id);

        // Buscar el municipio y verificar que existe
        MunicipalityEntity municipality = municipalityRepository.findById(request.getMunicipalityId())
                .orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Municipality")));

        // Convertir el DTO en entidad, conservar las vacunas existentes y actualizar el municipio
        ChildEntity childUpdate = this.requestToEntity(request);
        childUpdate.setId(id);
        childUpdate.setVaccines(existingChild.getVaccines());
        childUpdate.setMunicipality(municipality);

        return this.entityToResponse(this.childRepository.save(childUpdate));
    }

    @Override
    public void delete(Long id) {
        ChildEntity child = this.find(id);
        this.childRepository.delete(child);
    }

    @Override
public Page<ChildResp> getAll(int page, int size, SortType sortType) {
    if (page < 0) page = 0;

    PageRequest pagination = switch (sortType) {
        case NONE -> PageRequest.of(page, size);
        case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
        case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
    };

    return this.childRepository.findAll(pagination)
            .map(this::entityToResponse);
}


    // Método para encontrar un niño por su ID o lanzar una excepción si no existe
    private ChildEntity find(Long id) {
        return this.childRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Child")));
    }

    // Método para asociar un niño a un municipio
    @Override
    public ChildResp associateChildToMunicipality(Long childId, Long municipalityId) {
    ChildEntity child = find(childId);
    
    if (child.getMunicipality() != null && !child.getMunicipality().getId().equals(municipalityId)) {
        throw new BadRequestException("El niño ya está asociado a un municipio. Debe cambiar la asociación primero.");
    }

    MunicipalityEntity municipality = municipalityRepository.findById(municipalityId)
            .orElseThrow(() -> new BadRequestException("Municipality not found"));

    child.setMunicipality(municipality);
    return entityToResponse(childRepository.save(child));
}

public ChildResp applyVaccineToChild(Long childId, Long vaccineId) {
    ChildEntity child = find(childId);
    VaccineEntity vaccine = vaccineRepository.findById(vaccineId)
            .orElseThrow(() -> new BadRequestException("Vaccine not found"));

    if (child.getAge() > vaccine.getMaxAge()) {
        throw new BadRequestException("El niño es demasiado mayor para recibir esta vacuna.");
    }

    child.getVaccines().add(vaccine);
    return entityToResponse(childRepository.save(child));
}


public List<MunicipalityChildrenSummaryResp> getChildrenSummaryByMunicipality() {
    // Obtener todos los municipios
    List<MunicipalityEntity> municipalities = municipalityRepository.findAll();

    // Para cada municipio, obtener los niños y calcular cuántos están vacunados
    return municipalities.stream().map(municipality -> {
        // Obtener niños del municipio actual
        List<ChildEntity> children = childRepository.findByMunicipalityId(municipality.getId());

        // Contar cuántos niños están vacunados (tienen al menos una vacuna)
        long vaccinatedCount = children.stream().filter(child -> !child.getVaccines().isEmpty()).count();

        // Crear la respuesta del resumen
        return MunicipalityChildrenSummaryResp.builder()
                .municipalityName(municipality.getName())
                .totalChildren(children.size())
                .vaccinatedChildren(vaccinatedCount)
                .build();
    }).collect(Collectors.toList());
}

public Double getAverageAgeByMunicipality(Long municipalityId) {
    // Obtener los niños del municipio especificado
    List<ChildEntity> children = childRepository.findByMunicipalityId(municipalityId);

    // Verificar si no hay niños en el municipio
    if (children.isEmpty()) {
        throw new BadRequestException("No hay niños asociados a este municipio.");
    }

    // Calcular el promedio de edad
    return children.stream()
            .mapToInt(ChildEntity::getAge)
            .average()
            .orElse(0.0);  // Si hay algún problema con los datos, devolver 0.0
}




private ChildResp entityToResponse(ChildEntity entity) {

    List<String> vaccineNames = entity.getVaccines().stream()
            .map(VaccineEntity::getName)
            .collect(Collectors.toList());


    return ChildResp.builder()
            .id(entity.getId())
            .name(entity.getName())
            .age(entity.getAge())
            .municipalityName(entity.getMunicipality().getName())
            .hasVaccines(!vaccineNames.isEmpty())  
            .vaccines(vaccineNames)
            .build();
}


    // Conversión de DTO de solicitud a entidad
    private ChildEntity requestToEntity(ChildReq request) {
        return ChildEntity.builder()
                .name(request.getName())
                .age(request.getAge())
                .build();
    }

}
