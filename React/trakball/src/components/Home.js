import React, { useState, useEffect } from "react";

import UserService from "../services/user.service";
import map from '../assets/img/map.png';

const Home = () => {
  const [content, setContent] = useState("");

  useEffect(() => {
    UserService.getPublicContent().then(
      (response) => {
        setContent(response.data);
      },
      (error) => {
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();

        setContent(_content);
      }
    );
  }, []);

  return (
    <div className="container">
      <header className="jumbotron" style={{display:'flex',justifyContent:'center'}}>
        <img src={map} alt="map" />
      </header>
    </div>
  );
};

export default Home;