import { Fragment, useState, useEffect } from "react";
import NavigationBar from "../components/NavigationBar";
import axios from "axios";
import GenreCard from "../components/genres/GenreCard";

const Genres = (props) => {
  const [genres, setGenres] = useState([]);

  useEffect(() => {
    const fetchGenres = async () => {
      try {
        const response = await axios.get("/genres");
        setGenres(response.data);
      } catch (error) {
        console.error("Ошибка при получении данных: ", error);
      }
    };

    fetchGenres();
  }, []);
  return (
    <Fragment>
      <NavigationBar />
      <h1>Жанры</h1>
      {genres.map((genre, index) => (
        <GenreCard key={index} name={genre.name} />
      ))}
    </Fragment>
  );
};

export default Genres;
