package com.oncredit.OnVacunas.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MunicipalityChildrenSummaryResp {

    private String municipalityName;  // Nombre del municipio
    private long totalChildren;       // Total de niños en el municipio
    private long vaccinatedChildren;  // Total de niños vacunados en el municipio
}
