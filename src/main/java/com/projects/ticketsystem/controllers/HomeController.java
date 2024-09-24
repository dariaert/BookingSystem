package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.Movie;
import com.projects.ticketsystem.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        return "home";
    }

    @GetMapping("/movie/{id}")
    public String movieDetails(@PathVariable(value = "id") long movieId, Model model) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        ArrayList<Movie> arrayList = new ArrayList<>();
        movie.ifPresent(arrayList::add);
        model.addAttribute("movie", arrayList);
        return "item";
    }



}
