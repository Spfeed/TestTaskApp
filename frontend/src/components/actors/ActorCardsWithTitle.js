import { useState, useEffect } from "react";
import styles from "./ActorCardsWithTitle.module.css";
import ActorCard from "./ActorCard";
import axios from "axios";

const ActorCardsWithTitle = (props) => {
  const [actors, setActors] = useState([]);

  useEffect(() => {
    const fetchActors = async () => {
      try {
        const response = await axios.get("/actors");
        setActors(response.data);
      } catch (error) {
        console.error("Ошибка при получении данных: ", error);
      }
    };

    fetchActors();
  }, []);
  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Актеры</h1>
      {actors.map((actor, index) => (
        <ActorCard
          key={index}
          name={actor.name}
          lastName={actor.lastName}
          age={actor.age}
        />
      ))}
    </div>
  );
};

export default ActorCardsWithTitle;
