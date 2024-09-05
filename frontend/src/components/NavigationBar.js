import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { NavLink } from "react-router-dom/cjs/react-router-dom";

function NavigationBar() {
  return (
    <Navbar bg="light" data-bs-theme="light">
      <Container>
        <Navbar.Brand as={NavLink} to="/home">
          TestTask
        </Navbar.Brand>
        <Nav className="me-auto">
          <Nav.Link as={NavLink} to="/home">
            Фильмы
          </Nav.Link>
          <Nav.Link as={NavLink} to="/genres">
            Жанры
          </Nav.Link>
          <Nav.Link as={NavLink} to="/actors">
            Актеры
          </Nav.Link>
        </Nav>
      </Container>
    </Navbar>
  );
}

export default NavigationBar;
