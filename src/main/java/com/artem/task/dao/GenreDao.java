package com.artem.task.dao;

import com.artem.task.model.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreDao {

    private final JdbcTemplate jdbcTemplate;

    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //CRUD
    //Список всех жанров
    public List<Genre> findAll() {
        String sql = "SELECT * FROM genres";
        return jdbcTemplate.query(sql, new GenreRowMapper());
    }
    //Вывод жанра по id
    public Genre findById(Long id) {
        String sql = "SELECT * FROM genres WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new GenreRowMapper(), id);
    }
    //Добавление жанра
    public void save(Genre genre) {
        String sql = "INSERT INTO genres (name) VALUES (?)";
        jdbcTemplate.update(sql, genre.getName());
    }
    //Обновление жанра
    public void update(Genre genre) {
        String sql = "UPDATE genres SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, genre.getName(), genre.getId());
    }
    //Удаление жанра по id
    public void delete(Long id){
        String sql = "DELETE FROM genres WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
