package com.projects.ticketsystem.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_genre")
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Genre() {}

}
