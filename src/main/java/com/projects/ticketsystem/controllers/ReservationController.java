package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.*;
import com.projects.ticketsystem.repositories.ReservationRepository;
import com.projects.ticketsystem.repositories.SeatRepository;
import com.projects.ticketsystem.repositories.ShowRepository;
import com.projects.ticketsystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;

    @GetMapping("/reservation")
    public String reservationPage(@RequestParam Long showId, Model model) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Сеанс не найден"));

        // Находим все занятые места для данного сеанса
        List<Seat> reservedSeats = seatRepository.findByShowAndIsReservedTrue(show);

        // Преобразуем занятые места в формат "ряд-место" (например, "3-5")
        List<String> reservedSeatsFormatted = reservedSeats.stream()
                .map(seat -> seat.getRowNumber() + "-" + seat.getSeatNumber())
                .toList();

        model.addAttribute("show", show);
        model.addAttribute("movie", show.getMovie());
        model.addAttribute("formattedDate", show.getDate().getDayOfMonth() + " " +
                show.getDate().getMonth().getDisplayName(TextStyle.FULL, new Locale("ru")));
        model.addAttribute("reservedSeats", reservedSeatsFormatted);

        return "reservation";
    }

    @PostMapping("/reservation")
    public String reservation(){
        return "redirect:/reservation";
    }
}
