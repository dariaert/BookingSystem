package com.projects.ticketsystem.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "film")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_film")
    private Long id;

    @Column(name = "name_film")
    private String name;
    private String description, actors, filmmaker, country, poster_film;

    @Column(name = "duration_film")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "id_genre")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "id_ageLimit")
    private Age age;

    public Movie(String name, String description, String actors, String filmmaker, String country, String poster_film, int duration, Genre genre, Age age) {
        this.name = name;
        this.description = description;
        this.actors = actors;
        this.filmmaker = filmmaker;
        this.country = country;
        this.poster_film = poster_film;
        this.duration = duration;
        this.genre = genre;
        this.age = age;
    }

    public Movie() {}
}
