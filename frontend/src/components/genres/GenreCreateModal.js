import styles from "./GenreCreateModal.module.css";
import Modal from "react-bootstrap/Modal";
import { useState } from "react";
import axios from "axios";

const GenreCreateModal = ({ show, handleClose, refreshGenres }) => {
  const [genreName, setGenreName] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post("/genres", { name: genreName });

      if (response.status === 201) {
        setErrorMessage("");
        refreshGenres();
        handleClose();
      }
    } catch (error) {
      if (error.response && error.response.status === 400) {
        const serverMessage =
          "Ошибка при создании жанра: " + error.response.data.name;
        setErrorMessage(serverMessage);
      } else if (error.response && error.response.status === 409) {
        const serverMessage =
          "Ошибка при создании жанра: " + error.response.data;
        setErrorMessage(serverMessage);
      } else {
        console.error("Произошла ошибка: ", error);
      }
    }
  };
  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Создание жанра</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <form onSubmit={handleSubmit}>
          <div className={styles.inputGroup}>
            <label htmlFor="genreName">Введите название жанра:</label>
            <input
              type="text"
              id="genreName"
              value={genreName}
              onChange={(e) => setGenreName(e.target.value)}
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
      </Modal.Body>
    </Modal>
  );
};

export default GenreCreateModal;
