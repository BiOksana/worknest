package com.example.worknest.entity;

import com.example.worknest.entity.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    private String field;
    private String experience;
    private LocalDate deadline;
    @Column(columnDefinition = "text")
    private String description;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
}
