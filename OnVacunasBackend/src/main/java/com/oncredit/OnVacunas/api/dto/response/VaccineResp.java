package com.oncredit.OnVacunas.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaccineResp {

    private Long id;
    private String name;
    private Integer maxAge;
}
