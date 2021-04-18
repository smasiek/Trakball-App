
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import Places from "./Places"
import Squads from "./Squads"




export default function BasicExample() {
  return (
    <Router>
      <div>
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/squads">Squads</Link>
          </li>
          <li>
            <Link to="/places">Places</Link>
          </li>
        </ul>

        <hr />

        {/*
          A <Switch> looks through all its children <Route>
          elements and renders the first one whose path
          matches the current URL. Use a <Switch> any time
          you have multiple routes, but you want only one
          of them to render at a time
        */}
        <Switch>
          <Route exact path="/">
            <Home />
          </Route>
          <Route path="/squads">
            <Squads />
          </Route>
          <Route path="/places">
            <Places />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}
function Home() {
  return (
    <div>
      <h2>Home</h2>
    </div>
  );
}

// function Squads() {
//   return (
//     <div>
//      {this.state.data.map(squad=><Squad info={squad}/>)}
//     </div>
//   );
// }

// function Places() {
//   return (
//     <div>
//      {this.state.data.map(place=><Place info={place}/>)}
//     </div>
//   );
// }

//export default App;
