package com.projects.ticketsystem.controllers;

import com.projects.ticketsystem.models.Age;
import com.projects.ticketsystem.models.Genre;
import com.projects.ticketsystem.models.Movie;
import com.projects.ticketsystem.repositories.AgeRepository;
import com.projects.ticketsystem.repositories.GenreRepository;
import com.projects.ticketsystem.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class MovieController {

    @Value("${upload.path}")
    private String uploadPath;

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final AgeRepository ageRepository;

    @GetMapping("/redact/movie/{id}")
    public String movieDetails(@PathVariable(value = "id") long movieId, Model model) {
        Iterable<Age> ages = ageRepository.findAll();
        Iterable<Genre> genres = genreRepository.findAll();
        Optional<Movie> movie = movieRepository.findById(movieId);
        ArrayList<Movie> arrayList = new ArrayList<>();
        movie.ifPresent(arrayList::add);
        model.addAttribute("movies", arrayList);
        model.addAttribute("ages", ages);
        model.addAttribute("genres", genres);
        return "admin/actions/movie-redact";
    }

    @PostMapping("/redact/movie/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminMovieRedact(@PathVariable(value = "id") long movieId,
                                   @RequestParam("name_film") String nameFilm,
                                   @RequestParam("genreId") Long genreId, // Получаем id жанра
                                   @RequestParam("actors") String actors,
                                   @RequestParam("duration") int duration,
                                   @RequestParam("ageId") Long ageId, // Получаем id возрастного ограничения
                                   @RequestParam("filmmaker") String filmmaker,
                                   @RequestParam("country") String country,
                                   @RequestParam("poster") MultipartFile posterFile,
                                   @RequestParam("description") String description,
                                   Model model) throws IOException {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + movieId));
        // Найти жанр и возрастное ограничение по id
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
        Age age = ageRepository.findById(ageId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid age limit ID: " + ageId));
        // Если загружен новый постер
        if (!posterFile.isEmpty()) {
            String oldPosterFilename = movie.getPoster_film(); // Получаем имя старого постера
            if (oldPosterFilename != null && !oldPosterFilename.isEmpty()) {
                File oldPoster = new File(uploadPath + "/" + oldPosterFilename);
                if (oldPoster.exists()) {
                    boolean deleted = oldPoster.delete();
                    if (!deleted) {
                        model.addAttribute("error", "Не удалось удалить старое изображение.");
                    }
                }
            }
            // Генерация нового имени файла постера
            String extension = posterFile.getOriginalFilename().substring(posterFile.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
            if (extension.equals("png") || extension.equals("jpg")) {
                String randomNumbers = String.valueOf(new Random().nextInt(10000));
                String resultFilename = "poster_" + randomNumbers + "." + extension;
                // Создание директории, если не существует
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                // Сохранение нового файла
                posterFile.transferTo(new File(uploadPath + "/" + resultFilename));
                // Обновление ссылки на постер
                movie.setPoster_film(resultFilename);
            } else {
                model.addAttribute("error", "Неверный формат файла. Допустимы только PNG и JPG.");
                return "redirect:/admin/actions/movie-redact";
            }
        }
        movie.setName(nameFilm);
        movie.setActors(actors);
        movie.setDuration(duration);
        movie.setFilmmaker(filmmaker);
        movie.setCountry(country);
        movie.setDescription(description);
        movie.setGenre(genre);
        movie.setAge(age);
        movieRepository.save(movie);
        return "redirect:/admin/movies";
    }

    @GetMapping("/admin/movies")
    public String adminMovies(Model model) {
        Iterable<Movie> movies = movieRepository.findAll();
        Iterable<Genre> genres = genreRepository.findAll();
        Iterable<Age> ages = ageRepository.findAll();
        model.addAttribute("movies", movies);
        model.addAttribute("genres", genres);
        model.addAttribute("ages", ages);
        return "admin/movies";
    }

    @PostMapping("/movie/add")
    public String adminMoviesAdd(@RequestParam("name_film") String nameFilm,
                                 @RequestParam("genreId") Long genreId, // Получаем id жанра
                                 @RequestParam("actors") String actors,
                                 @RequestParam("duration") int duration,
                                 @RequestParam("ageId") Long ageId, // Получаем id возрастного ограничения
                                 @RequestParam("filmmaker") String filmmaker,
                                 @RequestParam("country") String country,
                                 @RequestParam("poster") MultipartFile posterFile,
                                 @RequestParam("description") String description,
                                 Model model) throws IOException {

        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));

        Age age = ageRepository.findById(ageId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid age limit ID: " + ageId));

        // Генерация имени файла постера
        String resultFilename = null;
        if (!posterFile.isEmpty()) {
            String extension = posterFile.getOriginalFilename().substring(posterFile.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
            if (extension.equals("png") || extension.equals("jpg")) {
                String randomNumbers = String.valueOf(new Random().nextInt(10000));
                resultFilename = "poster_" + randomNumbers + "." + extension;

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                posterFile.transferTo(new File(uploadPath + "/" + resultFilename));
            } else {
                model.addAttribute("error", "Неверный формат файла. Допустимы только PNG и JPG.");
                return "add-film-form";
            }
        }


        Movie movie = new Movie(nameFilm, description, actors, filmmaker, country, resultFilename, duration, genre, age);
        movieRepository.save(movie);

//        Movie movie = new  Movie();
//        movie.setName(nameFilm);
//        movie.setGenre(genre);
//        movie.setAge(age);
//        movie.setFilmmaker(filmmaker);
//        movie.setCountry(country);
//        movie.setDescription(description);
//
//        movieRepository.save(movie);

        return "redirect:/admin/movies";
    }

    @PostMapping("/remove/movie/{id}")
    public String adminMovieRemove(@PathVariable(value = "id") long movieId, Model model) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        String oldPosterFilename = movie.getPoster_film();
        if (oldPosterFilename != null && !oldPosterFilename.isEmpty()) {
            File oldPoster = new File(uploadPath + "/" + oldPosterFilename);
            if (oldPoster.exists()) {
                boolean deleted = oldPoster.delete();
                if (!deleted) {
                    model.addAttribute("error", "Не удалось удалить старое изображение.");
                }
            }
        }
        movieRepository.delete(movie);
        return "redirect:/admin/movies";
    }
}
