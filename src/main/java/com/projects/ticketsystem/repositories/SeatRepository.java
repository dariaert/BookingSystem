package com.projects.ticketsystem.repositories;

import com.projects.ticketsystem.models.Seat;
import com.projects.ticketsystem.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByShow(Show show);
    Optional<Seat> findByShowAndRowNumberAndSeatNumber(Show show, int rowNumber, int seatNumber);
}
