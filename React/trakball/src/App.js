import React, { useState, useEffect } from "react";
import { Switch, Route } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { Nav, Navbar } from "react-bootstrap";
import "./assets/css/App.css";

import logo from './assets/img/Logo big.png';
import AuthService from "./services/auth.service";

import Login from "./components/Login";
import Register from "./components/Register";
import Home from "./components/Home";
import Profile from "./components/Profile";
import BoardSquads from "./components/BoardSquads";
import BoardYourSquads from "./components/BoardYourSquads";
import BoardAddNewSquad from "./components/BoardAddNewSquad";
import BoardYourPlaces from "./components/BoardYourPlaces";
import EditProfile from "./components/EditProfile";
import BoardAddNewPlace from "./components/BoardAddNewPlace";

const App = () => {
  const [showUserBoards, setShowYourSquadsBoard] = useState(false);
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
      <Navbar collapseOnSelect expand="lg" bg="success" variant="dark">

        <Navbar.Brand href={"/"}>
          <img src={logo} alt="Logo" style={{ height: '6vh' }} />
        </Navbar.Brand>

        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">

          <Nav className="mr-auto">

            <Nav.Link href={"/home"}>Home</Nav.Link>
            <Nav.Link href={"/squads"}>Squads</Nav.Link>
            {showUserBoards && (
            [<Nav.Link href={"/your_squads"}>Your squads</Nav.Link>,
            <Nav.Link href={"/your_places"}>Your places</Nav.Link>])
            }
            {currentUser && (
              <Nav.Link href={"/new_squad"}>New squad</Nav.Link>
            )}

            {currentUser ? ([
              <Nav.Link href={"/profile"}>Your profile</Nav.Link>,
              <Nav.Link href={"/login"} onClick={logOut}>Log out</Nav.Link>
            ]
            ) : (
              [<Nav.Link href={"/login"}>Login</Nav.Link>,
              <Nav.Link href={"/register"}>Sign up</Nav.Link>]
            )}
          </Nav>
        </Navbar.Collapse>

      </Navbar>

      <div className="container-xl mt-3">
        <Switch>
          <Route exact path={["/", "/home"]} component={Home} />
          <Route path="/home/:lat/:lng" component={Home} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/profile" component={Profile} />
          <Route exact path="/edit_profile" component={EditProfile} />
          <Route path="/squads/:id" component={BoardSquads} />
          <Route path="/squads" component={BoardSquads} />
          <Route path="/your_squads" component={BoardYourSquads} />
          <Route path="/your_places" component={BoardYourPlaces} />
          <Route path="/new_place" component={BoardAddNewPlace} />
          <Route path="/new_squad" component={BoardAddNewSquad} />
        </Switch>
      </div>
    </div>
  );
};

export default App;