import styles from "./MovieCard.module.css";
import Accordion from "react-bootstrap/Accordion";

const MovieCard = ({ title, releaseYear, description, genre, actors }) => {
  return (
    <div className={styles.card}>
      <h2 className={styles.title}>{title}</h2>
      <div className={styles.genreAndYearContainer}>
        <p className={styles.genre}>{genre}</p>
        <p className={styles.releaseYear}>{releaseYear}</p>
      </div>
      <div className={styles.descriptionContainer}>
        <p className={styles.description}>{description}</p>
      </div>
      <Accordion>
        <Accordion.Item eventKey="0">
          <Accordion.Header>Актеры и роли</Accordion.Header>
          <Accordion.Body>
            {actors.map((actor, index) => (
              <div key={index} className={styles.actorData}>
                <p className={styles.actorName}>{actor.name}</p>
                <p className={styles.actorLastName}>{actor.lastName}</p>
                <p>-</p>
                <p className={styles.characterName}>{actor.characterName}</p>
              </div>
            ))}
          </Accordion.Body>
        </Accordion.Item>
      </Accordion>
    </div>
  );
};

export default MovieCard;
