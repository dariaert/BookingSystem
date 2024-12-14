package com.projects.ticketsystem.services;

import com.projects.ticketsystem.models.Age;
import com.projects.ticketsystem.models.Genre;
import com.projects.ticketsystem.models.Movie;
import com.projects.ticketsystem.repositories.AgeRepository;
import com.projects.ticketsystem.repositories.GenreRepository;
import com.projects.ticketsystem.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final AgeRepository ageRepository;
    private final ImageService imageService;

    public Iterable<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + movieId));
    }

    public Map<String, Object> getMovieDetails(long movieId) {
        Iterable<Age> ages = ageRepository.findAll();
        Iterable<Genre> genres = genreRepository.findAll();
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + movieId));
        return Map.of(
                "movies", movie,
                "ages", ages,
                "genres", genres
        );
    }

    public void updateMovie(long movieId, String nameFilm, Long genreId, String actors, int duration, Long ageId,
                            String filmmaker, String country, MultipartFile posterFile, String description) throws IOException {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + movieId));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
        Age age = ageRepository.findById(ageId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid age limit ID: " + ageId));
        String newPoster = posterFile.isEmpty() ? movie.getPoster_film() : imageService.savePoster(posterFile);
        if (!posterFile.isEmpty()) {
            imageService.deletePoster(movie.getPoster_film());
        }
        movie.setName(nameFilm);
        movie.setGenre(genre);
        movie.setAge(age);
        movie.setActors(actors);
        movie.setDuration(duration);
        movie.setFilmmaker(filmmaker);
        movie.setCountry(country);
        movie.setDescription(description);
        movie.setPoster_film(newPoster);
        movieRepository.save(movie);
    }

    public void addMovie(String nameFilm, Long genreId, String actors, int duration, Long ageId, String filmmaker,
                         String country, MultipartFile posterFile, String description) throws IOException {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
        Age age = ageRepository.findById(ageId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid age limit ID: " + ageId));
        String poster = posterFile.isEmpty() ? null : imageService.savePoster(posterFile);
        Movie movie = new Movie(nameFilm, description, actors, filmmaker, country, poster, duration, genre, age);
        movieRepository.save(movie);
    }

    public void removeMovie(long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + movieId));
        imageService.deletePoster(movie.getPoster_film());
        movieRepository.delete(movie);
    }


}
