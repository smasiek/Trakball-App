import React, { useState, useEffect } from "react";
import Squad from "./Squad"

import UserService from "../services/user.service";
import "../assets/css/squad.css";
const BoardSquads = () => {
  const [content, setContent] = useState([]);

  useEffect(() => {
    UserService.getSquadsBoard().then(
      (response) => {
        setContent(response.data);
      },
      (error) => {
        const _content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        setContent(_content);
      }
    );
  }, []);

  return (
    <div class="squads-boxed">
      <div className="container-fluid">
        <div class="intro">
          <h2 class="text-center">Squads </h2>
          <p class="text-center">search for squads in your city</p>
        </div>

        <div class="row squads">
          {content.map(squad => <Squad info={squad} />)}
        </div>
      </div>
    </div>

  );
};

export default BoardSquads;