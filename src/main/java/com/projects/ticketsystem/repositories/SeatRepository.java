package com.projects.ticketsystem.repositories;

import com.projects.ticketsystem.models.Seat;
import org.springframework.data.repository.CrudRepository;

public interface SeatRepository extends CrudRepository<Seat, Long> {}
