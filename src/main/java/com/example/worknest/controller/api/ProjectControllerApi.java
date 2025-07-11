package com.example.worknest.controller.api;

import com.example.worknest.dto.ProjectCreateDto;
import com.example.worknest.dto.ProjectDto;
import com.example.worknest.dto.VacancyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Project Controller", description = "REST API for managing projects and vacancies in the app")
public interface ProjectControllerApi {
    @Operation(summary = "Returns all app projects")
    @ApiResponse(responseCode = "200", description = "Projects successfully retrieved")
    List<ProjectDto> getAll();

    @Operation(summary = "Returns all vacancies by the provided project id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacancies successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    ResponseEntity<List<VacancyDto>> getVacanciesByProjectId(
            @Parameter(description = "The ID of the project to retrieve associated vacancies", required = true)
            @PathVariable Long id);

    @Operation(summary = "Creates new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project successfully created"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    ResponseEntity<ProjectDto> create(@RequestBody @Valid ProjectCreateDto dto);

    @Operation(summary = "Creates new vacancy by the provided project id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vacancy successfully created"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    ResponseEntity<VacancyDto> addVacancyByProjectId(
            @Parameter(description = "The ID of the project to create associated vacancy", required = true)
            @PathVariable Long id,
            @RequestBody @Valid VacancyDto dto);

    @Operation(summary = "Updates the project by the provided project id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    ResponseEntity<ProjectDto> update(
            @Parameter(description = "The ID of the project to update", required = true)
            @PathVariable Long id,
            @RequestBody @Valid ProjectDto dto);

    @Operation(summary = "Deletes the project by the provided project id")
    @ApiResponse(responseCode = "200", description = "ACCEPTED")
    ResponseEntity<Void> delete(
            @Parameter(description = "The ID of the project to delete", required = true)
            @PathVariable Long id);
}
