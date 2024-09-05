--Создание таблицы для хранения жанров фильмов, отдельная сущность для удобства
CREATE TABLE genres (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);
--Сами фильмы
CREATE TABLE movies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    release_year INT,
    description TEXT,
    genre_id INT REFERENCES genres(id),
    rating DECIMAL(2, 1)
);
--Актеры, так как один актер может исполнить множество ролей в разных фильмах
CREATE TABLE actors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
--Роли актеров в фильмах
CREATE TABLE movie_cast (
    movie_id INT REFERENCES movies(id),
    actor_id INT REFERENCES actors(id),
    character_name VARCHAR(255),
    PRIMARY KEY (movie_id, actor_id)
);
