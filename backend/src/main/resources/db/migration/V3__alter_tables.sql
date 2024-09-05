-- Добавление каскадного удаления в таблицу movie_cast при удалении фильма
ALTER TABLE movie_cast
DROP CONSTRAINT movie_cast_movie_id_fkey;

ALTER TABLE movie_cast
ADD CONSTRAINT movie_cast_movie_id_fkey
FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE;

-- Изменение поведения удаления жанра
ALTER TABLE movies
DROP CONSTRAINT movies_genre_id_fkey;

ALTER TABLE movies
ADD CONSTRAINT movies_genre_id_fkey
FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE SET NULL;
