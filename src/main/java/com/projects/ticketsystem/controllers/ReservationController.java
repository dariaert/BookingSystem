package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.*;
import com.projects.ticketsystem.repositories.*;
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

    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;
    private final PartReservationRepository partReservationRepository;

    @GetMapping("/reservation")
    public String reservationPage(@RequestParam Long showId, Model model) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Сеанс не найден"));

        List<Seat> seats = seatRepository.findByShow(show);

        // Группируем места по рядам
        Map<Integer, List<Seat>> seatsByRow = seats.stream()
                .collect(Collectors.groupingBy(
                        Seat::getRowNumber,
                        TreeMap::new,
                        Collectors.toList()
                ));

        // Сортируем места внутри каждого ряда по номеру места
        seatsByRow.forEach((rowNumber, seatList) ->
                seatList.sort(Comparator.comparing(Seat::getSeatNumber))
        );

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

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Находим сеанс
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Сеанс не найден"));

        // Ищем места по данным "ряд-место"
        List<Seat> selectedSeats = new ArrayList<>();
        for (String seatId : seatIds) {
            String[] seatParts = seatId.split("-"); // Разделяем строку по "-"
            int rowNumber = Integer.parseInt(seatParts[0]);
            int seatNumber = Integer.parseInt(seatParts[1]);

            // Ищем место с этим номером ряда и места для указанного сеанса
            Seat seat = seatRepository.findByShowAndRowNumberAndSeatNumber(show, rowNumber, seatNumber)
                    .orElseThrow(() -> new RuntimeException("Место " + seatId + " не найдено для данного сеанса"));

            // Проверяем, что место еще не занято
            if (seat.isReserved()) {
                throw new RuntimeException("Место " + seatId + " уже забронировано");
            }

            selectedSeats.add(seat);
        }

        // Создаем заказ0
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShow(show);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setTotalCost(seatIds.size() * costPerSeat);

        reservationRepository.save(reservation);

        // Создаем записи для связей Reservation и Seat
        for (Seat seat : selectedSeats) {
            PartReservation partReservation = new PartReservation();
            partReservation.setReservation(reservation);
            partReservation.setSeat(seat);
            partReservationRepository.save(partReservation);

            // Помечаем место как забронированное
            seat.setReserved(true);
            seatRepository.save(seat);
        }

        return "Бронирование успешно!";
    }
}
