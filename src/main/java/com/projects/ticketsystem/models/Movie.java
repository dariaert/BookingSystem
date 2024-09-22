package com.projects.ticketsystem.models;

import jakarta.persistence.*;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilmmaker() {
        return filmmaker;
    }

    public void setFilmmaker(String filmmaker) {
        this.filmmaker = filmmaker;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPoster_film() {
        return poster_film;
    }

    public void setPoster_film(String poster_film) {
        this.poster_film = poster_film;
    }
}
