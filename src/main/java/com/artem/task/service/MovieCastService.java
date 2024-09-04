    package com.artem.task.service;

    import com.artem.task.dao.MovieCastDao;
    import com.artem.task.dto.MovieCastDTO;
    import com.artem.task.exception.EntityAlreadyExistsException;
    import com.artem.task.exception.EntityNotFoundException;
    import com.artem.task.exception.MovieCastAlreadyExistsException;
    import com.artem.task.exception.MovieCastNotFoundException;
    import com.artem.task.model.MovieCast;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.List;

    @Service
    public class MovieCastService {

        private final MovieCastDao movieCastDao;

        public MovieCastService(MovieCastDao movieCastDao) {
            this.movieCastDao = movieCastDao;
        }

        //CRUD
        //Вывод персонажа по id фильма и актера
        public MovieCastDTO getCharacterByMovieIdAndActorId(Long movieId, Long actorId) {
            MovieCast movieCast = movieCastDao.findByMovieIdAndActorId(movieId, actorId);
            if (movieCast == null) {
                throw new MovieCastNotFoundException(movieId, actorId);
            }
            return new MovieCastDTO(movieCast.getCharacterName());
        }
        //Вывод персонажей по id фильма
        public List<MovieCastDTO> getCharactersByMovieId(Long movieId) {
            List<MovieCast> movieCharacters = movieCastDao.findByMovieId(movieId);
            List<MovieCastDTO> movieCastDTOS = new ArrayList<>();
            for (MovieCast movieCast : movieCharacters) {
                MovieCastDTO movieCastDTO = new MovieCastDTO(
                        movieCast.getCharacterName()
                );
                movieCastDTOS.add(movieCastDTO);
            }
            return movieCastDTOS;
        }
        //Вывод всех персонажей, сыгранных актером с введеным id
        public List<MovieCastDTO> getCharactersByActorId(Long actorId) {
            List<MovieCast> actorCharacters = movieCastDao.findByActorId(actorId);
            List<MovieCastDTO> movieCastDTOS = new ArrayList<>();
            for (MovieCast actorCharacter : actorCharacters) {
                MovieCastDTO movieCastDTO = new MovieCastDTO(
                        actorCharacter.getCharacterName()
                );
                movieCastDTOS.add(movieCastDTO);
            }
            return movieCastDTOS;
        }
        //Создание нового персонажа
        public void saveCharacter(MovieCast movieCast) {
            try {
                movieCastDao.save(movieCast);
            } catch (MovieCastAlreadyExistsException e) {
                throw e;
            }
        }
        //Редактирование персонажа
        public void updateCharacter(MovieCast movieCast) {
            try {
                movieCastDao.update(movieCast);
            } catch (MovieCastNotFoundException e) {
                throw e;
            }
        }
        //Удаление персонажа по id фильма и id актера
        public void deleteCharacter(Long movieId, Long actorId) {
            try {
                movieCastDao.delete(movieId, actorId);
            } catch (MovieCastNotFoundException e) {
                throw e;
            }
        }
    }
