package com.oncredit.OnVacunas.api.controllers;

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

import com.oncredit.OnVacunas.api.dto.request.VaccineReq;
import com.oncredit.OnVacunas.api.dto.response.VaccineResp;
import com.oncredit.OnVacunas.infrastructure.abstract_Services.IVaccineService;
import com.oncredit.OnVacunas.utils.enums.SortType;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/vaccines")
@AllArgsConstructor
public class VaccineController {

    private final IVaccineService vaccineService;

    @PostMapping
    public ResponseEntity<VaccineResp> createVaccine(@RequestBody VaccineReq request) {
        VaccineResp response = vaccineService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaccineResp> getVaccine(@PathVariable Long id) {
        VaccineResp response = vaccineService.get(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaccineResp> updateVaccine(@RequestBody VaccineReq request, @PathVariable Long id) {
        VaccineResp response = vaccineService.update(request, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaccine(@PathVariable Long id) {
        vaccineService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<VaccineResp>> getAllVaccines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NONE") SortType sortType) {
        Page<VaccineResp> response = vaccineService.getAll(page, size, sortType);
        return ResponseEntity.ok(response);
    }
}
