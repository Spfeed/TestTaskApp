import styles from "./ActorUpdateModal.module.css";
import { useState, useEffect } from "react";
import axios from "axios";
import Modal from "react-bootstrap/Modal";

const ActorUpdateModal = ({
  show,
  handleClose,
  actorName,
  actorLastName,
  actorAge,
  refreshActors,
}) => {
  const [updatedName, setUpdatedName] = useState(actorName);
  const [updatedLastName, setUpdatedLastName] = useState(actorLastName);
  const [updatedAge, setUpdatedAge] = useState(actorAge);
  const [errorMessage, setErrorMessage] = useState("");
  const [actorId, setActorId] = useState(null);

  useEffect(() => {
    const fetchActorId = async () => {
      try {
        const response = await axios.get(
          `/actors/name/${actorName}/lastName/${actorLastName}/age/${actorAge}`
        );
        setActorId(response.data.id);
        setUpdatedName(actorName);
        setUpdatedLastName(actorLastName);
        setUpdatedAge(actorAge);
      } catch (error) {
        if (error.response) {
          console.error(
            "Ошибка при получении ID актера: ",
            error.response.data.message
          );
        } else {
          console.error("Произошла ошибка: ", error);
        }
      }
    };

    if (show && actorName && actorLastName && actorAge) {
      fetchActorId();
    }
  }, [show, actorName, actorLastName, actorAge]);

  const handleUpdate = async (event) => {
    event.preventDefault();

    try {
      if (actorId === null) {
        setErrorMessage("ID актера не найден.");
        return;
      }

      const response = await axios.put("/actors", {
        id: actorId,
        name: updatedName,
        lastName: updatedLastName,
        age: updatedAge,
      });

      if (response.status === 200) {
        setErrorMessage("");
        handleClose();
        refreshActors();
      }
    } catch (error) {
      if (error.response) {
        const { status, data } = error.response;
        if (status === 400) {
          if (data.name === undefined) {
            if (data.lastName === undefined) {
              setErrorMessage("Ошибка редактирования актера: " + data.age);
            } else {
              setErrorMessage("Ошибка редактирования актера: " + data.lastName);
            }
          } else {
            setErrorMessage("Ошибка при редактировании актера: " + data.name);
          }
        } else if (status === 404) {
          setErrorMessage("Ошибка при редактировании актера: " + data.message);
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
        <Modal.Title>Редактирование актера</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <form onSubmit={handleUpdate}>
          <div className={styles.inputGroup}>
            <label htmlFor="actorName">Имя актера:</label>
            <input
              type="text"
              id="actorName"
              value={updatedName}
              onChange={(e) => setUpdatedName(e.target.value)}
              className={styles.input}
              required
            />
          </div>
          {errorMessage && (
            <div className={styles.errorMessage}>{errorMessage}</div>
          )}
          <div className={styles.inputGroup}>
            <label htmlFor="actorLastName">Фамилия актера:</label>
            <input
              type="text"
              id="actorLastName"
              value={updatedLastName}
              onChange={(e) => setUpdatedLastName(e.target.value)}
              className={styles.input}
              required
            />
          </div>
          {errorMessage && (
            <div className={styles.errorMessage}>{errorMessage}</div>
          )}

          <div className={styles.inputGroup}>
            <label htmlFor="actorAge">Возраст актера:</label>
            <input
              type="number"
              id="actorAge"
              value={updatedAge}
              onChange={(e) => setUpdatedAge(e.target.value)}
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

export default ActorUpdateModal;
