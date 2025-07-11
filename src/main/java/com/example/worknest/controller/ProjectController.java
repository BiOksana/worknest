package com.example.worknest.controller;

import com.example.worknest.controller.api.ProjectControllerApi;
import com.example.worknest.dto.ProjectCreateDto;
import com.example.worknest.dto.ProjectDto;
import com.example.worknest.dto.VacancyDto;
import com.example.worknest.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController implements ProjectControllerApi {

    private ProjectService service;

    @Autowired
    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProjectDto> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}/vacancies")
    public ResponseEntity<List<VacancyDto>> getVacanciesByProjectId(@PathVariable Long id) {
        return new ResponseEntity<>(service.getVacanciesByProjectId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectDto> create(@RequestBody @Valid ProjectCreateDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PostMapping("{id}/vacancies")
    public ResponseEntity<VacancyDto> addVacancyByProjectId(@PathVariable Long id, @RequestBody @Valid VacancyDto dto) {
        return new ResponseEntity<>(service.addVacancyByProjectId(id, dto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProjectDto> update(@PathVariable Long id, @RequestBody @Valid ProjectDto dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.accepted().build();
    }
}
