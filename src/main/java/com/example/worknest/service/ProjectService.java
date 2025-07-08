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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    ProjectRepository repository;
    ProjectMapper mapper;
    VacancyMapper vacancyMapper;
    VacancyRepository vacancyRepository;

    @Autowired
    public ProjectService(ProjectRepository repository, ProjectMapper mapper, VacancyMapper vacancyMapper, VacancyRepository vacancyRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.vacancyMapper = vacancyMapper;
        this.vacancyRepository = vacancyRepository;
    }

    public List<ProjectDto> getAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    public List<VacancyDto> getVacanciesByProjectId(Long id) {
        Project project = repository.findById(id).orElseThrow(() -> new WorkNestResourceNotFoundException("Project with id = " + id + " not found in database"));
        List<Vacancy> vacancies = project.getVacancies();
        return vacancyMapper.entityListToDtoList(vacancies);
    }

    @Transactional
    public ProjectDto create(ProjectCreateDto dto) {
        return mapper.entityToDto(repository.save(mapper.createDtoToEntity(dto)));
    }

    @Transactional
    public VacancyDto addVacancyByProjectId(Long id, VacancyDto dto) {
        if (!repository.existsById(id)) {
            throw new WorkNestResourceNotFoundException("Project with id = " + id + " not found in database");
        }
        Vacancy entity = vacancyMapper.dtoToEntityForCreating(dto);
        entity.setProject(repository.getReferenceById(id));
        return vacancyMapper.entityToDto(vacancyRepository.save(entity));
    }

    @Transactional
    public ProjectDto update(Long id, ProjectDto dto) {
        Project project = repository.findById(id).orElseThrow(() -> new WorkNestResourceNotFoundException("Project with id = " + id + " not found in database"));
        project.setName(dto.getName());
        project.setField(dto.getField());
        project.setExperience(dto.getExperience());
        project.setDeadline(dto.getDeadline());
        project.setDescription(dto.getDescription());
        project.setStatus(dto.getStatus());
        return mapper.entityToDto(repository.save(project));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
