import styles from "./GenreCard.module.css";
import { useState } from "react";
import GenreUpdateModal from "./GenreUpdateModal";
import axios from "axios";

const GenreCard = ({ name, refreshGenres }) => {
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [deleteError, setDeleteError] = useState("");

  const handleShowUpdateModal = () => setShowUpdateModal(true);
  const handleCloseUpdateModal = () => setShowUpdateModal(false);

  const handleDelete = async () => {
    try {
      const response = await axios.get(`/genres/findByName/${name}`);
      const genreId = response.data.id;

      const deleteResponse = await axios.delete(`/genres/${genreId}`);

      if (deleteResponse.status === 204) {
        refreshGenres();
      }
    } catch (error) {
      if (error.response) {
        const { status, data } = error.response;
        if (status === 404) {
          setDeleteError(data.message);
        } else {
          console.error("произошла ошибка при удалении жанра: ", error);
        }
      } else {
        console.error("Произошла ошибка: ", error);
      }
    }
  };

  return (
    <>
      <div className={styles.container}>
        <div className={styles.titleAndCreate}>
          <h2 className={styles.genre}>{name}</h2>
        </div>
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
      <GenreUpdateModal
        show={showUpdateModal}
        handleClose={handleCloseUpdateModal}
        genreName={name}
        refreshGenres={refreshGenres}
      />
    </>
  );
};

export default GenreCard;
