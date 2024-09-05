import styles from "./ActorCard.module.css";

const ActorCard = ({ name, lastName, age }) => {
  return (
    <div className={styles.container}>
      <p>{name}</p>
      <p>{lastName}</p>
      <p>{age}</p>
    </div>
  );
};

export default ActorCard;
