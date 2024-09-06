import styles from "./MovieCard.module.css";
import Accordion from "react-bootstrap/Accordion";
import { useState } from "react";
import MovieUpdateModal from "./MovieUpdateModal";
import axios from "axios";

const MovieCard = ({
  title,
  releaseYear,
  description,
  genre,
  actors,
  rating,
  refreshMovies,
}) => {
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [deleteError, setDeleteError] = useState("");

  const handleShowUpdateModal = () => setShowUpdateModal(true);
  const handleCloseUpdateModal = () => setShowUpdateModal(false);

  const handleDeleteMovie = async () => {
    try {
      const idResponse = await axios.get(`/movies/findByTitle/${title}`);
      const movieId = idResponse.data;

      const response = await axios.delete(`/movies/${movieId}`);
      if (response.status === 204) {
        refreshMovies();
      }
    } catch (error) {
      if (error.response) {
        if (error.response.data.status === 404) {
          setDeleteError(`Ошибка при удалении фильма: ${error.message}`);
        } else {
          console.error(error);
        }
      } else {
        console.error(error);
      }
    }
  };

  return (
    <>
      <div className={styles.card}>
        <div className={styles.titleAndButtons}>
          <h2 className={styles.title}>{title}</h2>
          <div className={styles.buttons}>
            <div className={styles.updateButtonContainer}>
              <button
                className={styles.updateButton}
                onClick={handleShowUpdateModal}
              >
                Редактировать
              </button>
            </div>
            <div className={styles.deleteButtonContainer}>
              <button
                className={styles.deleteButton}
                onClick={handleDeleteMovie}
              >
                Удалить
              </button>
            </div>
          </div>
        </div>
        {deleteError && (
          <div className={styles.errorMessage}>{deleteError}</div>
        )}
        <div className={styles.genreAndYearContainer}>
          <p className={styles.genre}>{genre}</p>
          <p className={styles.releaseYear}>{releaseYear}</p>
          <p>{rating}</p>
        </div>
        <div className={styles.descriptionContainer}>
          <p className={styles.description}>{description}</p>
        </div>
        <Accordion>
          <Accordion.Item eventKey="0">
            <Accordion.Header>Актеры и роли</Accordion.Header>
            <Accordion.Body>
              {actors.map((actor, index) => (
                <div key={index} className={styles.actorData}>
                  <p className={styles.actorName}>{actor.name}</p>
                  <p className={styles.actorLastName}>{actor.lastName}</p>
                  <p>-</p>
                  <p className={styles.characterName}>{actor.characterName}</p>
                </div>
              ))}
            </Accordion.Body>
          </Accordion.Item>
        </Accordion>
      </div>
      <MovieUpdateModal
        show={showUpdateModal}
        handleClose={handleCloseUpdateModal}
        title={title}
        releaseYear={releaseYear}
        description={description}
        genre={genre}
        rating={rating}
      />
    </>
  );
};

export default MovieCard;
