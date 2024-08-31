package com.artem.task.dao;

import com.artem.task.model.MovieCast;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class MovieCastRowMapper implements RowMapper<MovieCast> {
    @Override
    public MovieCast mapRow(ResultSet rs, int rowNum) throws SQLException {
        MovieCast movieCast = new MovieCast();
        movieCast.setMovieId(rs.getLong("movie_id"));
        movieCast.setActorId(rs.getLong("actor_id"));
        movieCast.setCharacterName(rs.getString("character_name"));
        return movieCast;
    }
}
