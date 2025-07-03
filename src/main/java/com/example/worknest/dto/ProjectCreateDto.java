package com.example.worknest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectCreateDto {
    @NotBlank(message = "{validation.project.name}")
    @Pattern(regexp = "^[A-Z][a-zA-Z ]{0,99}", message = "validation.project.name")
    private String name;
    @NotBlank(message = "{validation.project.field}")
    private String field;
    @NotBlank(message = "{validation.project.experience}")
    private String experience;
    @NotNull(message = "{validation.project.deadline}")
    @Future(message = "{validation.project.deadline}")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    @Length(max = 1500, message ="{validation.project.description}")
    private String description;
}
