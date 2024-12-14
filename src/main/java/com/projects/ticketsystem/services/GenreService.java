package com.projects.ticketsystem.services;

import com.projects.ticketsystem.models.Genre;
import com.projects.ticketsystem.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public Iterable<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
    }

    public void addGenre(String nameGenre) {
        Genre genre = new Genre(nameGenre);
        genreRepository.save(genre);
    }

    public void updateGenre(long genreId, String nameGenre) {
        Genre genre = getGenreById(genreId);
        genre.setGenreName(nameGenre);
        genreRepository.save(genre);
    }

    public void removeGenre(long genreId ){
        Genre genre = getGenreById(genreId);
        genreRepository.delete(genre);
    }

}
