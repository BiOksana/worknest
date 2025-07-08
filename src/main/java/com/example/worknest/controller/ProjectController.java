package com.example.worknest.controller;

import com.example.worknest.dto.ProjectCreateDto;
import com.example.worknest.dto.ProjectDto;
import com.example.worknest.dto.VacancyDto;
import com.example.worknest.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@Tag(name = "Project Controller", description = "REST API for managing projects and vacancies in the app")
public class ProjectController {

    private ProjectService service;

    @Autowired
    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @Operation(summary = "Returns all app projects")
    @GetMapping
    public List<ProjectDto> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Returns all vacancies by the provided project id")
    @GetMapping("{id}/vacancies")
    public ResponseEntity<List<VacancyDto>> getVacanciesByProjectId(@PathVariable Long id) {
        return new ResponseEntity<>(service.getVacanciesByProjectId(id), HttpStatus.OK);
    }

    @Operation(summary = "Creates new project")
    @PostMapping
    public ResponseEntity<ProjectDto> create(@RequestBody @Valid ProjectCreateDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Creates new vacancy by the provided project id")
    @PostMapping("{id}/vacancies")
    public ResponseEntity<VacancyDto> addVacancyByProjectId(@PathVariable Long id, @RequestBody @Valid VacancyDto dto) {
        return new ResponseEntity<>(service.addVacancyByProjectId(id, dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Updates the project by the provided project id")
    @PutMapping("{id}")
    public ResponseEntity<ProjectDto> update(@PathVariable Long id, @RequestBody @Valid ProjectDto dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @Operation(summary = "Deletes the project by the provided project id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.accepted().build();
    }
}
