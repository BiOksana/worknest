package com.example.worknest.controller;

import com.example.worknest.dto.ProjectCreateDto;
import com.example.worknest.dto.ProjectDto;
import com.example.worknest.dto.VacancyDto;
import com.example.worknest.entity.enums.Experience;
import com.example.worknest.entity.enums.ProjectStatus;
import com.example.worknest.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @MockitoBean
    private ProjectService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service).getAll();
    }

    @Test
    void getVacanciesByProjectId() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/projects/1/vacancies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service).getVacanciesByProjectId(id);
    }

    @Test
    void create() throws Exception {
        ProjectCreateDto inputDto = new ProjectCreateDto("New test name", "Design",
                Experience.MORE_THREE_YEARS, LocalDate.of(2025, 12, 5),
                "Something");
        ProjectDto dto = new ProjectDto(1L, "New test name", "Design",
                Experience.MORE_THREE_YEARS, LocalDate.of(2025, 12, 5),
                "Something", ProjectStatus.ACTIVE);

        when(service.create(eq(inputDto))).thenReturn(dto);

        MvcResult mvcResult = mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andReturn();
        verify(service).create(eq(inputDto));

        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(dto), contentAsString);
    }

    @Test
    void addVacancyByProjectId() throws Exception {

        VacancyDto inputDto = new VacancyDto(1L, "New Test Name", "Design",
                Experience.MORE_THREE_YEARS, "Ukraine", "Description", null);

        VacancyDto expectedDto = new VacancyDto(1L, "New Test Name", "Design",
                Experience.MORE_THREE_YEARS, "Ukraine", "Description", 1L);

        when(service.addVacancyByProjectId(1L, inputDto)).thenReturn(expectedDto);

        mockMvc.perform(post("/projects/1/vacancies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedDto)));

        verify(service).addVacancyByProjectId(1L, inputDto);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}