package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.*;
import com.projects.ticketsystem.repositories.*;
import com.projects.ticketsystem.services.ReservationService;
import com.projects.ticketsystem.services.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ShowService showService;
    private final ReservationService reservationService;

    @GetMapping("/reservation")
    public String reservationPage(@RequestParam Long showId, Model model) {
        Show show = showService.getShowById(showId);
        Map<Integer, List<Seat>> seatsByRow = reservationService.getSeatsGroupedByRow(show);

        model.addAttribute("show", show);
        model.addAttribute("movie", show.getMovie());
        model.addAttribute("formattedDate", show.getDate().getDayOfMonth() + " " +
                show.getDate().getMonth().getDisplayName(TextStyle.FULL, new Locale("ru")));
        model.addAttribute("seatsByRow", seatsByRow);

        return "reservation";
    }

    @PostMapping("/reservation")
    @ResponseBody
    public String reservation(@RequestParam List<String> seatIds,
                              @RequestParam int costPerSeat,
                              @RequestParam Long showId,
                              Principal principal){
        User user = reservationService.getUserByPrincipal(principal);
        Show show = showService.getShowById(showId);
        List<Seat> selectedSeats = reservationService.getAvailableSeatsForReservation(seatIds, show);
        reservationService.createReservation(user, show, selectedSeats, costPerSeat);

        return "Бронирование успешно!";
    }
}
