import styles from "./GenreCard.module.css";

const GenreCard = ({ name }) => {
  return (
    <div className={styles.container}>
      <h2 className={styles.genre}>{name}</h2>
    </div>
  );
};

export default GenreCard;
