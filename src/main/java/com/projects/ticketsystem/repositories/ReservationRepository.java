package com.projects.ticketsystem.repositories;

import com.projects.ticketsystem.models.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {}
