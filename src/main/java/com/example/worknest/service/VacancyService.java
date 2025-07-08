package com.example.worknest.service;

import com.example.worknest.dto.VacancyDto;
import com.example.worknest.entity.Vacancy;
import com.example.worknest.exception.WorkNestResourceNotFoundException;
import com.example.worknest.mapper.VacancyMapper;
import com.example.worknest.repository.ProjectRepository;
import com.example.worknest.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VacancyService {

    private VacancyRepository repository;
    private VacancyMapper mapper;
    private ProjectRepository projectRepository;

    @Autowired
    public VacancyService(VacancyRepository repository, VacancyMapper mapper, ProjectRepository projectRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public VacancyDto update(Long id, VacancyDto dto) {
        Vacancy vacancy = repository.findById(id).orElseThrow(() -> new WorkNestResourceNotFoundException("Vacancy with id = " + id + " not found in database"));
        vacancy.setName(dto.getName());
        vacancy.setField(dto.getField());
        vacancy.setExperience(dto.getExperience());
        vacancy.setCountry(dto.getCountry());
        vacancy.setDescription(dto.getDescription());
        vacancy.setProject(projectRepository.getReferenceById(dto.getProjectId()));
        return mapper.entityToDto(repository.save(vacancy));
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
