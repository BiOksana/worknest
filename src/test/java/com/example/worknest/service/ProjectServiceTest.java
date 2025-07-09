package com.example.worknest.service;

import com.example.worknest.dto.ProjectCreateDto;
import com.example.worknest.dto.ProjectDto;
import com.example.worknest.dto.VacancyDto;
import com.example.worknest.entity.Project;
import com.example.worknest.entity.Vacancy;
import com.example.worknest.exception.WorkNestResourceNotFoundException;
import com.example.worknest.mapper.ProjectMapper;
import com.example.worknest.mapper.VacancyMapper;
import com.example.worknest.repository.ProjectRepository;
import com.example.worknest.repository.VacancyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository repository;
    @Mock
    private ProjectMapper mapper;
    @Mock
    private VacancyMapper vacancyMapper;
    @Mock
    private VacancyRepository vacancyRepository;
    @InjectMocks
    private ProjectService service;

    @Test
    void getAll() {
        service.getAll();

        verify(repository, Mockito.times(1)).findAll();
        verify(mapper).entityListToDtoList(any());
    }

    @Test
    void shouldReturnVacanciesByProjectIdWhenIdExists() {
        Long id = 1L;
        Project project = new Project();
        project.setId(id);

        Vacancy vacancy1 = new Vacancy();
        Vacancy vacancy2 = new Vacancy();
        List<Vacancy> vacancies = List.of(vacancy1, vacancy2);
        project.setVacancies(vacancies);

        List<VacancyDto> vacancyDtoList = List.of(new VacancyDto(), new VacancyDto());

        when(repository.findById(id)).thenReturn(Optional.of(project));
        when(vacancyMapper.entityListToDtoList(vacancies)).thenReturn(vacancyDtoList);

        List<VacancyDto> result = service.getVacanciesByProjectId(id);

        verify(repository).findById(id);
        verify(vacancyMapper).entityListToDtoList(vacancies);

        assertEquals(2, result.size());
        assertSame(vacancyDtoList, result);
    }

    @Test
    void shouldThrowExceptionWhenProjectIdNotExistMethodGetVacanciesByProjectId() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(WorkNestResourceNotFoundException.class, () -> service.getVacanciesByProjectId(id));

        verify(repository).findById(id);
        verify(vacancyMapper, never()).entityListToDtoList(any());
    }

    @Test
    void shouldCreateAndReturnProjectDto() {
        ProjectCreateDto dto = new ProjectCreateDto();
        dto.setName("New test name");

        Project project = new Project();
        project.setId(123L);

        ProjectDto resultDto = new ProjectDto();
        resultDto.setId(123L);
        resultDto.setName("New test name");

        when(mapper.createDtoToEntity(dto)).thenReturn(project);
        when(repository.save(project)).thenReturn(project);
        when(mapper.entityToDto(project)).thenReturn(resultDto);

        resultDto = service.create(dto);

        assertEquals(123L, resultDto.getId());
        assertEquals("New test name", resultDto.getName());

        verify(mapper).createDtoToEntity(dto);
        verify(repository).save(project);
        verify(mapper).entityToDto(project);
    }

    @Test
    void shouldAddVacancyByProjectIdWhenIdExists() {
        Long id = 1L;
        VacancyDto inputDto = new VacancyDto();
        String newVacancyName = "New vacancy name";
        inputDto.setName(newVacancyName);

        Vacancy vacancy = new Vacancy();
        vacancy.setName(newVacancyName);

        Project project = new Project();
        project.setId(id);

        VacancyDto expectedDto = new VacancyDto();
        expectedDto.setName(newVacancyName);
        expectedDto.setProjectId(id);

        when(repository.existsById(id)).thenReturn(true);
        when(vacancyMapper.dtoToEntityForCreating(inputDto)).thenReturn(vacancy);
        when(repository.getReferenceById(id)).thenReturn(project);
        when(vacancyRepository.save(vacancy)).thenReturn(vacancy);
        when(vacancyMapper.entityToDto(vacancy)).thenReturn(expectedDto);

        VacancyDto resultDto = service.addVacancyByProjectId(id, inputDto);

        assertEquals(expectedDto.getProjectId(), resultDto.getProjectId());
        assertEquals(expectedDto.getName(), resultDto.getName());

        verify(repository).existsById(id);
        verify(vacancyMapper).dtoToEntityForCreating(inputDto);
        verify(repository).getReferenceById(id);
        verify(vacancyMapper).entityToDto(vacancy);
        verify(vacancyRepository).save(vacancy);
    }

    @Test
    void shouldThrowExceptionWhenProjectNotFoundMethodAddVacancyByProjectId() {
        Long id = 123L;
        VacancyDto dto = new VacancyDto();

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(WorkNestResourceNotFoundException.class, () -> service.addVacancyByProjectId(id, dto));
        verify(repository).existsById(id);
        verify(vacancyMapper, never()).dtoToEntityForCreating(any());
        verify(repository, never()).getReferenceById(any());
        verify(vacancyMapper, never()).entityToDto(any());
        verify(vacancyRepository, never()).save(any());
    }

    @Test
    void shouldUpdateProjectWhenIdExists() {
        Long id = 1L;
        Project project = new Project();
        project.setId(id);

        ProjectDto dto = new ProjectDto();
        dto.setName("New project");

        when(repository.findById(id)).thenReturn(Optional.of(project));
        when(repository.save(project)).thenReturn(project);
        when(mapper.entityToDto(project)).thenReturn(dto);

        ProjectDto updated = service.update(id, dto);

        assertEquals("New project", updated.getName());
        verify(repository).save(project);
        verify(mapper).entityToDto(project);
    }

    @Test
    void shouldThrowExceptionWhenProjectNotFound() {
        Long id = 1L;
        ProjectDto dto = new ProjectDto();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(WorkNestResourceNotFoundException.class, () -> service.update(id, dto));

        verify(repository).findById(id);
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