package com.artem.task.dao;

import com.artem.task.exception.EntityAlreadyExistsException;
import com.artem.task.exception.EntityNotFoundException;
import com.artem.task.model.Movie;
import com.artem.task.model.MovieCast;
import org.springframework.dao.EmptyResultDataAccessException;
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
        try {
            return jdbcTemplate.queryForObject(sql, new MovieRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
         throw new EntityNotFoundException("Фильм", id);
        }
    }
    //Создание фильма
    public void save(Movie movie) {
        String sql = "INSERT INTO movies (title, release_year, description, genre_id, rating) VALUES (?, ?, ?, ?, ?)";
        String checkSql = "SELECT COUNT(*) FROM movies WHERE title = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, movie.getTitle());
        if (count > 0) {
            throw new EntityAlreadyExistsException("Фильм",movie.getTitle());
        }
        jdbcTemplate.update(sql, movie.getTitle(), movie.getReleaseYear(), movie.getDescription(), movie.getGenreId(),
                movie.getRating());
    }
    //Обновление информации о фильме
    public void update(Movie movie) {
        String sql = "UPDATE movies SET title = ?, release_year = ?, description = ?, genre_id = ?, rating = ? WHERE id = ?";
        int updated = jdbcTemplate.update(sql, movie.getTitle(), movie.getReleaseYear(), movie.getDescription(), movie.getGenreId(),
                movie.getRating(), movie.getId());
        if (updated == 0) {
            throw new EntityNotFoundException("Фильм", movie.getId());
        }
    }
    //Удаление фильма по его id
    public void delete(Long id) {
        String sql = "DELETE FROM movies WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        if (deleted == 0) {
            throw new EntityNotFoundException("Фильм", id);
        }
    }
    //Фильтрация фильмов по слову в названии
    public List<Movie> findByTitleContaining(String keyword)  {
        String sql = "SELECT * FROM movies WHERE title ILIKE ? ORDER BY id DESC";//ILIKE позволяет игнорировать регистр
        String query = "%" + keyword + "%";//Обеспечение поиска в любом месте строки
        return jdbcTemplate.query(sql, new MovieRowMapper(), query);
    }
    //Поиск фильма по названию
    public Movie findByTitle(String title) {
        String sql = "SELECT * FROM movies WHERE title = ?";
        return jdbcTemplate.queryForObject(sql, new MovieRowMapper(), title);
    }
}
