import { Fragment } from "react";
import NavigationBar from "../components/NavigationBar";
import SidebarAndCards from "../components/home/SidebarAndCards";

const Home = (props) => {
  return (
    <Fragment>
      <div>
        <NavigationBar />
        <SidebarAndCards />
        {/* Добавьте сюда содержимое вашей домашней страницы */}
      </div>
    </Fragment>
  );
};

export default Home;
