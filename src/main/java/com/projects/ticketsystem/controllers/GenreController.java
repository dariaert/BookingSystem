package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.Genre;
import com.projects.ticketsystem.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/edit/genre/{id}")
    public String genreDetails(@PathVariable(value = "id") long genreId, Model model) {
        Genre genre = genreService.getGenreById(genreId);
        model.addAttribute("genre", genre);
        return "admin/actions/genre-redact";
    }

    @PostMapping("/genre/add")
    public String adminGenreAdd(@RequestParam("name_genre") String nameGenre) {
        genreService.addGenre(nameGenre);
        return "redirect:/admin";
    }

    @PostMapping("/edit/genre/{id}")
    public String adminGenreRedact(@PathVariable(value = "id") long genreId, @RequestParam("name_genre") String nameGenre, Model model) {
        genreService.updateGenre(genreId, nameGenre);
        return "redirect:/admin";
    }

    @PostMapping("/remove/genre/{id}")
    public String adminGenreRemove(@PathVariable(value = "id") long genreId, Model model) {
        genreService.removeGenre(genreId);
        return "redirect:/admin";
    }

}
