package com.example.worknest.controller;

import com.example.worknest.dto.VacancyDto;
import com.example.worknest.service.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vacancies")
@Tag(name = "Vacancy Controller", description = "REST API for managing (changing and deleting) vacancies in the app")
public class VacancyController {

    private VacancyService service;

    @Autowired
    public VacancyController(VacancyService service) {
        this.service = service;
    }

    @Operation(summary = "Updates the vacancy by the provided vacancy id")
    @PutMapping("{id}")
    public ResponseEntity<VacancyDto> update(@PathVariable Long id, @RequestBody @Valid VacancyDto dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @Operation(summary = "Deletes the vacancy by the provided vacancy id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.accepted().build();
    }
}
