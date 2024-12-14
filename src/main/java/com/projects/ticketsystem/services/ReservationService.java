package com.projects.ticketsystem.services;

import com.projects.ticketsystem.models.*;
import com.projects.ticketsystem.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final PartReservationRepository partReservationRepository;

    public Map<Integer, List<Seat>> getSeatsGroupedByRow(Show show) {
        List<Seat> seats = seatRepository.findByShow(show);

        // Группируем места по рядам и сортируем
        return seats.stream()
                .collect(Collectors.groupingBy(
                        Seat::getRowNumber,
                        TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(Seat::getSeatNumber))
                                        .collect(Collectors.toList())
                        )
                ));
    }

    public User getUserByPrincipal(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    // Ищем места по данным "ряд-место"
    public List<Seat> getAvailableSeatsForReservation(List<String> seatIds, Show show) {
        List<Seat> selectedSeats = new ArrayList<>();
        for (String seatId : seatIds) {
            String[] seatParts = seatId.split("-");
            int rowNumber = Integer.parseInt(seatParts[0]);
            int seatNumber = Integer.parseInt(seatParts[1]);
            Seat seat = seatRepository.findByShowAndRowNumberAndSeatNumber(show, rowNumber, seatNumber)
                    .orElseThrow(() -> new RuntimeException("Место " + seatId + " не найдено для данного сеанса"));
            if (seat.isReserved()) { throw new RuntimeException("Место " + seatId + " уже забронировано"); }
            selectedSeats.add(seat);
        }
        return selectedSeats;
    }

    public void createReservation(User user, Show show, List<Seat> selectedSeats, int costPerSeat) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShow(show);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setTotalCost(selectedSeats.size() * costPerSeat);

        reservationRepository.save(reservation);

        for (Seat seat : selectedSeats) {
            PartReservation partReservation = new PartReservation();
            partReservation.setReservation(reservation);
            partReservation.setSeat(seat);
            partReservationRepository.save(partReservation);

            seat.setReserved(true);
            seatRepository.save(seat);
        }
    }

}
