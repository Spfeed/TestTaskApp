package com.artem.task.dao;

import com.artem.task.model.Movie;
import com.artem.task.model.MovieCast;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieCastDao {

    private final JdbcTemplate jdbcTemplate;

    public MovieCastDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //CRUD
    //Поиск персонажа по id фильма и id актера
    public MovieCast findByMovieIdAndActorId(Long movieId, Long actorId) {
        String sql = "SELECT * FROM movie_cast WHERE movie_id = ?, actor_id = ?";
        return jdbcTemplate.queryForObject(sql, new MovieCastRowMapper(), movieId, actorId);
    }
    //Поиск всех персонажей из фильма по его id
    public List<MovieCast> findByMovieId(Long movieId) {
        String sql = "SELECT * FROM movie_cast WHERE movie_id = ?";
        return jdbcTemplate.query(sql, new MovieCastRowMapper(), movieId);
    }
    //Поиск всех персонажей из фильмов, которых играл актер с указанным id
    public List<MovieCast> findByActorId(Long actorId) {
        String sql = "SELECT * FROM movie_cast WHERE actor_id = ?";
        return jdbcTemplate.query(sql, new MovieCastRowMapper());
    }
    //Создание нового персонажа в фильме
    public void save(MovieCast movieCast) {
        String sql = "INSERT INTO movie_cast (movie_id, actor_id, character_name) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, movieCast.getMovieId(), movieCast.getActorId(), movieCast.getCharacterName());
    }
    //Изменение имени персонажа в определенном фильме с определенным актером
    public void update(MovieCast movieCast) {
        String sql = "UPDATE movie_cast SET character_name = ? WHERE movie_id = ? AND actor_id = ?";
        jdbcTemplate.update(sql, movieCast.getCharacterName(), movieCast.getMovieId(), movieCast.getActorId());
    }
    //Удаление персонажа по id фильма и актера
    public void delete(Long movieId, Long actorId) {
        String sql = "DELETE FROM movie_cast WHERE movie_id = ? AND actor_id = ?";
        jdbcTemplate.update(sql, movieId, actorId);
    }
}
