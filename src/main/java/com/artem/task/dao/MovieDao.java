package com.artem.task.dao;

import com.artem.task.model.Movie;
import com.artem.task.model.MovieCast;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDao {

    private final JdbcTemplate jdbcTemplate;

    public MovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //CRUD
    //Вывод всех фильмов от последнего к первому
    public List<Movie> findAll() {
        String sql = "SELECT * FROM movies ORDER BY id DESC"; //вывод в обратном порядке
        return jdbcTemplate.query(sql, new MovieRowMapper());
    }
    //Поиск фильма по id
    public Movie findById(Long id) {
        String sql = "SELECT * FROM movies WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new MovieRowMapper(), id);
    }
    //Создание фильма
    public void save(Movie movie) {
        String sql = "INSERT INTO movies (title, release_year, description, genre_id, rating) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, movie.getTitle(), movie.getReleaseYear(), movie.getDescription(), movie.getGenreId(),
                movie.getRating());
    }
    //Обновление информации о фильме
    public void update(Movie movie) {
        String sql = "UPDATE movies SET title = ?, release_year = ?, description = ?, genre_id = ?, rating = ? WHERE id = ?";
        jdbcTemplate.update(sql, movie.getTitle(), movie.getReleaseYear(), movie.getDescription(), movie.getGenreId(),
                movie.getRating(), movie.getId());
    }
    //Удаление фильма по его id
    public void delete(Long id) {
        String sql = "DELETE FROM movies WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
