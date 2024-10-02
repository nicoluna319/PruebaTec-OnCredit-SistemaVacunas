package com.oncredit.OnVacunas.infrastructure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oncredit.OnVacunas.api.dto.request.VaccineReq;
import com.oncredit.OnVacunas.api.dto.response.VaccineResp;
import com.oncredit.OnVacunas.domain.entities.VaccineEntity;
import com.oncredit.OnVacunas.domain.repositories.VaccineRepository;
import com.oncredit.OnVacunas.infrastructure.abstract_Services.IVaccineService;
import com.oncredit.OnVacunas.utils.enums.SortType;
import com.oncredit.OnVacunas.utils.exception.BadRequestException;
import com.oncredit.OnVacunas.utils.messages.ErrorMessages;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VaccineService implements IVaccineService {

    private static final String FIELD_BY_SORT = "name"; 

    @Autowired
    private final VaccineRepository vaccineRepository;

    @Override
    public VaccineResp create(VaccineReq request) {
        // Validar si la edad máxima es válida
        if (request.getMaxAge() < 0) {
            throw new BadRequestException("La edad máxima no puede ser menor a 0.");
        }

        VaccineEntity vaccine = this.requestToEntity(request);
        return this.entityToResponse(this.vaccineRepository.save(vaccine));
    }

    @Override
    public VaccineResp get(Long id) {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public VaccineResp update(VaccineReq request, Long id) {
        // Buscar la vacuna existente
        VaccineEntity existingVaccine = this.find(id);

        // Validar si la edad máxima es válida
        if (request.getMaxAge() < 0) {
            throw new BadRequestException("La edad máxima no puede ser menor a 0.");
        }

        // Actualizar los campos de la vacuna
        VaccineEntity vaccineUpdate = this.requestToEntity(request);
        vaccineUpdate.setId(id); // Mantener el ID de la vacuna existente

        return this.entityToResponse(this.vaccineRepository.save(vaccineUpdate));
    }

    @Override
    public void delete(Long id) {
        VaccineEntity vaccine = this.find(id);
        this.vaccineRepository.delete(vaccine);
    }

    @Override
    public Page<VaccineResp> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;

        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };

        return this.vaccineRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    // Método para encontrar una vacuna por su ID o lanzar una excepción si no existe
    private VaccineEntity find(Long id) {
        return this.vaccineRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Vaccine")));
    }

    // Conversión de entidad a Response DTO
    private VaccineResp entityToResponse(VaccineEntity entity) {
        return VaccineResp.builder()
                .id(entity.getId())
                .name(entity.getName())
                .maxAge(entity.getMaxAge())
                .build();
    }

    // Conversión de Request DTO a entidad
    private VaccineEntity requestToEntity(VaccineReq request) {
        return VaccineEntity.builder()
                .name(request.getName())
                .maxAge(request.getMaxAge())
                .build();
    }

}
