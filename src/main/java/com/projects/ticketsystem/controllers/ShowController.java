package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.Movie;
import com.projects.ticketsystem.models.Show;
import com.projects.ticketsystem.repositories.MovieRepository;
import com.projects.ticketsystem.repositories.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ShowController {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;

    @GetMapping("/admin/shows")
    public String adminShows(Model model) {
        Iterable<Show> shows = showRepository.findAll();
        Iterable<Movie> movies = movieRepository.findAll();
        model.addAttribute("shows", shows);
        model.addAttribute("movies", movies);
        return "admin/shows";
    }

    @GetMapping("/redact/show/{id}")
    public String movieDetails(@PathVariable(value = "id") long showId, Model model) {
        Iterable<Movie> movies = movieRepository.findAll();
        Optional<Show> show = showRepository.findById(showId);
        ArrayList<Show> arrayList = new ArrayList<>();
        show.ifPresent(arrayList::add);
        model.addAttribute("shows", arrayList);
        model.addAttribute("movies", movies);
        return "admin/actions/show-redact";
    }

    @PostMapping("/show/add")
    public String adminShowAdd(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                 @RequestParam("movieId") Long movieId, // Получаем id фильма
                                 @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
                                 @RequestParam("cost") int cost,
                                 Model model) throws IOException {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + movieId));

        Show show = new Show(movie, cost, date, time);
        showRepository.save(show);

        return "redirect:/admin/shows";
    }

    @PostMapping("/remove/show/{id}")
    public String adminShowRemove(@PathVariable(value = "id") long showId, Model model) {
        Show show = showRepository.findById(showId).orElseThrow();
        showRepository.delete(show);
        return "redirect:/admin/shows";
    }

    @PostMapping("/redact/show/{id}")
    public String adminShowRedact(@PathVariable(value = "id") long showId,
                                   @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                   @RequestParam("movieId") Long movieId, // Получаем id фильма
                                   @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
                                   @RequestParam("cost") int cost,
                                   Model model) throws IOException {

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid show ID: " + showId));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + movieId));

        show.setDate(date);
        show.setTime(time);
        show.setCost(cost);
        show.setMovie(movie);
        showRepository.save(show);
        return "redirect:/admin/shows";
    }

}
