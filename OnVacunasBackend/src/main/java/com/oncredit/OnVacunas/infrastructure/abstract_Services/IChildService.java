package com.oncredit.OnVacunas.infrastructure.abstract_Services;

import java.util.List;

import com.oncredit.OnVacunas.api.dto.request.ChildReq;
import com.oncredit.OnVacunas.api.dto.response.ChildResp;
import com.oncredit.OnVacunas.api.dto.response.MunicipalityChildrenSummaryResp;


public interface IChildService extends CrudService<ChildReq, ChildResp, Long> {

    ChildResp associateChildToMunicipality(Long childId, Long municipalityId);
    ChildResp applyVaccineToChild(Long childId, Long vaccineId);
    List<MunicipalityChildrenSummaryResp> getChildrenSummaryByMunicipality();
    Double getAverageAgeByMunicipality(Long municipalityId);
    
}
