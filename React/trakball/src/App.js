import React, { useState, useEffect } from "react";
import { Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import logo from './assets/img/Logo big.png';
import AuthService from "./services/auth.service";

import Login from "./components/Login";
import Register from "./components/Register";
import Home from "./components/Home";
import Profile from "./components/Profile";
import BoardUser from "./components/BoardUser";
import BoardSquads from "./components/BoardSquads";
import BoardYourSquads from "./components/BoardYourSquads";
import BoardAdmin from "./components/BoardAdmin";
import BoardAddNewSquad from "./components/BoardAddNewSquad";

const App = () => {
  const [showYourSquadsBoard, setShowYourSquadsBoard] = useState(false);
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);
      setShowYourSquadsBoard(user.roles.includes("ROLE_USER"));
      //setShowSquadsBoard(user.roles.includes("ROLE_MODERATOR"));
      setShowAdminBoard(user.roles.includes("ROLE_ADMIN"));
    }
  }, []);

  const logOut = () => {
    AuthService.logout();
  };

  return (
    <div>
      <nav className="navbar navbar-expand navbar-dark bg-success">
        <Link to={"/"} className="navbar-brand">
          <img src={logo} alt="Logo" style={{height:'6vh'}}/>
        </Link>
        <div className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/home"} className="nav-link">
              Home
            </Link>
          </li>

          {//showSquadsBoard && (
            <li className="nav-item">
              <Link to={"/squads"} className="nav-link">
                Squads
              </Link>
            </li>
         // )
        }

        {showYourSquadsBoard && (
            <li className="nav-item">
            <Link to={"/your_squads"} className="nav-link">
              Your Squads
            </Link>
          </li>
        )
      }

          {showAdminBoard && (
            <li className="nav-item">
              <Link to={"/admin"} className="nav-link">
                Admin Board
              </Link>
            </li>
          )}

          {currentUser && (
            <li className="nav-item">
              <Link to={"/user"} className="nav-link">
                User
              </Link>
            </li>,

            <li className="nav-item">
              <Link to={"/new_squad"} className="nav-link">
                New squad
              </Link>
            </li>
          )}
        </div>

        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/profile"} className="nav-link">
                Your Profile
              </Link>
            </li>
            <li className="nav-item float-lg-right">
              <a href="/login" className="nav-link" onClick={logOut}>
                LogOut
              </a>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/login"} className="nav-link">
                Login
              </Link>
            </li>

            <li className="nav-item">
              <Link to={"/register"} className="nav-link">
                Sign Up
              </Link>
            </li>
          </div>
        )}
        
{/* 
        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/profile"} className="nav-link">
                {currentUser.username}
              </Link>
            </li>
            <li className="nav-item">
              <a href="/login" className="nav-link" onClick={logOut}>
                LogOut
              </a>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/login"} className="nav-link">
                Login
              </Link>
            </li>

            <li className="nav-item">
              <Link to={"/register"} className="nav-link">
                Sign Up
              </Link>
            </li>
          </div>
        )} */}



      </nav>

      <div className="container mt-3">
        <Switch>
          <Route exact path={["/", "/home"]} component={Home} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/profile" component={Profile} />
          <Route path="/user" component={BoardUser} />
          <Route path="/squads" component={BoardSquads} />
          <Route path="/your_squads" component={BoardYourSquads} />
          <Route path="/admin" component={BoardAdmin} />
          <Route path="/new_squad" component={BoardAddNewSquad} />
        </Switch>
      </div>
    </div>
  );
};

export default App;
/*

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import Places from "./Places"
import Squads from "./Squads"
import Home from "./Home"
import './App.css';



 function App() {
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
        *//*}
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


export default App;
*/