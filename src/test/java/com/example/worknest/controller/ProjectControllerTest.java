package com.example.worknest.controller;

import com.example.worknest.dto.ProjectCreateDto;
import com.example.worknest.dto.ProjectDto;
import com.example.worknest.dto.VacancyDto;
import com.example.worknest.entity.enums.Experience;
import com.example.worknest.entity.enums.ProjectStatus;
import com.example.worknest.exception.WorkNestResourceNotFoundException;
import com.example.worknest.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @MockitoBean
    private ProjectService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service).getAll();
    }

    @Test
    void getVacanciesByProjectId() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/projects/1/vacancies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service).getVacanciesByProjectId(id);
    }

    @Test
    void createProjectWithCorrectData() throws Exception {
        ProjectCreateDto inputDto = new ProjectCreateDto("New test name", "Design",
                Experience.MORE_THREE_YEARS, LocalDate.of(2025, 12, 5),
                "Something");
        ProjectDto dto = new ProjectDto(1L, "New test name", "Design",
                Experience.MORE_THREE_YEARS, LocalDate.of(2025, 12, 5),
                "Something", ProjectStatus.ACTIVE);

        when(service.create(eq(inputDto))).thenReturn(dto);

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(inputDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(dto)));

        verify(service).create(eq(inputDto));
    }

    @Test
    void createProjectWithIncorrectName() throws Exception {
        ProjectCreateDto inputDto = new ProjectCreateDto("New/test name", "Design",
                Experience.MORE_THREE_YEARS, LocalDate.of(2025, 12, 5),
                "Something");

        mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(inputDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name should start with a capital letter and contain only letters and spaces and should not exceed 100 characters."));

        verify(service, never()).create(any());
    }

    @Test
    void createProjectWithIncorrectDate() throws Exception {
        ProjectCreateDto inputDto = new ProjectCreateDto("New test name", "Design",
                Experience.MORE_THREE_YEARS, LocalDate.of(2023, 12, 5),
                "Something");

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(inputDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.deadline").value("Deadline should not be empty and should be in the future."));

        verify(service, never()).create(any());
    }

    @Test
    void addVacancyByProjectId() throws Exception {
        Long id = 1L;
        VacancyDto inputDto = new VacancyDto(1L, "New Test Name", "Design",
                Experience.MORE_THREE_YEARS, "Ukraine", "Description", null);

        VacancyDto expectedDto = new VacancyDto(1L, "New Test Name", "Design",
                Experience.MORE_THREE_YEARS, "Ukraine", "Description", 1L);

        when(service.addVacancyByProjectId(1L, inputDto)).thenReturn(expectedDto);

        mockMvc.perform(post("/projects/{id}/vacancies", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(inputDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(expectedDto)));

        verify(service).addVacancyByProjectId(eq(1L), eq(inputDto));
    }

    @Test
    void updateProjectWithCorrectData() throws Exception {
        Long id = 1L;
        ProjectDto inputDto = new ProjectDto(1L, "New test name", "Design",
                Experience.MORE_THREE_YEARS, LocalDate.of(2025, 12, 5),
                "Something", ProjectStatus.ACTIVE);

        ProjectDto expectedDto = new ProjectDto(1L, "New test name", "Design",
                Experience.MORE_THREE_YEARS, LocalDate.of(2025, 12, 5),
                "Something", ProjectStatus.ACTIVE);

        when(service.update(id, inputDto)).thenReturn(expectedDto);

        mockMvc.perform(put("/projects/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(inputDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedDto)));

        verify(service).update(eq(id), eq(inputDto)); //??????
    }

    @Test
    void updateProjectNotFound() throws Exception {
        Long id = 99L;
        ProjectDto inputDto = new ProjectDto(1L, "New test name", "Design",
                Experience.MORE_THREE_YEARS, LocalDate.of(2025, 12, 5),
                "Something", ProjectStatus.ACTIVE);

        when(service.update(eq(id), any())).thenThrow(new WorkNestResourceNotFoundException("Project with id = \" + id + \" not found in database"));

        mockMvc.perform(put("/projects/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(inputDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Project with id = \" + id + \" not found in database"));

        verify(service).update(eq(id), eq(inputDto));
    }

    @Test
    void deleteProjectById() throws Exception {
        Long id = 1L;
        doNothing().when(service).delete(id);
        mockMvc.perform(delete("/projects/{id}", id))
                .andExpect(status().isAccepted());
        verify(service).delete(id);
    }
}