import styles from "./CharacterListItem.module.css";
import axios from "axios";
import { useState } from "react";

const CharacterListItem = ({
  movieId,
  actorId,
  firstName,
  lastName,
  characterName,
  onDelete,
}) => {
  const [error, setError] = useState("");
  const handleDelete = async () => {
    try {
      await axios.delete(`/characters/movie/${movieId}/actor/${actorId}`);
      onDelete(actorId);
    } catch (error) {
      if (error.response) {
        setError(error.response.data.message);
      }
      console.error("Ошибка при удалении персонажа:", error);
    }
  };

  return (
    <div className={styles.characterListItem}>
      <span>{`${firstName} ${lastName} - ${characterName}`}</span>
      <div className={styles.deleteButtonContainer}>
        <button onClick={handleDelete} className={styles.deleteButton}>
          Удалить
        </button>
      </div>
      {error && <div className={styles.error}>{error}</div>}
    </div>
  );
};

export default CharacterListItem;
