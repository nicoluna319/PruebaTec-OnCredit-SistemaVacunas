package com.oncredit.OnVacunas.api.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oncredit.OnVacunas.api.dto.request.MunicipalityReq;
import com.oncredit.OnVacunas.api.dto.response.MunicipalityResp;
import com.oncredit.OnVacunas.infrastructure.abstract_Services.IMunicipalityService;
import com.oncredit.OnVacunas.utils.enums.SortType;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/municipalities")
@AllArgsConstructor
public class MunicipalityController {

    private final IMunicipalityService municipalityService;

    @PostMapping
    public ResponseEntity<MunicipalityResp> createMunicipality(@RequestBody MunicipalityReq request) {
        MunicipalityResp response = municipalityService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MunicipalityResp> getMunicipality(@PathVariable Long id) {
        MunicipalityResp response = municipalityService.get(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MunicipalityResp> updateMunicipality(@RequestBody MunicipalityReq request, @PathVariable Long id) {
        MunicipalityResp response = municipalityService.update(request, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMunicipality(@PathVariable Long id) {
        municipalityService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<MunicipalityResp>> getAllMunicipalities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NONE") SortType sortType) {
        Page<MunicipalityResp> response = municipalityService.getAll(page, size, sortType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-department/{departmentId}")
public ResponseEntity<List<MunicipalityResp>> getMunicipalitiesByDepartment(@PathVariable Long departmentId) {
    return ResponseEntity.ok(municipalityService.findByDepartment(departmentId));
}

}
