package com.projects.ticketsystem.repositories;

import com.projects.ticketsystem.models.Favourites;
import org.springframework.data.repository.CrudRepository;

public interface FavouritesRepository extends CrudRepository<Favourites, Long> {}
