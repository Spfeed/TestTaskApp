package com.artem.task.service;

import com.artem.task.dao.ActorDao;
import com.artem.task.dao.GenreDao;
import com.artem.task.dao.MovieCastDao;
import com.artem.task.dao.MovieDao;
import com.artem.task.dto.*;
import com.artem.task.exception.EntityAlreadyExistsException;
import com.artem.task.exception.EntityNotFoundException;
import com.artem.task.model.Actor;
import com.artem.task.model.Movie;
import com.artem.task.model.MovieCast;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieDao movieDao;
    private final GenreDao genreDao;
    private final ActorDao actorDao;
    private final MovieCastDao movieCastDao;

    public MovieService(MovieDao movieDao, GenreDao genreDao, ActorDao actorDao, MovieCastDao movieCastDao) {
        this.movieDao = movieDao;
        this.genreDao = genreDao;
        this.actorDao = actorDao;
        this.movieCastDao = movieCastDao;
    }

    //CRUD
    //Вывод всех фильмов с их жанрами (для отладки)
    public List<MovieDTOForSwagger> getAllMovies() {
        List<Movie> movies = movieDao.findAll();
        List<MovieDTOForSwagger> movieListDTO = new ArrayList<>();
        for (Movie movie : movies) {
            MovieDTOForSwagger movieDTOForSwagger = new MovieDTOForSwagger(
                    movie.getTitle(),
                    movie.getReleaseYear(),
                    movie.getDescription(),
                    genreDao.findById(movie.getGenreId()).getName(),
                    movie.getRating()
            );
            movieListDTO.add(movieDTOForSwagger);
        }
        return movieListDTO;
    }
    //Вывод фильма по id (только для отладки)
    public MovieDTOForSwagger getMovieById(Long id) {
        try {
            Movie movie = movieDao.findById(id);
            return new MovieDTOForSwagger(
                    movie.getTitle(),
                    movie.getReleaseYear(),
                    movie.getDescription(),
                    genreDao.findById(movie.getGenreId()).getName(),
                    movie.getRating()
            );
        } catch (EntityNotFoundException e) {
            throw e; //Перебрасываем исключение
        }
    }
    //Создание фильма (уже не отладка)
    public void saveMovie(MovieCreateDTO movieCreateDTO) {
        try {
            Movie movie = new Movie(
                    movieCreateDTO.getTitle(),
                    movieCreateDTO.getReleaseYear(),
                    movieCreateDTO.getDescription(),
                    movieCreateDTO.getGenreId(),
                    movieCreateDTO.getRating()
            );
            movieDao.save(movie);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        }
    }
    //Редактирование фильма (уже не отладка)
    public void updateMovie(MovieUpdateDTO movieUpdateDTO) {
        try {
            Movie movie = new Movie();
            movie.setId(movieUpdateDTO.getId());
            movie.setTitle(movieUpdateDTO.getTitle());
            movie.setReleaseYear(movieUpdateDTO.getReleaseYear());
            movie.setDescription(movieUpdateDTO.getDescription());
            movie.setGenreId(movieUpdateDTO.getGenreId());
            movie.setRating(movieUpdateDTO.getRating());
            movieDao.update(movie);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }
    //Удаление фильма (пока отладка)
    public void deleteMovie(Long id) {
        try {
            movieDao.delete(id);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    //Методы из задания
    //Вывод всех фильмов с полным описанием, акерами и ролями
    public List<MovieDTO> getAllMoviesDetails() {
        List<Movie> movies = movieDao.findAll();
        List<MovieDTO> movieDTOS = new ArrayList<>();

        for (Movie movie : movies) {
            movieDTOS.add(convertToMovieDTO(movie));
        }
        return movieDTOS;
    }
    //Фильтрация по слову в названии
    //Два варианта: реализация при помощи JavaStream и создание нового метода в movieDao, представлены оба:
    //Вариант с JavaStream:
    public List<MovieDTO> filterMoviesByTitleJavaStream(String keyword) {
        List<MovieDTO> allMovies = getAllMoviesDetails();
        return allMovies.stream().
                filter(movieDTO -> movieDTO.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
    //Вариант с использованием movieDao:
    public List<MovieDTO> filterMoviesByTitleMovieDao(String keyword) {
        List<Movie> movies = movieDao.findByTitleContaining(keyword);
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (Movie movie : movies) {
            movieDTOS.add(convertToMovieDTO(movie));
        }
        return movieDTOS;
    }
    //Фильтрация фильмов по жанрам
    public List<MovieDTO> getMoviesFilteredByGenres(List<String> genres) {
        List<MovieDTO> filteredMovies = new ArrayList<>();
        List<Movie> movies = movieDao.findAll();
        for(Movie movie : movies) {
            String movieGenre = genreDao.findById(movie.getGenreId()).getName();
            if (genres.contains(movieGenre)) {
                filteredMovies.add(convertToMovieDTO(movie));
            }
        }
        return filteredMovies;
    }
    //Фильрация по фамилии актера
    //Пользователь может указать имя и фамилию, имя или фмаилию, по которым будет осщуествлена фильтрация
    public List<MovieDTO> getMoviesFilteredByActor(String actorName) {
        String[] nameParts = actorName.trim().split(" ", 2);
        String firstName = null;
        String lastname = null;

        if (nameParts.length == 2) {
            firstName = nameParts[0];
            lastname = nameParts[1];
        } else if (nameParts.length == 1) {//Предполагается, что пользователь ввел фамилию
            lastname = nameParts[0];
        }
        //TODO выброс исключения

        List<Actor> actors = new ArrayList<>();

        if (firstName != null && lastname != null) {
            actors = actorDao.findByFullName(firstName, lastname);
        }
        else {
            actors = actorDao.findByLastName(lastname);
            if (actors.isEmpty()){
                actors = actorDao.findByFirstName(lastname);
            }
        }
        if (actors.isEmpty()){
            return new ArrayList<>();
        }

        List<MovieDTO> filteredMovies = new ArrayList<>();

        List<Movie> movies = movieDao.findAll();
        for (Movie movie : movies) {
            boolean hasMatchingActor = actors.stream()
                    .anyMatch(actor -> movieCastDao.findByMovieIdAndActorId(movie.getId(), actor.getId()) != null);
            if (hasMatchingActor) {
                filteredMovies.add(convertToMovieDTO(movie));
            }
        }
        return filteredMovies;
    }
    //Поиск id фильма по названию
    public Long getMovieIdByTitle(String title) {
        Movie movie = movieDao.findByTitle(title);
        return movie.getId();
    }

    //Преобразование данных для отправки на внешний API
    private MovieDTO convertToMovieDTO(Movie movie) {
        //Получение названия жанра фильма
        String genre = genreDao.findById(movie.getGenreId()).getName();

        //Получение списка актеров фильма и их ролей
        List<MovieCast> movieCasts = movieCastDao.findByMovieId(movie.getId());
        List<ActorForMovieDTO> actors = new ArrayList<>();
        for (MovieCast movieCast : movieCasts) {
            Actor actor = actorDao.findById(movieCast.getActorId());
            ActorForMovieDTO actorForMovieDTO = new ActorForMovieDTO(
                    actor.getName(),
                    actor.getLastName(),
                    movieCast.getCharacterName()
            );
            actors.add(actorForMovieDTO);
        }
        return new MovieDTO(
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getDescription(),
                genre,
                actors,
                movie.getRating()
        );
    }
}
