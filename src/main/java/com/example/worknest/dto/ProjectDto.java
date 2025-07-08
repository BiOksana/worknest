package com.example.worknest.dto;

import com.example.worknest.entity.enums.Experience;
import com.example.worknest.entity.enums.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProjectDto {

    private Long id;
    @NotBlank(message = "{validation.project.name}")
    @Pattern(regexp = "^[A-Z][a-zA-Z ]{0,99}", message = "{validation.project.name}")
    private String name;
    @NotBlank(message = "{validation.project.field}")
    private String field;

    private Experience experience;

    @NotNull(message = "{validation.project.deadline}")
    @Future(message = "{validation.project.deadline}")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    @Length(max = 1500, message = "{validation.project.description}")
    private String description;

    private ProjectStatus status;
}