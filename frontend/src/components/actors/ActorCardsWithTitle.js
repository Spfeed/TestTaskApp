import { useState, useEffect } from "react";
import styles from "./ActorCardsWithTitle.module.css";
import ActorCard from "./ActorCard";
import axios from "axios";
import ActorCreateModal from "./ActorCreateModal";

const ActorCardsWithTitle = (props) => {
  const [actors, setActors] = useState([]);
  const [showModal, setShowModal] = useState(false);

  const fetchActors = async () => {
    try {
      const response = await axios.get("/actors");
      setActors(response.data);
    } catch (error) {
      console.error("Ошибка при получении данных: ", error);
    }
  };
  useEffect(() => {
    fetchActors();
  }, []);

  const handleShowModal = () => setShowModal(true);
  const handleCloseModal = () => setShowModal(false);

  const refreshActors = () => {
    fetchActors();
  };

  return (
    <>
      <div className={styles.container}>
        <h1 className={styles.title}>Актеры</h1>
        <div className={styles.createButtonContainer}>
          <button className={styles.createButton} onClick={handleShowModal}>
            Создать
          </button>
        </div>
        {actors.map((actor, index) => (
          <ActorCard
            key={index}
            name={actor.name}
            lastName={actor.lastName}
            age={actor.age}
            refreshActors={refreshActors}
          />
        ))}
      </div>
      <ActorCreateModal
        show={showModal}
        handleClose={handleCloseModal}
        refreshActors={refreshActors}
      />
    </>
  );
};

export default ActorCardsWithTitle;
