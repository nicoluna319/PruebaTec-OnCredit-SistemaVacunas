package com.oncredit.OnVacunas.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MunicipalityReq {
    @NotNull(message = "El nombre del municipio no puede ser nulo")
    private String name;

    @NotNull(message = "El ID del departamento no puede ser nulo")
    private Long departmentId;
}
