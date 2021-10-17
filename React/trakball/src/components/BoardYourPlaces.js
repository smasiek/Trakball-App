import React, {useEffect, useState} from "react";
import {positions, Provider} from "react-alert";
import AlertTemplate from "./AlertTemplate";
import Place from "./Place"

import UserService from "../services/user.service";
import "../assets/css/place.css";
import {Carousel} from '3d-react-carousal';

const BoardYourPlaces = () => {

    const [showError, setShowError] = useState(false);
    const [content, setContent] = useState([]);
    const [errorMessage, setErrorMessage] = useState("");

    const options = {
        timeout: 5000,
        position: positions.BOTTOM_CENTER
    };

    const NO_PLACE_MESSAGE = "You haven't followed any place 😓\nTry finding and following any place on map!";

    useEffect(() => {
        UserService.getYourPlacesBoard().then(
            (response) => {
                setContent(response.data);
                if (response.data.length === 0) {
                    setErrorMessage(NO_PLACE_MESSAGE)
                    setShowError(true);
                }
            },
            (error) => {
                const _content =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setShowError(true);
                setErrorMessage(_content);
            }
        );
    }, []);

    return (
        <div className="places-boxed">
            <div className="container-fluid">
                <div className="intro">
                    <h2 className="text-center">Your Places</h2>
                </div>
                <div className="row squads">
                    {showError && (
                        <div className="form-group">
                            <div className="alert alert-danger error" role="alert">
                                {errorMessage}
                            </div>
                        </div>
                    )}

                    <Carousel slides={content.map((place, index) =>
                        <Provider template={AlertTemplate} {...options}
                                  key={index}> <Place info={place}
                                                      board={"your_places"}
                                                      alt={index}/>
                        </Provider>)} autoplay={false} interval={1000}/>
                </div>
            </div>
        </div>
    );
};

export default BoardYourPlaces;