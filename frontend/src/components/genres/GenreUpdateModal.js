import styles from "./GenreUpdateModal.module.css";
import Modal from "react-bootstrap/Modal";
import { useState, useEffect } from "react";
import axios from "axios";

const GenreUpdateModal = ({ show, handleClose, genreName, refreshGenres }) => {
  const [updatedName, setUpdatedName] = useState(genreName);
  const [errorMessage, setErrorMessage] = useState("");
  const [genreId, setGenreId] = useState(null);

  useEffect(() => {
    const fetchGenreId = async () => {
      try {
        const response = await axios.get(`/genres/findByName/${genreName}`);
        setGenreId(response.data.id);
        setUpdatedName(genreName);
      } catch (error) {
        if (error.response) {
          console.error(
            "Ошибка при получении ID жанра: ",
            error.response.data.message
          );
        } else {
          console.error("Произошла ошибка: ", error);
        }
      }
    };

    if (show && genreName) {
      fetchGenreId();
    }
  }, [show, genreName]);

  const handleUpdate = async (event) => {
    event.preventDefault();

    try {
      if (genreId === null) {
        setErrorMessage("ID жанра не найден.");
        return;
      }

      const response = await axios.put("/genres", {
        id: genreId,
        name: updatedName,
      });

      if (response.status === 200) {
        setErrorMessage("");
        handleClose();
        refreshGenres();
      }
    } catch (error) {
      if (error.response) {
        const { status, data } = error.response;
        if (status === 400) {
          setErrorMessage("Ошибка при редактировании жанра: " + data.name);
        } else if (status === 404) {
          setErrorMessage("Ошибка при редактировании жанра: " + data.message);
        } else {
          console.error("Произошла ошибка: ", error);
        }
      } else {
        console.error("Произошла ошибка: ", error);
      }
    }
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Редактирование жанра</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <form onSubmit={handleUpdate}>
          <div className={styles.inputGroup}>
            <label htmlFor="genreName">Название жанра:</label>
            <input
              type="text"
              id="genreName"
              value={updatedName}
              onChange={(e) => setUpdatedName(e.target.value)}
              className={styles.input}
              required
            />
          </div>
          {errorMessage && (
            <div className={styles.errorMessage}>{errorMessage}</div>
          )}
          <div className={styles.submitButtonContainer}>
            <button type="submit" className={styles.submitButton}>
              Изменить
            </button>
          </div>
        </form>
      </Modal.Body>
    </Modal>
  );
};

export default GenreUpdateModal;
