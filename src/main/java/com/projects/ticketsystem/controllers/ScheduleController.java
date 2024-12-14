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
            model.addAttribute("showsGroupedByMovie", scheduleService.getShowsGroupedByMovie(firstDate));
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
        model.addAttribute("showsGroupedByMovie", scheduleService.getShowsGroupedByMovie(date));
        return "components/fragments/showsFragment :: showsFragment";
    }

}
