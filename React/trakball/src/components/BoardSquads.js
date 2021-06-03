import React, { useState, useEffect } from "react";
import Squad from "./Squad"

import SquadService from "../services/squad.service";
import "../assets/css/squad.css";

const BoardSquads = () => {
  const [content, setContent] = useState([]);

  useEffect(() => {
    SquadService.getSquadsBoard().then(
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
    <div className="squads-boxed">
      <div className="container-fluid">
        <div className="intro">
          <h2 className="text-center">Squads </h2>
          <p className="text-center">search for squads in your city</p>
        </div>

        <div className="row squads">
          {content.map(squad => <Squad info={squad} key={squad.squad_id}/>)}
        </div>
      </div>
    </div>

  );
};

export default BoardSquads;