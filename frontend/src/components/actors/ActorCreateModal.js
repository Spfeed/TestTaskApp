import styles from "./ActorCreateModal.module.css";
import { useState } from "react";
import axios from "axios";
import Modal from "react-bootstrap/Modal";

const ActorCreateModal = ({ show, handleClose, refreshActors }) => {
  const [actorName, setActorName] = useState("");
  const [actorLastName, setActorLastName] = useState("");
  const [actorAge, setActorAge] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post("/actors", {
        name: actorName,
        lastName: actorLastName,
        age: actorAge,
      });

      if (response.status === 201) {
        setErrorMessage("");
        refreshActors();
        handleClose();
      }
    } catch (error) {
      if (error.response && error.response.status === 400) {
        const data = error.response.data;
        if (data.name === undefined) {
          if (data.lastName === undefined) {
            setErrorMessage("Ошибка при создании актера: " + data.age);
          } else {
            setErrorMessage("Ошибка при создании актера: " + data.lastName);
          }
        } else {
          setErrorMessage("Ошибка при создании актера: " + data.name);
        }
      } else if (error.response && error.response.status === 409) {
        const serverMessage =
          "Ошибка при создании актера: " + error.response.data;
        setErrorMessage(serverMessage);
      } else {
        console.error("Произошла ошибка: ", error);
      }
    }
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Создание актера</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <form onSubmit={handleSubmit}>
          <div className={styles.inputGroup}>
            <label htmlFor="actorName">Введите имя актера:</label>
            <input
              type="text"
              id="actorName"
              value={actorName}
              onChange={(e) => setActorName(e.target.value)}
              className={styles.input}
              required
            />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="actorLastName">Введите фамилию актера:</label>
            <input
              type="text"
              id="actorLastName"
              value={actorLastName}
              onChange={(e) => setActorLastName(e.target.value)}
              className={styles.input}
              required
            />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="actorAge">Введите возраст актера:</label>
            <input
              type="number"
              id="actorAge"
              value={actorAge}
              onChange={(e) => setActorAge(e.target.value)}
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

export default ActorCreateModal;
