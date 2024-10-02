package com.oncredit.OnVacunas.api.error_handler;

import java.util.List;

import com.oncredit.OnVacunas.api.dto.errors.BaseErrorResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper=true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorsResp extends BaseErrorResponse {
    private List<String> errors;    
}
