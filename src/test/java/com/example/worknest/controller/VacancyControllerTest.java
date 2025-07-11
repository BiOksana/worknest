package com.example.worknest.controller;

import com.example.worknest.dto.VacancyDto;
import com.example.worknest.entity.enums.Experience;
import com.example.worknest.exception.WorkNestResourceNotFoundException;
import com.example.worknest.service.VacancyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VacancyController.class)
class VacancyControllerTest {

    @MockitoBean
    private VacancyService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void updateVacancyWithCorrectData() throws Exception {
        Long id = 1L;
        VacancyDto inputDto = new VacancyDto(1L, "New Test Name", "Design",
                Experience.MORE_THREE_YEARS, "Ukraine", "Description", 1L);

        VacancyDto expectedDto = new VacancyDto(1L, "New Test Name", "Design",
                Experience.MORE_THREE_YEARS, "Ukraine", "Description", 1L);

        when(service.update(id, inputDto)).thenReturn(expectedDto);

        mockMvc.perform(put("/vacancies/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(inputDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedDto)));

        verify(service).update(id, inputDto);
    }

    @Test
    void updateVacancyWithMismatchedIdInBody() throws Exception {
        Long id = 1L;
        VacancyDto inputDto = new VacancyDto(2L, "New Test Name", "Design",
                Experience.MORE_THREE_YEARS, "Ukraine", "Description", 1L);

        when(service.update(id, inputDto)).thenReturn(inputDto);

        mockMvc.perform(put("/vacancies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk());

        verify(service).update(id, inputDto);
    }

    @Test
    void updateVacancyWithEmptyBody() throws Exception {
        Long id = 1L;
        mockMvc.perform(put("/vacancies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verify(service, never()).update(any(), any());
    }

    @Test
    void updateVacancyWithoutName() throws Exception {
        Long id = 1L;
        VacancyDto dto = new VacancyDto(1L, null, "Design",
                Experience.MORE_THREE_YEARS, "Ukraine", "Description", 1L);

        mockMvc.perform(put("/vacancies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name should start with a capital letter and contain 2-4 words (letters, hyphens, spaces) and should not exceed 45 characters."));

        verify(service, never()).update(any(), any());
    }

    @Test
    void updateVacancyWithIncorrectName() throws Exception {
        VacancyDto inputDto = new VacancyDto(1L, "New test name", "Design",
                Experience.MORE_THREE_YEARS, "Ukraine", "Description", 1L);

        mockMvc.perform(put("/vacancies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(inputDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name should start with a capital letter and contain 2-4 words (letters, hyphens, spaces) and should not exceed 45 characters."));

        verify(service, never()).update(any(), any());
    }

    @Test
    void updateVacancyNotFound() throws Exception {
        Long id = 99L;
        VacancyDto inputDto = new VacancyDto(99L, "New Test Name", "Design",
                Experience.MORE_THREE_YEARS, "Ukraine", "Description", 1L);
        when(service.update(eq(id), any())).thenThrow(new WorkNestResourceNotFoundException("Vacancy with id = " + id + " not found in database"));

        mockMvc.perform(put("/vacancies/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(inputDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Vacancy with id = " + id + " not found in database"));

        verify(service).update(any(), eq(inputDto));
    }

    @Test
    void updateVacancyWhenProjectNotFound() throws Exception {
        Long id = 1L;
        VacancyDto inputDto = new VacancyDto(1L, "New Test Name", "Design",
                Experience.MORE_THREE_YEARS, "Ukraine", "Description", 999L);
        when(service.update(eq(id), any())).thenThrow(new EntityNotFoundException("Project not found in database"));

        mockMvc.perform(put("/vacancies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(inputDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Project not found in database"));

        verify(service).update(any(), eq(inputDto));
    }

    @Test
    void deleteVacancyById() throws Exception {
        Long id = 1L;
        doNothing().when(service).delete(id);
        mockMvc.perform(delete("/vacancies/{id}", id))
                .andExpect(status().isAccepted());
        verify(service).delete(id);
    }
}