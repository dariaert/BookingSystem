package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.Movie;
import com.projects.ticketsystem.models.Seat;
import com.projects.ticketsystem.models.Show;
import com.projects.ticketsystem.repositories.MovieRepository;
import com.projects.ticketsystem.repositories.SeatRepository;
import com.projects.ticketsystem.repositories.ShowRepository;
import com.projects.ticketsystem.services.ShowService;
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
    private final SeatRepository seatRepository;
    private final ShowService showService;

    @GetMapping("/edit/show/{id}")
    public String showDetails(@PathVariable(value = "id") long showId, Model model) {
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
        int rows = 9;
        int seatNumber = 20;
        // Для каждого ряда и места создаем записи в таблице seats
        for (int row = 1; row <= rows; row++) {
            for (int seat = 1; seat <= seatNumber; seat++) {
                Seat seatEntity = new Seat();
                seatEntity.setRowNumber(row);
                seatEntity.setSeatNumber(seat);
                seatEntity.setReserved(false);
                seatEntity.setShow(show);
                seatRepository.save(seatEntity);
            }
        }
        return "redirect:/admin";
    }

    @PostMapping("/remove/show/{id}")
    public String adminShowRemove(@PathVariable(value = "id") long showId, Model model) {
        showService.removeShow(showId);
        return "redirect:/admin";
    }

    @PostMapping("/edit/show/{id}")
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
        return "redirect:/admin";
    }

}
