package com.example.worknest.service;

import com.example.worknest.dto.VacancyDto;
import com.example.worknest.entity.Project;
import com.example.worknest.entity.Vacancy;
import com.example.worknest.entity.enums.Experience;
import com.example.worknest.exception.WorkNestResourceNotFoundException;
import com.example.worknest.mapper.VacancyMapper;
import com.example.worknest.repository.ProjectRepository;
import com.example.worknest.repository.VacancyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacancyServiceTest {

    @Mock
    private VacancyRepository repository;
    @Mock
    private VacancyMapper mapper;
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private VacancyService service;

    @Test
    void shouldUpdateVacancyWhenIdExists() {
        Long id = 123L;
        long projectId = 1L;

        VacancyDto inputDto = new VacancyDto();
        String newTestName = "New test name";
        inputDto.setName(newTestName);
        inputDto.setProjectId(projectId);
        inputDto.setExperience(Experience.MORE_ONE_YEAR);

        Vacancy existingVacancy = new Vacancy();
        existingVacancy.setId(id);

        Project project = new Project();
        project.setId(projectId);

        VacancyDto expectedDto = new VacancyDto();
        expectedDto.setProjectId(project.getId());
        expectedDto.setName(newTestName);
        expectedDto.setExperience(Experience.MORE_ONE_YEAR);

        when(repository.findById(id)).thenReturn(Optional.of(existingVacancy));
        when(projectRepository.getReferenceById(projectId)).thenReturn(project);
        when(repository.save(existingVacancy)).thenReturn(existingVacancy);
        when(mapper.entityToDto(existingVacancy)).thenReturn(expectedDto);

        VacancyDto resultDto = service.update(id, inputDto);

        assertEquals(newTestName, existingVacancy.getName());
        assertEquals(Experience.MORE_ONE_YEAR, existingVacancy.getExperience());

        assertEquals(newTestName, resultDto.getName());
        assertEquals(projectId, resultDto.getProjectId());
        assertEquals(Experience.MORE_ONE_YEAR, resultDto.getExperience());

        verify(repository).findById(id);
        verify(projectRepository).getReferenceById(projectId);
        verify(repository).save(existingVacancy);
        verify(mapper).entityToDto(existingVacancy);
    }

    @Test
    void shouldThrowExceptionWhenVacancyNotFound() {
        Long id = 123L;
        VacancyDto dto = new VacancyDto();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(WorkNestResourceNotFoundException.class, () -> service.update(id, dto));

        verify(projectRepository, never()).getReferenceById(any());
        verify(repository, never()).save(any());
        verify(mapper, never()).entityToDto(any());
    }

    @Test
    void delete() {
       Long id = 123L;
       service.delete(id);
       verify(repository).deleteById(id);
    }
}