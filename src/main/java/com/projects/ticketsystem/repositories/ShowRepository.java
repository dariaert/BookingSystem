package com.projects.ticketsystem.repositories;

import com.projects.ticketsystem.models.Movie;
import com.projects.ticketsystem.models.Show;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends CrudRepository<Show, Long> {

    List<Show> findAllByDate(LocalDate date);

    @Query("SELECT DISTINCT s.date FROM Show s ORDER BY s.date ASC")
    List<LocalDate> findAllDistinctDates();

}
