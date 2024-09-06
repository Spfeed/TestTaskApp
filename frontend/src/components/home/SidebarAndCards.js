import { useEffect, useState } from "react";
import MovieCard from "./MovieCard";
import Sidebar from "./Sidebar";
import styles from "./SidebarAndCards.module.css";
import axios from "axios";
import MovieCreateModal from "./MovieCreateModal";

const SidebarAndCards = (props) => {
  const [movies, setMovies] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [filterKeyWord, setFilterKeyWord] = useState("");
  const [filterType, setFilterType] = useState("");
  const [emptyResult, setEmptyResult] = useState(false);

  const fetchMovies = async (url, method = "GET", data = null) => {
    try {
      setEmptyResult(false);
      const config = {
        method,
        url,
        data,
      };
      const response = await axios(config);
      if (response.status === 204) {
        setMovies([]);
        setEmptyResult(true);
      } else {
        setMovies(response.data);
      }
    } catch (error) {
      console.error("Ошибка при получении данных: ", error);
    }
  };

  useEffect(() => {
    if (!filterKeyWord) {
      fetchMovies("/movies/details");
    }
  }, [filterKeyWord]);

  const handleShowModal = () => setShowModal(true);
  const handleCloseModal = () => setShowModal(false);

  const refreshMovies = () => {
    if (filterType === "title") {
      fetchMovies(`/movies/serviceFiltration/${filterKeyWord}`);
    } else if (filterType === "actor") {
      fetchMovies(`/movies/filterByActor/${filterKeyWord}`);
    } else if (filterType === "genre") {
      fetchMovies("/movies/filterByGenres", "POST", filterKeyWord);
    } else {
      fetchMovies("/movies/details");
    }
  };

  const handleSearchByTitle = (keyword) => {
    setFilterKeyWord(keyword);
    setFilterType("title");
    fetchMovies(`/movies/serviceFiltration/${keyword}`);
  };

  const handleSearchByActor = (keyword) => {
    setFilterKeyWord(keyword);
    setFilterType("actor");
    fetchMovies(`/movies/filterByActor/${keyword}`);
  };

  const handleGenreChange = (selectedGenres) => {
    setFilterKeyWord(selectedGenres);
    setFilterType("genre");
    fetchMovies("/movies/filterByGenres", "POST", selectedGenres);
  };

  return (
    <>
      <div className={styles.container}>
        <Sidebar
          onSearchByTitle={handleSearchByTitle}
          onSearchByActor={handleSearchByActor}
          onGenreChange={handleGenreChange}
        />
        {!emptyResult ? (
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
        ) : (
          <p className={styles.emptyResult}>
            Фильмы по вашему запросу не найдены, попробуйте ввести другой запрос
            или перезагрузите страницу.
          </p>
        )}
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
