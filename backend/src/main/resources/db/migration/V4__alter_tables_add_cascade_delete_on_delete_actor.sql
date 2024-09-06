-- Удаление текущего ограничение внешнего ключа на поле actor_id в таблице movie_cast
ALTER TABLE movie_cast
DROP CONSTRAINT movie_cast_actor_id_fkey;

-- Добавление нового ограничение внешнего ключа с каскадным удалением
ALTER TABLE movie_cast
ADD CONSTRAINT movie_cast_actor_id_fkey
FOREIGN KEY (actor_id) REFERENCES actors(id) ON DELETE CASCADE;
