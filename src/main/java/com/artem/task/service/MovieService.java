package com.artem.task.service;

import com.artem.task.dao.ActorDao;
import com.artem.task.dao.GenreDao;
import com.artem.task.dao.MovieCastDao;
import com.artem.task.dao.MovieDao;
import com.artem.task.dto.MovieCreateDTO;
import com.artem.task.dto.MovieDTO;
import com.artem.task.dto.MovieDTOForSwagger;
import com.artem.task.dto.MovieUpdateDTO;
import com.artem.task.model.Movie;
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

}
