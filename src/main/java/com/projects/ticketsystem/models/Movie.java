package com.projects.ticketsystem.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "movie_table")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column(name = "movie_name")
    private String name;

    @Column(name = "movie_description", length = 1024)
    private String description;

    @Column(name = "movie_actors")
    private String actors;

    @Column(name = "movie_filmmaker")
    private String filmmaker;

    @Column(name = "movie_country")
    private String country;

    @Column(name = "movie_poster")
    private String poster_film;

    @Column(name = "movie_duration")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "movie_genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "movie_age_id")
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

}
