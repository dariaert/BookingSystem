package com.projects.ticketsystem.repositories;

import com.projects.ticketsystem.models.Show;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends CrudRepository<Show, Long> {
}
