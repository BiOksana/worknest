package com.example.worknest.controller;

import com.example.worknest.controller.api.VacancyControllerApi;
import com.example.worknest.dto.VacancyDto;
import com.example.worknest.service.VacancyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vacancies")
public class VacancyController implements VacancyControllerApi {

    private VacancyService service;

    @Autowired
    public VacancyController(VacancyService service) {
        this.service = service;
    }

    @PutMapping("{id}")
    public ResponseEntity<VacancyDto> update(@PathVariable Long id, @RequestBody @Valid VacancyDto dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.accepted().build();
    }
}
