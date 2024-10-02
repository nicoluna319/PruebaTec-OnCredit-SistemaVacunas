package com.oncredit.OnVacunas.infrastructure.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oncredit.OnVacunas.api.dto.request.MunicipalityReq;
import com.oncredit.OnVacunas.api.dto.response.MunicipalityResp;
import com.oncredit.OnVacunas.domain.entities.MunicipalityEntity;
import com.oncredit.OnVacunas.domain.entities.DepartmentEntity;
import com.oncredit.OnVacunas.domain.repositories.MunicipalityRepository;
import com.oncredit.OnVacunas.domain.repositories.DepartmentRepository;
import com.oncredit.OnVacunas.infrastructure.abstract_Services.IMunicipalityService;
import com.oncredit.OnVacunas.utils.enums.SortType;
import com.oncredit.OnVacunas.utils.exception.BadRequestException;
import com.oncredit.OnVacunas.utils.messages.ErrorMessages;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MunicipalityService implements IMunicipalityService {

    private static final String FIELD_BY_SORT = "name"; 

    @Autowired
    private final MunicipalityRepository municipalityRepository;

    @Autowired
    private final DepartmentRepository departmentRepository;

    @Override
    public MunicipalityResp create(MunicipalityReq request) {
        // Buscar el departamento antes de crear el municipio
        DepartmentEntity department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Department")));

        MunicipalityEntity municipality = this.requestToEntity(request);
        municipality.setDepartment(department); // Asociar el departamento

        return this.entityToResponse(this.municipalityRepository.save(municipality));
    }

    @Override
    public MunicipalityResp get(Long id) {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public MunicipalityResp update(MunicipalityReq request, Long id) {
        MunicipalityEntity existingMunicipality = this.find(id);

        // Buscar el departamento y asociarlo al municipio
        DepartmentEntity department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Department")));

        MunicipalityEntity municipalityUpdate = this.requestToEntity(request);
        municipalityUpdate.setId(id);
        municipalityUpdate.setDepartment(department); // Actualizar la relación con el departamento

        return this.entityToResponse(this.municipalityRepository.save(municipalityUpdate));
    }

    @Override
    public void delete(Long id) {
        MunicipalityEntity municipality = this.find(id);
        this.municipalityRepository.delete(municipality);
    }

    @Override
    public Page<MunicipalityResp> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;

        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };

        return this.municipalityRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    // Método para encontrar un municipio por su ID o lanzar una excepción si no existe
    private MunicipalityEntity find(Long id) {
        return this.municipalityRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Municipality")));
    }

    public List<MunicipalityResp> findByDepartment(Long departmentId) {
    return municipalityRepository.findByDepartmentId(departmentId)
            .stream()
            .map(this::entityToResponse)
            .collect(Collectors.toList());
}


    // Conversión de entidad a Response DTO
    private MunicipalityResp entityToResponse(MunicipalityEntity entity) {
        return MunicipalityResp.builder()
                .id(entity.getId())
                .name(entity.getName())
                .departmentName(entity.getDepartment().getName()) // Obtener el nombre del departamento
                .build();
    }

    // Conversión de Request DTO a entidad
    private MunicipalityEntity requestToEntity(MunicipalityReq request) {
        return MunicipalityEntity.builder()
                .name(request.getName())
                .build();
    }

}
