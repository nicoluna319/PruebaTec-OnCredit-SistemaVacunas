package com.oncredit.OnVacunas.api.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ChildResp {

    private Long id;
    private String name;
    private Integer age;
    private String municipalityName; 
    private boolean hasVaccines;  
    private List<String> vaccines;
}
