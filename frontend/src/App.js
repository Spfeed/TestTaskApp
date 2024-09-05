import { Route, Switch, Redirect } from "react-router-dom";
import Home from "./pages/Home";
import Genres from "./pages/Genres";
import Actors from "./pages/Actors";

function App() {
  return (
    <Switch>
      <Route path="/" exact>
        <Redirect to="/home" />
      </Route>
      <Route path="/home">
        <Home />
      </Route>
      <Route path="/genres">
        <Genres />
      </Route>
      <Route>
        <Actors />
      </Route>
    </Switch>
  );
}

export default App;
