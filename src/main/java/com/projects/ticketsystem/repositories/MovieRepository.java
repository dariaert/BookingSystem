package com.projects.ticketsystem.repositories;

import com.projects.ticketsystem.models.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {}