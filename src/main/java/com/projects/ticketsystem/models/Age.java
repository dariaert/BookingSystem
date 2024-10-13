package com.projects.ticketsystem.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "age_limit")
public class Age {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_ageLimit")
    private String age;

}
