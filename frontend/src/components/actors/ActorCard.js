import styles from "./ActorCard.module.css";
import { useState } from "react";
import ActorUpdateModal from "./ActorUpdateModal";
import axios from "axios";

const ActorCard = ({ name, lastName, age, refreshActors }) => {
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [deleteError, setDeleteError] = useState("");

  const handleShowUpdateModal = () => setShowUpdateModal(true);
  const handleCloseUpdateModal = () => setShowUpdateModal(false);

  const handleDelete = async () => {
    try {
      const response = await axios.get(
        `/actors/name/${name}/lastName/${lastName}/age/${age}`
      );
      const actorId = response.data.id;

      const deleteResponse = await axios.delete(`/actors/${actorId}`);

      if (deleteResponse.status === 204) {
        refreshActors();
      }
    } catch (error) {
      if (error.response) {
        const { status, data } = error.response;
        if (status === 404) {
          setDeleteError(data.message);
        } else {
          console.error("произошла ошибка при удалении актера: ", error);
        }
      } else {
        console.error("Произошла ошибка: ", error);
      }
    }
  };

  return (
    <>
      <div className={styles.container}>
        <p>{name}</p>
        <p>{lastName}</p>
        <p>{age}</p>
        <div className={styles.updateButtonContainer}>
          <button
            className={styles.updateButton}
            onClick={handleShowUpdateModal}
          >
            Редактировать
          </button>
        </div>
        <div className={styles.deleteButtonContainer}>
          <button className={styles.deleteButton} onClick={handleDelete}>
            Удалить
          </button>
          {deleteError && (
            <div className={styles.errorMessage}>{deleteError}</div>
          )}
        </div>
      </div>
      <ActorUpdateModal
        show={showUpdateModal}
        handleClose={handleCloseUpdateModal}
        actorName={name}
        actorLastName={lastName}
        actorAge={age}
        refreshActors={refreshActors}
      />
    </>
  );
};

export default ActorCard;
