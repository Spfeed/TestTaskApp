package com.artem.task.dao;

import com.artem.task.model.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie = new Movie();
        movie.setId(rs.getLong("id"));
        movie.setTitle(rs.getString("title"));
        movie.setReleaseYear(rs.getInt("release_year"));
        movie.setDescription(rs.getString("description"));
        movie.setGenreId(rs.getLong("genre_id"));
        movie.setRating(rs.getBigDecimal("rating"));
        return movie;
    }
}
