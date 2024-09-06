import { Fragment, useState, useEffect } from "react";
import NavigationBar from "../components/NavigationBar";
import axios from "axios";
import GenreCard from "../components/genres/GenreCard";
import styles from "./Genres.module.css";
import GenreCreateModal from "../components/genres/GenreCreateModal";

const Genres = (props) => {
  const [genres, setGenres] = useState([]);
  const [showModal, setShowModal] = useState(false);

  const fetchGenres = async () => {
    try {
      const response = await axios.get("/genres");
      setGenres(response.data);
    } catch (error) {
      console.error("Ошибка при получении данных: ", error);
    }
  };
  useEffect(() => {
    fetchGenres();
  }, []);

  const handleShowModal = () => setShowModal(true);
  const handleCloseModal = () => setShowModal(false);

  const refreshGenres = () => {
    fetchGenres();
  };

  return (
    <Fragment>
      <NavigationBar />
      <div className={styles.titleAndCreate}>
        <h1>Жанры</h1>
        <div className={styles.createButtonContainer}>
          <button className={styles.createButton} onClick={handleShowModal}>
            Создать
          </button>
        </div>
      </div>
      {genres.map((genre, index) => (
        <GenreCard
          key={index}
          name={genre.name}
          refreshGenres={refreshGenres}
        />
      ))}
      <GenreCreateModal
        show={showModal}
        handleClose={handleCloseModal}
        refreshGenres={refreshGenres}
      />
    </Fragment>
  );
};

export default Genres;
