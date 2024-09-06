import { useEffect, useState } from "react";
import MovieCard from "./MovieCard";
import Sidebar from "./Sidebar";
import styles from "./SidebarAndCards.module.css";
import axios from "axios";
import MovieCreateModal from "./MovieCreateModal";

const SidebarAndCards = (props) => {
  const [movies, setMovies] = useState([]);
  const [showModal, setShowModal] = useState(false);

  const fetchMovies = async () => {
    try {
      const response = await axios.get("/movies/details");
      setMovies(response.data);
    } catch (error) {
      console.error("Ошибка при получении данных: ", error);
    }
  };
  useEffect(() => {
    fetchMovies();
  });

  const handleShowModal = () => setShowModal(true);
  const handleCloseModal = () => setShowModal(false);

  const refreshMovies = () => {
    fetchMovies();
  };

  return (
    <>
      <div className={styles.container}>
        <Sidebar />
        <div className={styles.cards}>
          <h1 className={styles.cardsTitle}>Фильмы</h1>
          <div className={styles.createButtonContainer}>
            <button className={styles.createButton} onClick={handleShowModal}>
              Создать
            </button>
          </div>
          <div className={styles.cardsContainer}>
            {movies.map((movie, index) => (
              <MovieCard
                key={index}
                title={movie.title}
                releaseYear={movie.releaseYear}
                description={movie.description}
                genre={movie.genre}
                actors={movie.actors}
                rating={movie.rating}
                refreshMovies={refreshMovies}
              />
            ))}
          </div>
        </div>
      </div>
      <MovieCreateModal
        show={showModal}
        handleClose={handleCloseModal}
        refreshMovies={refreshMovies}
      />
    </>
  );
};

export default SidebarAndCards;
