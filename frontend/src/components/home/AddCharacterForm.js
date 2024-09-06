import { useState } from "react";
import axios from "axios";
import styles from "./AddCharacterForm.module.css";

const AddCharacterForm = ({ movieId, actors, onCharacterAdded }) => {
  const [selectedActorId, setSelectedActorId] = useState("");
  const [characterName, setCharacterName] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleAddCharacter = async (event) => {
    event.preventDefault();

    try {
      console.log(actors);
      await axios.post("/characters", {
        movieId: movieId,
        actorId: selectedActorId,
        characterName: characterName,
      });

      // Очистка формы после успешного добавления
      setSelectedActorId("");
      setCharacterName("");

      // Уведомление родительского компонента о добавлении персонажа
      onCharacterAdded();
    } catch (error) {
      setErrorMessage("Ошибка при добавлении персонажа: " + error.message);
    }
  };

  return (
    <form onSubmit={handleAddCharacter} className={styles.addCharacterForm}>
      <div className={styles.inputGroup}>
        <label htmlFor="actor">Актер:</label>
        <select
          id="actor"
          value={selectedActorId}
          onChange={(e) => setSelectedActorId(e.target.value)}
          className={styles.input}
          required
        >
          <option value="" disabled>
            Выберите актера
          </option>
          {actors.map((actor) => (
            <option key={actor.id} value={actor.id}>
              {actor.name} {actor.lastName}
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
      <div className={styles.submitButtonContainer}>
        <button
          type="submit"
          className={styles.submitButton}
          onClick={handleAddCharacter}
        >
          Добавить персонажа
        </button>
      </div>
    </form>
  );
};

export default AddCharacterForm;
