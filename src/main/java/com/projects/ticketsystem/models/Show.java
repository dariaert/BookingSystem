package com.projects.ticketsystem.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "show_film")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_film")
    private Movie movie;

    private int cost;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    public Show(Movie movie, int cost, LocalDate date, LocalTime time) {
        this.movie = movie;
        this.cost = cost;
        this.date = date;
        this.time = time;
    }

    public Show() {}
}
