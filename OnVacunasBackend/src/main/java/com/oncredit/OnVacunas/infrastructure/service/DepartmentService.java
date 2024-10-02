package com.oncredit.OnVacunas.infrastructure.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oncredit.OnVacunas.api.dto.request.DepartmentReq;
import com.oncredit.OnVacunas.api.dto.response.DepartmentResp;
import com.oncredit.OnVacunas.domain.entities.DepartmentEntity;
import com.oncredit.OnVacunas.domain.repositories.DepartmentRepository;
import com.oncredit.OnVacunas.infrastructure.abstract_Services.IDepartmentService;
import com.oncredit.OnVacunas.utils.enums.SortType;
import com.oncredit.OnVacunas.utils.exception.BadRequestException;
import com.oncredit.OnVacunas.utils.messages.ErrorMessages;

@Service
@AllArgsConstructor
public class DepartmentService implements IDepartmentService {

    private static final String FIELD_BY_SORT = "name";

    @Autowired
    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentResp create(DepartmentReq request) {
        DepartmentEntity department = requestToEntity(request);
        return entityToResponse(departmentRepository.save(department));
    }

    @Override
    public DepartmentResp get(Long id) {
        return entityToResponse(find(id));
    }

    @Override
    public DepartmentResp update(DepartmentReq request, Long id) {
        DepartmentEntity existingDepartment = find(id);
        existingDepartment.setName(request.getName());
        return entityToResponse(departmentRepository.save(existingDepartment));
    }

    @Override
    public void delete(Long id) {
        DepartmentEntity department = find(id);
        departmentRepository.delete(department);
    }

    @Override
    public Page<DepartmentResp> getAll(int page, int size, SortType sortType) {
        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };

        return departmentRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    private DepartmentEntity find(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessages.idNotFound("Department")));
    }

    private DepartmentEntity requestToEntity(DepartmentReq request) {
        return DepartmentEntity.builder()
                .name(request.getName())
                .build();
    }

    private DepartmentResp entityToResponse(DepartmentEntity entity) {
        return DepartmentResp.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
