package com.projects.ticketsystem.services;

import com.projects.ticketsystem.models.Movie;
import com.projects.ticketsystem.models.Show;
import com.projects.ticketsystem.repositories.MovieRepository;
import com.projects.ticketsystem.repositories.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ShowRepository showRepository;

    public List<Show> getShowsByDate(LocalDate date) {
        return showRepository.findAllByDate(date);
    }

    public List<LocalDate> getAvailableDates() {
        return showRepository.findAllDistinctDates();
    }

    public Map<Movie, List<Show>> getShowsGroupedByMovie(LocalDate date) {
        return getShowsByDate(date).stream()
                .collect(Collectors.groupingBy(Show::getMovie,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(Show::getTime))
                                        .collect(Collectors.toList()))));
    }

}
