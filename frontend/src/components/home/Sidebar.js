import React from "react";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import styles from "./Sidebar.module.css";
import { useState, useEffect } from "react";
import axios from "axios";

const Sidebar = ({ onSearchByTitle, onSearchByActor, onGenreChange }) => {
  const [titleKeyword, setTitleKeyword] = useState("");
  const [actorKeyword, setActorKeyword] = useState("");
  const [genres, setGenres] = useState([]);
  const [selectedGenres, setSelectedGenres] = useState([]);

  useEffect(() => {
    const fetchGenres = async () => {
      try {
        const response = await axios.get("/genres");
        setGenres(response.data);
      } catch (error) {
        console.error("Ошибка при получении жанров: ", error);
      }
    };

    fetchGenres();
  }, []);

  const handleGenreChange = (genreName) => {
    setSelectedGenres((prevSelectedGenres) =>
      prevSelectedGenres.includes(genreName)
        ? prevSelectedGenres.filter((g) => g !== genreName)
        : [...prevSelectedGenres, genreName]
    );
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onGenreChange(selectedGenres);
  };

  const handleSearchByTitle = (e) => {
    e.preventDefault();
    if (titleKeyword.trim()) {
      onSearchByTitle(titleKeyword.trim());
    }
  };

  const handleSearchByActor = (e) => {
    e.preventDefault();
    if (actorKeyword.trim()) {
      onSearchByActor(actorKeyword.trim());
    }
  };
  return (
    <div className={styles.sidebar}>
      <h2>Фильрация</h2>
      <Container className={styles.container}>
        <Form className={styles.form} onSubmit={handleSearchByTitle}>
          <Form.Group controlId="searchByTitle">
            <Form.Label>Поиск по названию</Form.Label>
            <Form.Control
              type="text"
              placeholder="Введите название"
              value={titleKeyword}
              onChange={(e) => setTitleKeyword(e.target.value)}
            />
          </Form.Group>
          <Button
            variant="primary"
            type="submit"
            className={styles.searchbutton}
          >
            Поиск
          </Button>
        </Form>
      </Container>
      <Container className={styles.container}>
        <Form onSubmit={handleSearchByActor}>
          <Form.Group controlId="searchByName">
            <Form.Label>Поиск по имени и фамилии актера</Form.Label>
            <Form.Control
              type="text"
              placeholder="Введите имя и фамилию (фамилию или имя)"
              value={actorKeyword}
              onChange={(e) => setActorKeyword(e.target.value)}
            />
          </Form.Group>

          <Button
            variant="primary"
            type="submit"
            className={styles.searchbutton}
          >
            Поиск
          </Button>
        </Form>
      </Container>
      <Container className={styles.container}>
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="searchByGenres">
            <Form.Label>Фильтр по жанрам</Form.Label>
            {genres.map((genre) => (
              <Form.Check
                key={genre.name}
                type="checkbox"
                label={genre.name}
                checked={selectedGenres.includes(genre.name)}
                onChange={() => handleGenreChange(genre.name)}
              />
            ))}
          </Form.Group>
          <Button
            variant="primary"
            type="submit"
            className={styles.searchbutton}
          >
            Поиск
          </Button>
        </Form>
      </Container>
    </div>
  );
};

export default Sidebar;
