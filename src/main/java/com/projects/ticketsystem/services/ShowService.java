package com.projects.ticketsystem.services;

import com.projects.ticketsystem.models.Show;
import com.projects.ticketsystem.repositories.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;

    public Iterable<Show> getAllShows(){
        return showRepository.findAll();
    }

    public Show getShowById(long showId){
        return showRepository.findById(showId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid show ID: " + showId));
    }

    public void addShow(){

    }

    public void updateShow(){

    }

    public void removeShow(long showId){
        Show show = getShowById(showId);
        showRepository.delete(show);
    }

}
