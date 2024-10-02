package com.oncredit.OnVacunas.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class VaccineReq {

     @NotNull(message = "El nombre de la vacuna no puede ser nulo")
    private String name;

    @NotNull(message = "La edad máxima no puede ser nula")
    @Min(value = 0, message = "La edad máxima debe ser mayor o igual a 0")
    private Integer maxAge;
    
}
