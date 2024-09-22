package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.Movie;
import com.projects.ticketsystem.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Movie> movies = movieRepository.findAll();
        //model.addAttribute("title", "Welcome to the Ticket System");
        model.addAttribute("movies", movies);
        return "home";
    }

}
