package com.example.worknest.service;

import com.example.worknest.dto.ProjectCreateDto;
import com.example.worknest.dto.ProjectDto;
import com.example.worknest.exception.WorkNestResourceNotFoundException;
import com.example.worknest.mapper.ProjectMapper;
import com.example.worknest.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    ProjectRepository repository;
    ProjectMapper mapper;

    @Autowired
    public ProjectService(ProjectRepository repository, ProjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ProjectDto> getAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    @Transactional
    public ProjectDto create(ProjectCreateDto dto) {
        return mapper.entityToDto(repository.save(mapper.createDtoToEntity(dto)));
    }

    @Transactional
    public ProjectDto update(Long id, ProjectDto dto) {
        if (!repository.existsById(id)) {
            throw new WorkNestResourceNotFoundException("Project with id = " + id + " not found in database");
        }

        return mapper.entityToDto(repository.save(mapper.dtoToEntity(dto)));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new WorkNestResourceNotFoundException("Project with id = " + id + " not found in database");
        }
        repository.deleteById(id);
    }
}
