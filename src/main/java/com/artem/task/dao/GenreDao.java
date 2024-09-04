package com.artem.task.dao;

import com.artem.task.exception.EntityNotFoundException;
import com.artem.task.exception.GenreAlreadyExistsException;
import com.artem.task.exception.GenreNotFoundException;
import com.artem.task.model.Genre;
import org.springframework.dao.EmptyResultDataAccessException;
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
        try{
            return jdbcTemplate.queryForObject(sql, new GenreRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Жанр" ,id);
        }
    }
    //Добавление жанра
    public void save(Genre genre) {
        String sql = "INSERT INTO genres (name) VALUES (?)";
        jdbcTemplate.update(sql, genre.getName());
    }
    //Обновление жанра
    public void update(Genre genre) {
        String sql = "UPDATE genres SET name = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, genre.getName(), genre.getId());
        if (rowsAffected == 0) {
            throw new EntityNotFoundException("Жанр" ,genre.getId());
        }
    }
    //Удаление жанра по id
    public void delete(Long id){
        String sql = "DELETE FROM genres WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new EntityNotFoundException("Жанр" ,id);
        }
    }
    //Поиск жанра по имени
    public Genre findByName(String name) {
        String sql = "SELECT * FROM genres WHERE name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new GenreRowMapper(), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
