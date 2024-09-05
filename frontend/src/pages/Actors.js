import { Fragment } from "react";
import NavigationBar from "../components/NavigationBar";
import ActorCardsWithTitle from "../components/actors/ActorCardsWithTitle";

const Actors = (props) => {
  return (
    <Fragment>
      <NavigationBar />
      <ActorCardsWithTitle />
    </Fragment>
  );
};

export default Actors;
