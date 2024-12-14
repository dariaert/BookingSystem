package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.Movie;
import com.projects.ticketsystem.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MovieService movieService;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "home";
    }

    @GetMapping("/movie/{id}")
    public String movieDetails(@PathVariable(value = "id") long movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);
        model.addAttribute("movie", movie);
        return "item";
    }

}
