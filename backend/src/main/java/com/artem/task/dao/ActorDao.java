package com.artem.task.dao;

import com.artem.task.exception.EntityNotFoundException;
import com.artem.task.model.Actor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActorDao {

    private final JdbcTemplate jdbcTemplate;

    public ActorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //CRUD
    //Вывод всех актеров
    public List<Actor> findAll() {
        String sql = "SELECT * FROM actors";
        return jdbcTemplate.query(sql, new ActorRowMapper());
    }
    //Поиск актера по id
    public Actor findById(Long id) {
        try {
            String sql = "SELECT * FROM actors WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new ActorRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Актер", id);
        }
    }
    //Создание актера
    public void save(Actor actor) {
        String sql = "INSERT INTO actors (name, last_name, age) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, actor.getName(), actor.getLastName(), actor.getAge());
    }
    //Обновление актера
    public void update(Actor actor) {
        String sql = "UPDATE actors SET name = ?, last_name = ?, age = ? WHERE id = ?";
        int updated = jdbcTemplate.update(sql, actor.getName(), actor.getLastName(), actor.getAge(), actor.getId());
        if (updated == 0) {
            throw new EntityNotFoundException("Актер", actor.getId());
        }
    }
    //Удаление актера по id
    public void delete(Long id) {
        String sql = "DELETE FROM actors WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        if (deleted == 0) {
            throw new EntityNotFoundException("Актер", id);
        }
    }
    //Поиск по имени и фамилии
    public List<Actor> findByFullName(String firstName, String lastName) {
        String sql = "SELECT * FROM actors WHERE LOWER (name) = LOWER (?) AND LOWER (last_name) = LOWER (?)";
        return jdbcTemplate.query(sql, new ActorRowMapper(), firstName, lastName);
    }
    //Поиск по имени актера
    public List<Actor> findByFirstName(String firstName) {
        String sql = "SELECT * FROM actors WHERE LOWER (name) = LOWER (?)";
        return jdbcTemplate.query(sql, new ActorRowMapper(), firstName);
    }
    //Поиск по фамилии актера
    public List<Actor> findByLastName(String lastName) {
        String sql = "SELECT * FROM actors WHERE LOWER (last_name) = LOWER (?)";
        return jdbcTemplate.query(sql, new ActorRowMapper(), lastName);
    }
    //Поиск по имени, фамилии и возрасту
    public Actor findByNameAndLastNameAndAge(String name, String lastName, int age) {
        String sql = "SELECT * FROM actors WHERE LOWER (name) = LOWER (?) AND LOWER (last_name) = LOWER (?) AND age = ?";
        return jdbcTemplate.queryForObject(sql, new ActorRowMapper(), name, lastName, age);
    }
}
