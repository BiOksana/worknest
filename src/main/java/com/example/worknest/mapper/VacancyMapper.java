package com.example.worknest.mapper;

import com.example.worknest.dto.VacancyDto;
import com.example.worknest.entity.Vacancy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VacancyMapper {

    @Mapping(target = "projectId", source = "project.id")
    VacancyDto entityToDto(Vacancy vacancy);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    Vacancy dtoToEntityForCreating(VacancyDto dto);

    List<VacancyDto> entityListToDtoList(List<Vacancy> entities);
}
