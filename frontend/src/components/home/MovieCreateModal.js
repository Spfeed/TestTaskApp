import styles from "./MovieCreateModal.module.css";
import { useState, useEffect } from "react";
import axios from "axios";
import Modal from "react-bootstrap/Modal";

const MovieCreateModal = ({ show, handleClose, refreshMovies }) => {
  const [title, setTitle] = useState("");
  const [releaseYear, setReleaseYear] = useState("");
  const [description, setDescription] = useState("");
  const [rating, setRating] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [genres, setGenres] = useState([]);
  const [genreName, setGenreName] = useState("");
  const [movieId, setMovieId] = useState(null);
  const [actors, setActors] = useState([]);
  const [selectedActor, setSelectedActor] = useState("");
  const [characterName, setCharacterName] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [characterAdded, setCharacterAdded] = useState(false);

  useEffect(() => {
    const fetchGenres = async () => {
      try {
        const response = await axios.get("/genres");
        setGenres(response.data);
      } catch (error) {
        console.error("Ошибка при загрузке жанров:", error);
      }
    };

    fetchGenres();
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const genreResponse = await axios.get(`/genres/findByName/${genreName}`);
      const genreId = genreResponse.data.id;

      const response = await axios.post("/movies", {
        title,
        releaseYear: parseInt(releaseYear),
        description,
        genreId,
        rating: parseFloat(rating),
      });

      if (response.status === 201) {
        const movieResponse = await axios.get(`/movies/findByTitle/${title}`);
        setMovieId(movieResponse.data);
        setErrorMessage("");
        setSuccessMessage("Фильм успешно создан! Теперь добавьте персонажей.");
        await fetchActors();
      }
    } catch (error) {
      if (error.response) {
        const { status, data } = error.response;
        if (status === 400) {
          if (data.title === undefined) {
            if (data.releaseYear === undefined) {
              if (data.description === undefined) {
                if (data.genreId === undefined) {
                  setErrorMessage("Ошибка при создании фильма: " + data.rating);
                } else {
                  setErrorMessage(
                    "Ошибка при создании фильма: " + data.genreId
                  );
                }
              } else {
                setErrorMessage(
                  "Ошибка при создании фильма: " + data.description
                );
              }
            } else {
              setErrorMessage(
                "Ошибка при создании фильма: " + data.releaseYear
              );
            }
          } else {
            setErrorMessage("Ошибка при создании фильма: " + data.title);
          }
        } else if (status === 409) {
          setErrorMessage("Ошибка при создании фильма: " + data.message);
        } else {
          console.error("Произошла ошибка:", error);
        }
      } else {
        console.error("Произошла ошибка:", error);
      }
    }
  };

  const fetchActors = async () => {
    try {
      const response = await axios.get("/actors");
      setActors(response.data);
    } catch (error) {
      console.error("Ошибка при загрузке актеров:", error);
    }
  };

  const handleCharacterSubmit = async (event) => {
    event.preventDefault();

    const [name, lastName, age] = selectedActor.split("-");
    const actorResponse = await axios.get(
      `/actors/name/${name}/lastName/${lastName}/age/${age}`
    );

    const actorId = actorResponse.data.id;

    try {
      const response = await axios.post("/characters", {
        movieId,
        actorId,
        characterName,
      });

      if (response.status === 201) {
        setSuccessMessage(
          "Персонаж добавлен, можете добавить еще или закрыть окно."
        );
        setCharacterAdded(true);
        refreshMovies();
      }
    } catch (error) {
      if (error.response) {
        const { status, data } = error.response;
        if (status === 400) {
          if (data.movieId === undefined) {
            if (data.actorId === undefined) {
              setErrorMessage(
                "Ошибка при создании персонажа: " + data.characterName
              );
            } else {
              setErrorMessage("Ошибка при создании персонажа: " + data.actorId);
            }
          } else {
            setErrorMessage("Ошибка при создании персонажа: " + data.movieId);
          }
        } else if (status === 409) {
          setErrorMessage("Ошибка при создании персонажа: " + data.message);
        } else {
          console.error("Произошла ошибка:", error);
        }
      } else {
        console.error("Произошла ошибка:", error);
      }
    }
  };

  return (
    <Modal
      show={show}
      onHide={() => (movieId && !characterAdded ? null : handleClose())}
    >
      <Modal.Header closeButton={movieId}>
        <Modal.Title>Создание фильма</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {movieId === null ? (
          <form onSubmit={handleSubmit}>
            <div className={styles.inputGroup}>
              <label htmlFor="title">Название фильма:</label>
              <input
                type="text"
                id="title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                className={styles.input}
                required
              />
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="releaseYear">Год выхода:</label>
              <input
                type="number"
                id="releaseYear"
                value={releaseYear}
                onChange={(e) => setReleaseYear(e.target.value)}
                className={styles.input}
                required
              />
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="description">Описание:</label>
              <textarea
                id="description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                className={styles.textarea}
              />
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="genre">Жанр:</label>
              <select
                id="genre"
                value={genreName}
                onChange={(e) => setGenreName(e.target.value)}
                className={styles.input}
                required
              >
                <option value="" disabled>
                  Выберите жанр
                </option>
                {genres.map((genre) => (
                  <option key={genre.name} value={genre.name}>
                    {genre.name}
                  </option>
                ))}
              </select>
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="rating">Рейтинг:</label>
              <input
                type="number"
                step="0.1"
                id="rating"
                value={rating}
                onChange={(e) => setRating(e.target.value)}
                className={styles.input}
                required
              />
            </div>
            {errorMessage && (
              <div className={styles.errorMessage}>{errorMessage}</div>
            )}
            <div className={styles.submitButtonContainer}>
              <button type="submit" className={styles.submitButton}>
                Создать
              </button>
            </div>
          </form>
        ) : (
          <form onSubmit={handleCharacterSubmit}>
            <div className={styles.inputGroup}>
              <label htmlFor="actor">Выберите актера:</label>
              <select
                id="actor"
                value={selectedActor}
                onChange={(e) => setSelectedActor(e.target.value)}
                className={styles.input}
                required
              >
                <option value="" disabled>
                  Выберите актера
                </option>
                {actors.map((actor) => (
                  <option
                    key={`${actor.name}-${actor.lastName}-${actor.age}`}
                    value={`${actor.name}-${actor.lastName}-${actor.age}`}
                  >
                    {actor.name}-{actor.lastName}-{actor.age}
                  </option>
                ))}
              </select>
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="characterName">Имя персонажа:</label>
              <input
                type="text"
                id="characterName"
                value={characterName}
                onChange={(e) => setCharacterName(e.target.value)}
                className={styles.input}
                required
              />
            </div>
            {errorMessage && (
              <div className={styles.errorMessage}>{errorMessage}</div>
            )}
            {successMessage && (
              <div className={styles.successMessage}>{successMessage}</div>
            )}
            <div className={styles.submitButtonContainer}>
              <button type="submit" className={styles.submitButton}>
                Сохранить
              </button>
            </div>
          </form>
        )}
      </Modal.Body>
    </Modal>
  );
};

export default MovieCreateModal;
