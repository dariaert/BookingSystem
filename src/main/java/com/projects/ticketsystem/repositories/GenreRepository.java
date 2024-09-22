package com.projects.ticketsystem.repositories;

import com.projects.ticketsystem.models.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
