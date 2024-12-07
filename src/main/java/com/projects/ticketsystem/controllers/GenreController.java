package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.Genre;
import com.projects.ticketsystem.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping("/redact/genre/{id}")
    public String genreDetails(@PathVariable(value = "id") long genreId, Model model) {
        Optional<Genre> genre = genreRepository.findById(genreId);
        ArrayList<Genre> arrayList = new ArrayList<>();
        genre.ifPresent(arrayList::add);
        model.addAttribute("genres", arrayList);
        return "admin/actions/genre-redact";
    }

    @PostMapping("/genre/add")
    public String adminGenreAdd(@RequestParam("name_genre") String nameGenre, Model model) {
        Genre genre = new Genre(nameGenre);
        genreRepository.save(genre);
        return "redirect:/admin";
    }

    @PostMapping("/redact/genre/{id}")
    public String adminGenreRedact(@PathVariable(value = "id") long genreId, @RequestParam("name_genre") String nameGenre, Model model) {
        Genre genre = genreRepository.findById(genreId).orElseThrow();
        genre.setGenreName(nameGenre);
        genreRepository.save(genre);
        return "redirect:/admin";
    }

    @PostMapping("/remove/genre/{id}")
    public String adminGenreRemove(@PathVariable(value = "id") long genreId, Model model) {
        Genre genre = genreRepository.findById(genreId).orElseThrow();
        genreRepository.delete(genre);
        return "redirect:/admin";
    }

}
