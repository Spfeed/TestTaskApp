-- Добавление столбца фамилии для поиска фильмов по фамилии
ALTER TABLE actors ADD COLUMN last_name VARCHAR(255) NOT NULL;

-- Добавление столбца возраста для того, чтобы различать одноименных актеров
ALTER TABLE actors ADD COLUMN age INT;
