package com.projects.ticketsystem.models;

import jakarta.persistence.*;

@Entity
@Table(name = "favourites")
public class Favourites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_favourites")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_favourites_film")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
