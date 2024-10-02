package com.oncredit.OnVacunas.api.controllers;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.oncredit.OnVacunas.api.dto.request.DepartmentReq;
import com.oncredit.OnVacunas.api.dto.response.DepartmentResp;
import com.oncredit.OnVacunas.infrastructure.abstract_Services.IDepartmentService;
import com.oncredit.OnVacunas.utils.enums.SortType;



@RestController
@RequestMapping("/departments")
@AllArgsConstructor
public class DepartmentController {

    private final IDepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResp> create(@RequestBody DepartmentReq request) {
        return ResponseEntity.ok(departmentService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResp> get(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResp> update(@RequestBody DepartmentReq request, @PathVariable Long id) {
        return ResponseEntity.ok(departmentService.update(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentResp>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NONE") SortType sortType) {
        return ResponseEntity.ok(departmentService.getAll(page, size, sortType));
    }
}