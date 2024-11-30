package com.projects.ticketsystem.repositories;

import com.projects.ticketsystem.models.Seat;
import com.projects.ticketsystem.models.Show;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeatRepository extends CrudRepository<Seat, Long> {
    List<Seat> findByShowAndIsReservedTrue(Show show);
}
