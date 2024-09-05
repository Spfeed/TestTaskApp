import { useEffect, useState } from "react";
import MovieCard from "./MovieCard";
import Sidebar from "./Sidebar";
import styles from "./SidebarAndCards.module.css";
import axios from "axios";

const SidebarAndCards = (props) => {
  const [movies, setMovies] = useState([]);

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const response = await axios.get("/movies/details");
        setMovies(response.data);
      } catch (error) {
        console.error("Ошибка при получении данных: ", error);
      }
    };

    fetchMovies();
  }, []);
  return (
    <div className={styles.container}>
      <Sidebar />
      <div className={styles.cards}>
        <h1 className={styles.cardsTitle}>Фильмы</h1>
        <div className={styles.cardsContainer}>
          {movies.map((movie, index) => (
            <MovieCard
              key={index}
              title={movie.title}
              releaseYear={movie.releaseYear}
              description={movie.description}
              genre={movie.genre}
              actors={movie.actors}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default SidebarAndCards;
