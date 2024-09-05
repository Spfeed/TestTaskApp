import React from "react";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import styles from "./Sidebar.module.css";

const Sidebar = (props) => {
  return (
    <div className={styles.sidebar}>
      <h2>Фильрация</h2>
      <Container className={styles.container}>
        <Form className={styles.form}>
          <Form.Group controlId="searchByTitle">
            <Form.Label>Поиск по названию</Form.Label>
            <Form.Control type="text" placeholder="Введите название" />
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
        <Form>
          <Form.Group controlId="searchByName">
            <Form.Label>Поиск по имени и фамилии актера</Form.Label>
            <Form.Control type="text" placeholder="Введите имя и фамилию" />
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
