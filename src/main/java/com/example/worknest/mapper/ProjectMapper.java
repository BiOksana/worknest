package com.example.worknest.mapper;

import com.example.worknest.dto.ProjectCreateDto;
import com.example.worknest.dto.ProjectDto;
import com.example.worknest.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDto entityToDto(Project entity);
    Project dtoToEntity(ProjectDto dto);

    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "id", ignore = true)
    Project createDtoToEntity(ProjectCreateDto dto);

    List<ProjectDto> entityListToDtoList(List<Project> entities);
}
