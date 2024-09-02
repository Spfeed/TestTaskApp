package com.artem.task.dao;

import com.artem.task.model.Movie;
import com.artem.task.model.MovieCast;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    //Поиск фильма по вхождению слова в название
    public List<Movie> findByTitleContaining(String keyword) {
        String sql = "SELECT * FROM movies WHERE title ILIKE ? ORDER BY id DESC";//ILIKE позволяет игнорировать регистр
        String query = "%" + keyword + "%"; //Поиск во всей строке
        return jdbcTemplate.query(sql, new MovieRowMapper(), query);
    }
    //Поиск фильмов по списку жанров
    public List<Movie> findByGenres(List<Long> genres) {
        String sql = "SELECT * FROM movies WHERE genre_id IN (:genreIds) ORDER BY id DESC";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();//Позволяет искать по списку
        parameterSource.addValue("genreIds", genres);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                jdbcTemplate.getDataSource()
        );//Позволяет использовать именованные параметры в запросе (заменяет genreIds на значения из списка)
        return namedParameterJdbcTemplate.query(sql, parameterSource, new MovieRowMapper());
    }
    //Поиск фильмов по списку актеров
    public List<Movie> findMoviesByActorsIds(List<Long> actorIds) {
        if (actorIds == null || actorIds.isEmpty()) {
            return new ArrayList<>();
        }
        //Создание строки плейсхолдеров (?, ?) в зависимости от количества элментов в списке
        String placeholders = actorIds.stream()
                .map(id -> "?").collect(Collectors.joining(", "));
        //SQL-запрос для поиска фильмов, в которых участвуют указанные актеры
        String sql = "SELECT m.* FROM movies m " +
                "JOIN movie_cast mc ON m.id = mc.movie_id " +
                "WHERE mc.actor_id IN (" + placeholders + ") " +
                "ORDER BY m.id DESC";
        return jdbcTemplate.query(sql, new MovieRowMapper(), actorIds.toArray());
    }
}
