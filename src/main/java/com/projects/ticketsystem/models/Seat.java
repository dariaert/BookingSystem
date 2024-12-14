package com.projects.ticketsystem.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "seat_table")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Column(name = "seat_number")
    private int seatNumber;

    @Column(name = "seat_row")
    private int rowNumber;

    @Column(name = "is_reserved", columnDefinition = "boolean default false")
    private boolean isReserved;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartReservation> partReservations;

}
