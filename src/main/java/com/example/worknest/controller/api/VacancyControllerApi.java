package com.example.worknest.controller.api;

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

@Tag(name = "Vacancy Controller", description = "REST API for managing (changing and deleting) vacancies in the app")
public interface VacancyControllerApi {

    @Operation(summary = "Updates the vacancy by the provided vacancy id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    ResponseEntity<VacancyDto> update(
            @Parameter(description = "The ID of the vacancy to update", required = true)
            @PathVariable Long id,
            @RequestBody @Valid VacancyDto dto);

    @Operation(summary = "Deletes the vacancy by the provided vacancy id")
    @ApiResponse(responseCode = "200", description = "ACCEPTED")
    ResponseEntity<Void> delete(
            @Parameter(description = "The ID of the vacancy to delete", required = true)
            @PathVariable Long id);
}
