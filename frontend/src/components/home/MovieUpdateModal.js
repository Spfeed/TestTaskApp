import styles from "./MovieUpdateModal.module.css";
import { useState, useEffect } from "react";
import axios from "axios";
import Modal from "react-bootstrap/Modal";
import CharacterListItem from "./CharacterListItem";
import AddCharacterForm from "./AddCharacterForm";

const MovieUpdateModal = ({
  show,
  handleClose,
  title,
  releaseYear,
  description,
  genre,
  rating,
}) => {
  const [updatedTitle, setUpdatedTitle] = useState(title || "");
  const [updatedReleaseYear, setUpdatedReleaseYear] = useState(
    releaseYear || ""
  );
  const [updatedDescription, setUpdatedDescription] = useState(
    description || ""
  );
  const [updatedGenreName, setUpdatedGenreName] = useState(genre || "");
  const [updatedRating, setUpdatedRating] = useState(rating || "");
  const [genres, setGenres] = useState([]);
  const [genreId, setGenreId] = useState(null);
  const [characters, setCharacters] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  const [showAddCharacterForm, setShowAddCharacterForm] = useState(false);
  const [actors, setActors] = useState([]);
  const [movieId, setMovieId] = useState(null);

  useEffect(() => {
    const fetchGenres = async () => {
      try {
        const response = await axios.get("/genres");
        setGenres(response.data);

        const genreResponse = await axios.get(
          `/genres/findByName/${updatedGenreName}`
        );
        setGenreId(genreResponse.data.id);
      } catch (error) {
        console.error("Ошибка при загрузке жанров:", error);
      }
    };

    fetchGenres();
  }, [updatedGenreName]);

  useEffect(() => {
    const fetchCharacters = async () => {
      try {
        const movieResponse = await axios.get(`/movies/findByTitle/${title}`);
        const movieId = movieResponse.data;
        if (!movieId) {
          console.error("movieId is undefined");
          return;
        }
        setMovieId(movieId);

        const charactersResponse = await axios.get(
          `/characters/movie/${movieId}`
        );
        const charactersData = await Promise.all(
          charactersResponse.data.map(async (character) => {
            const actorResponse = await axios.get(
              `/actors/${character.actorId}`
            );
            return {
              actorId: character.actorId,
              firstName: actorResponse.data.name,
              lastName: actorResponse.data.lastName,
              characterName: character.characterName,
            };
          })
        );
        setCharacters(charactersData);
      } catch (error) {
        console.error("Ошибка при загрузке персонажей:", error);
      }
    };
    fetchCharacters();
  }, [title]);

  useEffect(() => {
    const fetchActors = async () => {
      try {
        const response = await axios.get("/actors/allWithIds");
        setActors(response.data);
      } catch (error) {
        console.error("Ошибка при загрузке актеров:", error);
      }
    };

    fetchActors();
  }, []);

  const handleUpdate = async (event) => {
    event.preventDefault();

    try {
      const movieResponse = await axios.get(`/movies/findByTitle/${title}`);
      const movieId = movieResponse.data;

      await axios.put("/movies", {
        id: movieId,
        title: updatedTitle,
        releaseYear: parseInt(updatedReleaseYear),
        description: updatedDescription,
        genreId,
        rating: parseFloat(updatedRating),
      });

      handleClose();
    } catch (error) {
      if (error.response) {
        const { status, data } = error.response;
        if (status === 400) {
          if (data.id === undefined) {
            if (data.title === undefined) {
              if (data.releaseYear === undefined) {
                if (data.description === undefined) {
                  if (data.genreId === undefined) {
                    setErrorMessage(
                      "Ошибка при обновлении фильма: " + data.rating
                    );
                  } else {
                    setErrorMessage(
                      "Ошибка при обновлении фильма: " + data.genreId
                    );
                  }
                } else {
                  setErrorMessage(
                    "Ошибка при обновлении фильма: " + data.description
                  );
                }
              } else {
                setErrorMessage(
                  "Ошибка при обновлении фильма: " + data.releaseYear
                );
              }
            } else {
              setErrorMessage("Ошибка при обновлении фильма: " + data.title);
            }
          } else {
            setErrorMessage("Ошибка при обновлении фильма: " + data.id);
          }
        } else if (status === 404) {
          setErrorMessage("Ошибка при обновлении фильма: " + data.message);
        } else {
          console.error("Произошла ошибка:", error);
        }
      } else {
        console.error("Произошла ошибка:", error);
      }
    }
  };

  const handleCharacterDelete = (actorId) => {
    setCharacters(
      characters.filter((character) => character.actorId !== actorId)
    );
  };

  const handleCharacterAdded = () => {
    // Функция вызывается после успешного добавления персонажа, обновляем список персонажей
    setShowAddCharacterForm(false); // Скрываем форму после добавления
    // Повторное получение персонажей
    const fetchCharacters = async () => {
      try {
        const charactersResponse = await axios.get(
          `/characters/movie/${movieId}`
        );
        const charactersData = await Promise.all(
          charactersResponse.data.map(async (character) => {
            const actorResponse = await axios.get(
              `/actors/${character.actorId}`
            );
            return {
              actorId: character.actorId,
              firstName: actorResponse.data.name,
              lastName: actorResponse.data.lastName,
              characterName: character.characterName,
            };
          })
        );
        setCharacters(charactersData);
      } catch (error) {
        console.error("Ошибка при загрузке персонажей:", error);
      }
    };
    fetchCharacters();
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Редактирование фильма</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <form onSubmit={handleUpdate}>
          <div className={styles.inputGroup}>
            <label htmlFor="title">Название фильма:</label>
            <input
              type="text"
              id="title"
              value={updatedTitle}
              onChange={(e) => setUpdatedTitle(e.target.value)}
              className={styles.input}
              required
            />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="releaseYear">Год выпуска:</label>
            <input
              type="number"
              id="releaseYear"
              value={updatedReleaseYear}
              onChange={(e) => setUpdatedReleaseYear(e.target.value)}
              className={styles.input}
              required
            />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="description">Описание:</label>
            <textarea
              id="description"
              value={updatedDescription}
              onChange={(e) => setUpdatedDescription(e.target.value)}
              className={styles.input}
              required
            />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="genre">Жанр:</label>
            <select
              id="genre"
              value={updatedGenreName}
              onChange={(e) => {
                setUpdatedGenreName(e.target.value);
                const selectedGenre = genres.find(
                  (genre) => genre.name === e.target.value
                );
                setGenreId(selectedGenre?.id || null);
              }}
              className={styles.input}
              required
            >
              <option value="" disabled>
                Выберите жанр
              </option>
              {genres.map((genre) => (
                <option key={genre.id} value={genre.name}>
                  {genre.name}
                </option>
              ))}
            </select>
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="rating">Рейтинг:</label>
            <input
              type="number"
              id="rating"
              step="0.1"
              min="0"
              max="10"
              value={updatedRating}
              onChange={(e) => setUpdatedRating(e.target.value)}
              className={styles.input}
              required
            />
          </div>
          {errorMessage && (
            <div className={styles.errorMessage}>{errorMessage}</div>
          )}
          <div className={styles.charactersContainer}>
            <h5>Персонажи:</h5>
            {characters.length > 0 ? (
              characters.map((character) => (
                <CharacterListItem
                  key={character.actorId}
                  movieId={movieId}
                  actorId={character.actorId}
                  firstName={character.firstName}
                  lastName={character.lastName}
                  characterName={character.characterName}
                  onDelete={handleCharacterDelete}
                />
              ))
            ) : (
              <p>Персонажи не найдены.</p>
            )}
            <div className={styles.addCharacterButtonContainer}>
              <button
                type="button"
                onClick={() => setShowAddCharacterForm(!showAddCharacterForm)}
                className={styles.addCharacterButton}
              >
                {showAddCharacterForm ? "Отменить" : "Добавить персонажа"}
              </button>
            </div>

            {/* Форма добавления персонажа */}
            {showAddCharacterForm && (
              <AddCharacterForm
                movieId={movieId}
                actors={actors}
                onCharacterAdded={handleCharacterAdded}
              />
            )}
          </div>
        </form>
      </Modal.Body>
      <Modal.Footer>
        <div className={styles.cancelButtonContainer}>
          <button onClick={handleClose} className={styles.cancelButton}>
            Отмена
          </button>
        </div>
        <div className={styles.submitButtonContainer}>
          <button onClick={handleUpdate} className={styles.submitButton}>
            Сохранить изменения
          </button>
        </div>
      </Modal.Footer>
    </Modal>
  );
};

export default MovieUpdateModal;
