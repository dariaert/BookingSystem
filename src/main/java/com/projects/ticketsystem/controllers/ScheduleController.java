package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.Movie;
import com.projects.ticketsystem.models.Show;
import com.projects.ticketsystem.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/schedule")
    public String schedule(Model model) {
        List<LocalDate> availableDates = scheduleService.getAvailableDates();
        model.addAttribute("availableDates", availableDates);

        if (!availableDates.isEmpty()) {
            LocalDate firstDate = availableDates.get(0);
            model.addAttribute("selectedDate", firstDate);

            // Группируем сеансы по фильмам
            Map<Movie, List<Show>> showsGroupedByMovie = scheduleService.getShowsByDate(firstDate).stream()
                    .collect(Collectors.groupingBy(Show::getMovie));
            model.addAttribute("showsGroupedByMovie", showsGroupedByMovie);
        } else {
            model.addAttribute("selectedDate", null);
            model.addAttribute("showsGroupedByMovie", Map.of());
        }

        return "schedule";
    }

    @GetMapping("/shows/filter")
    public String getShowsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {
        Map<Movie, List<Show>> showsGroupedByMovie = scheduleService.getShowsByDate(date).stream()
                .collect(Collectors.groupingBy(Show::getMovie,
                        Collectors.collectingAndThen(Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(Show::getTime))
                                        .collect(Collectors.toList()))));
        model.addAttribute("showsGroupedByMovie", showsGroupedByMovie);
        return "components/showsFragment :: showsFragment";
    }

}
