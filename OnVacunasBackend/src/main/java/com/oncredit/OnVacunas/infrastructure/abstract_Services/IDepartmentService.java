package com.oncredit.OnVacunas.infrastructure.abstract_Services;

import org.springframework.data.domain.Page;

import com.oncredit.OnVacunas.api.dto.request.DepartmentReq;
import com.oncredit.OnVacunas.api.dto.response.DepartmentResp;
import com.oncredit.OnVacunas.utils.enums.SortType;

public interface IDepartmentService {
    DepartmentResp create(DepartmentReq request);
    DepartmentResp get(Long id);
    DepartmentResp update(DepartmentReq request, Long id);
    void delete(Long id);
    Page<DepartmentResp> getAll(int page, int size, SortType sortType);
}
