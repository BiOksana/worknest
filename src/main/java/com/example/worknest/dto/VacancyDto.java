package com.example.worknest.dto;

import com.example.worknest.entity.enums.Experience;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class VacancyDto {

    private Long id;
    @NotBlank(message = "{validation.vacancy.name}")
    @Size(max = 45, message = "{validation.vacancy.name}")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*(?:[- ][A-Z][a-zA-Z]*){1,3}$", message = "{validation.vacancy.name}")
    private String name;
    @NotBlank(message = "{validation.vacancy.field}")
    private String field;

    private Experience experience;

    @NotBlank(message = "{validation.vacancy.country}")
    private String country;

    @Length(max = 1500, message ="{validation.vacancy.description}")
    private String description;

    private Long projectId;
}
