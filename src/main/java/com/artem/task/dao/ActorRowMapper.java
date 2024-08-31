package com.artem.task.dao;

import com.artem.task.model.Actor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class ActorRowMapper implements RowMapper<Actor> {
    @Override
    public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Actor actor = new Actor();
        actor.setId(rs.getLong("id")) ;
        actor.setName(rs.getString("name"));
        actor.setLastName(rs.getString("last_name"));
        actor.setAge(rs.getInt("age"));
        return actor;
    }
}
