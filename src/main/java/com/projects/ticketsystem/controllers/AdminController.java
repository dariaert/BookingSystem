package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.Age;
import com.projects.ticketsystem.models.Genre;
import com.projects.ticketsystem.models.Movie;
import com.projects.ticketsystem.models.Show;
import com.projects.ticketsystem.repositories.AgeRepository;
import com.projects.ticketsystem.repositories.GenreRepository;
import com.projects.ticketsystem.repositories.MovieRepository;
import com.projects.ticketsystem.repositories.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final AgeRepository ageRepository;
    private final ShowRepository showRepository;

    @GetMapping("/admin")
    public String admin(Model model) {
        Iterable<Movie> movies = movieRepository.findAll();
        Iterable<Genre> genres = genreRepository.findAll();
        Iterable<Age> ages = ageRepository.findAll();
        Iterable<Show> shows = showRepository.findAll();
        model.addAttribute("shows", shows);
        model.addAttribute("movies", movies);
        model.addAttribute("genres", genres);
        model.addAttribute("ages", ages);
        return "admin";
    }

}
