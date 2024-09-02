package com.artem.task.service;

import com.artem.task.dao.ActorDao;
import com.artem.task.dao.GenreDao;
import com.artem.task.dao.MovieCastDao;
import com.artem.task.dao.MovieDao;
import com.artem.task.dto.*;
import com.artem.task.model.Actor;
import com.artem.task.model.Genre;
import com.artem.task.model.Movie;
import com.artem.task.model.MovieCast;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Movie movie = movieDao.findById(id);
        return new MovieDTOForSwagger(
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getDescription(),
                genreDao.findById(movie.getGenreId()).getName(),
                movie.getRating()
        );
    }
    //Создание фильма (уже не отладка)
    public void saveMovie(MovieCreateDTO movieCreateDTO) {
        Movie movie = new Movie(
          movieCreateDTO.getTitle(),
          movieCreateDTO.getReleaseYear(),
          movieCreateDTO.getDescription(),
          movieCreateDTO.getGenreId(),
          movieCreateDTO.getRating()
        );
        movieDao.save(movie);
    }
    //Редактирование фильма (уже не отладка)
    public void updateMovie(MovieUpdateDTO movieUpdateDTO) {
        Movie movie = new Movie();
        movie.setId(movieUpdateDTO.getId());
        movie.setTitle(movieUpdateDTO.getTitle());
        movie.setReleaseYear(movieUpdateDTO.getReleaseYear());
        movie.setDescription(movieUpdateDTO.getDescription());
        movie.setGenreId(movieUpdateDTO.getGenreId());
        movie.setRating(movieUpdateDTO.getRating());
        movieDao.update(movie);
    }
    //Удаление фильма (пока отладка)
    public void deleteMovie(Long id) {
        movieDao.delete(id);
    }

    //Методы из задания
    //Вывод всех фильмов с названием жанра и списком актеров с их ролями
    public List<MovieDTO> getAllMoviesDetails() {
        List<Movie> movies = movieDao.findAll();
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (Movie movie : movies) {
            movieDTOS.add(convertToMovieDTO(movie));
        }
        return movieDTOS;
    }
    //Фильтрация по вхождению слова в название
    public List<MovieDTO> getMoviesFilteredByKeyword(String keyword) {
        List<Movie> movies = movieDao.findByTitleContaining(keyword);
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (Movie movie : movies) {
            movieDTOS.add(convertToMovieDTO(movie));
        }
        return movieDTOS;
    }
    //Фильтрация по жанрам
    public List<MovieDTO> getMoviesFilteredByGenres(List<String> genres) {
        List<Long> genreIds = new ArrayList<>();
        for (String genre : genres) {
            Genre genreForId = genreDao.findByName(genre);
            if (genreForId != null) {
                genreIds.add(genreForId.getId());
            }
        }
        if (genreIds.isEmpty()){
            return new ArrayList<>();
        }
        List<Movie> movies = movieDao.findByGenres(genreIds);
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (Movie movie : movies) {
            movieDTOS.add(convertToMovieDTO(movie));
        }
        return movieDTOS;
    }
    // Фильтрация по фамилии актера (имени и фамиилии, имени)
    public List<MovieDTO> getMoviesFilteredByActor(String actorName) {
        String[] nameParts = actorName.trim().split(" ", 2);
        String firstName = null;
        String lastName = null;

        if (nameParts.length == 2 ){
            firstName = nameParts[0];
            lastName = nameParts[1];
        } else if (nameParts.length == 1){
            lastName = nameParts[0];
        }

        List<Actor> actors = new ArrayList<>();
        if (firstName != null && lastName != null){
            actors = actorDao.findByFullName(firstName, lastName);
        }
        else {
            actors = actorDao.findByLastName(lastName);
            if (actors.isEmpty()) {
                actors = actorDao.findByFirstName(lastName);
            }
        }
        if (actors.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> actorIds = new ArrayList<>();
        for (Actor actor : actors) {
            actorIds.add(actor.getId());
        }
        List<Movie> movies = movieDao.findMoviesByActorsIds(actorIds);
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (Movie movie : movies) {
            movieDTOS.add(convertToMovieDTO(movie));
        }
        return movieDTOS;
    }

    //Преобразование данных для отправки на внешний API
    private MovieDTO convertToMovieDTO(Movie movie) {
        String genre = genreDao.findById(movie.getGenreId()).getName();

        List<MovieCast> characters = movieCastDao.findByMovieId(movie.getId());
        List<ActorForMovieDTO> actors = new ArrayList<>();
        for (MovieCast character : characters) {
            Actor actor = actorDao.findById(character.getActorId());
            ActorForMovieDTO actorForMovieDTO = new ActorForMovieDTO(
                    actor.getName(),
                    actor.getLastName(),
                    character.getCharacterName()
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
