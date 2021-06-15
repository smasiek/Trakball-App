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
import BoardAddNewSquad from "./components/BoardAddNewSquad";
import EditProfile from "./components/EditProfile";

const App = () => {
  const [showYourSquadsBoard, setShowYourSquadsBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);
      setShowYourSquadsBoard(user.roles.includes("ROLE_USER"));
    }
  }, []);

  const logOut = () => {
    AuthService.logout();
  };

  return (
    <div>
      <nav className="navbar navbar-expand navbar-dark bg-success">
        <Link to={"/"} className="navbar-brand">
          <img src={logo} alt="Logo" style={{ height: '6vh' }} />
        </Link>

        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#basicExampleNav"
          aria-controls="basicExampleNav" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="basicExampleNav">

          <div className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link to={"/home"} className="nav-link">
                Home
              </Link>
            </li>


            <li className="nav-item">
              <Link to={"/squads"} className="nav-link">
                Squads
              </Link>
            </li>

            {showYourSquadsBoard && (
              <li className="nav-item">
                <Link to={"/your_squads"} className="nav-link">
                  Your Squads
                </Link>
              </li>
            )
            }

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
                  Log out
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

        </div>



      </nav>

      <div className="container mt-3">
        <Switch>
          <Route exact path={["/", "/home"]} component={Home} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/profile" component={Profile} />
          <Route exact path="/edit_profile" component={EditProfile} />
          <Route path="/user" component={BoardUser} />
          <Route path="/squads/:id" component={BoardSquads} />
          <Route path="/squads" component={BoardSquads} />
          <Route path="/your_squads" component={BoardYourSquads} />
          <Route path="/new_squad" component={BoardAddNewSquad} />
        </Switch>
      </div>
    </div>
  );
};

export default App;