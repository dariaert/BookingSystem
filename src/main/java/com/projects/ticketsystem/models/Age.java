package com.projects.ticketsystem.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "age_table")
public class Age {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "age_id")
    private Long id;

    @Column(name = "age_name")
    private String age;

    @OneToMany(mappedBy = "age", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movie> movie;

}
