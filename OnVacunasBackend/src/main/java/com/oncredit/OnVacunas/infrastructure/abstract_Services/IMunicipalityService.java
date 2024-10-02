package com.oncredit.OnVacunas.infrastructure.abstract_Services;

import java.util.List;

import com.oncredit.OnVacunas.api.dto.request.MunicipalityReq;
import com.oncredit.OnVacunas.api.dto.response.MunicipalityResp;

public interface IMunicipalityService extends CrudService<MunicipalityReq,MunicipalityResp, Long> {
    List<MunicipalityResp> findByDepartment(Long departmentId);
    
}
