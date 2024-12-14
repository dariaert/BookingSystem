package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.Age;
import com.projects.ticketsystem.models.Genre;
import com.projects.ticketsystem.models.Movie;
import com.projects.ticketsystem.repositories.AgeRepository;
import com.projects.ticketsystem.repositories.GenreRepository;
import com.projects.ticketsystem.repositories.MovieRepository;
import com.projects.ticketsystem.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/edit/movie/{id}")
    public String movieDetails(@PathVariable(value = "id") long movieId, Model model) {
        Map<String, Object> movieDetails = movieService.getMovieDetails(movieId);
        model.addAttribute("movies", movieDetails.get("movies"));
        model.addAttribute("ages", movieDetails.get("ages"));
        model.addAttribute("genres", movieDetails.get("genres"));
        return "admin/actions/movie-redact";
    }

    @PostMapping("/edit/movie/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminMovieRedact(@PathVariable(value = "id") long movieId,
                                   @RequestParam("name_film") String nameFilm,
                                   @RequestParam("genreId") Long genreId, // Получаем id жанра
                                   @RequestParam("actors") String actors,
                                   @RequestParam("duration") int duration,
                                   @RequestParam("ageId") Long ageId, // Получаем id возрастного ограничения
                                   @RequestParam("filmmaker") String filmmaker,
                                   @RequestParam("country") String country,
                                   @RequestParam("poster") MultipartFile posterFile,
                                   @RequestParam("description") String description) throws IOException {
        movieService.updateMovie(movieId, nameFilm, genreId, actors, duration, ageId, filmmaker, country, posterFile, description);
        return "redirect:/admin";
    }

    @PostMapping("/movie/add")
    public String adminMoviesAdd(@RequestParam("name_film") String nameFilm,
                                 @RequestParam("genreId") Long genreId, // Получаем id жанра
                                 @RequestParam("actors") String actors,
                                 @RequestParam("duration") int duration,
                                 @RequestParam("ageId") Long ageId, // Получаем id возрастного ограничения
                                 @RequestParam("filmmaker") String filmmaker,
                                 @RequestParam("country") String country,
                                 @RequestParam("poster") MultipartFile posterFile,
                                 @RequestParam("description") String description,
                                 Model model) throws IOException {
        movieService.addMovie(nameFilm, genreId, actors, duration, ageId, filmmaker, country, posterFile, description);
        return "redirect:/admin";
    }

    @PostMapping("/remove/movie/{id}")
    public String adminMovieRemove(@PathVariable("id") long movieId) {
        movieService.removeMovie(movieId);
        return "redirect:/admin";
    }

}
