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

import com.oncredit.OnVacunas.api.dto.request.ChildReq;
import com.oncredit.OnVacunas.api.dto.response.ChildResp;
import com.oncredit.OnVacunas.api.dto.response.MunicipalityChildrenSummaryResp;
import com.oncredit.OnVacunas.infrastructure.abstract_Services.IChildService;
import com.oncredit.OnVacunas.utils.enums.SortType;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/children")
@AllArgsConstructor
public class ChildController {

    private final IChildService childService;

    @PostMapping
    public ResponseEntity<ChildResp> createChild(@RequestBody ChildReq request) {
        ChildResp response = childService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChildResp> getChild(@PathVariable Long id) {
        ChildResp response = childService.get(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChildResp> updateChild(@RequestBody ChildReq request, @PathVariable Long id) {
        ChildResp response = childService.update(request, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChild(@PathVariable Long id) {
        childService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ChildResp>> getAllChildren(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NONE") SortType sortType) {
        Page<ChildResp> response = childService.getAll(page, size, sortType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/average-age/{municipalityId}")
public ResponseEntity<Double> getAverageAgeByMunicipality(@PathVariable Long municipalityId) {
    return ResponseEntity.ok(childService.getAverageAgeByMunicipality(municipalityId));
}


    @PostMapping("/{childId}/apply-vaccine/{vaccineId}")
    public ResponseEntity<ChildResp> applyVaccineToChild(@PathVariable Long childId, @PathVariable Long vaccineId) {
    return ResponseEntity.ok(childService.applyVaccineToChild(childId, vaccineId));
}

@GetMapping("/summary")
public ResponseEntity<List<MunicipalityChildrenSummaryResp>> getChildrenSummaryByMunicipality() {
    return ResponseEntity.ok(childService.getChildrenSummaryByMunicipality());
}


}