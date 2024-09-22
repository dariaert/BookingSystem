package com.projects.ticketsystem.repositories;

import com.projects.ticketsystem.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {}
