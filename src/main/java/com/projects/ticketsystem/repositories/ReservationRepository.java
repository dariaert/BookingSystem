package com.projects.ticketsystem.repositories;

import com.projects.ticketsystem.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {}
