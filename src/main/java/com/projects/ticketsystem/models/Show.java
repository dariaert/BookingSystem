package com.projects.ticketsystem.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "show_table")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_movie_id")
    private Movie movie;

    private int cost;

    @Column(name = "show_date")
    private LocalDate date;

    @Column(name = "show_time")
    private LocalTime time;

    // Добавляем связь с Seat и каскадное удаление
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservation;

    public Show(Movie movie, int cost, LocalDate date, LocalTime time) {
        this.movie = movie;
        this.cost = cost;
        this.date = date;
        this.time = time;
    }

    public Show() {}
}
