import React from "react";
import {Marker, Popup} from 'react-leaflet'
import {useHistory} from 'react-router-dom';
import PlaceService from "../services/place.service";

const PlaceMarker = (props) => {
    const history = useHistory();

    const handlePlaceSquads = () => {
        history.push("/squads/" + props.place.place_id);
    }

    const handleFollowPlace = () => {
        PlaceService.followPlace(props.place.place_id).then(
            () => {
                history.push("/your_places");
                window.location.reload();
            },
            (error) => {
                const message =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
            }
        );
    }

    return (
        <>
            <Marker position={[props.place.latitude, props.place.longitude]}>
                <Popup>
                    <div className="popup">
                        <h6>{props.place.name}</h6>
                        <span>ul. {props.place.street}</span>
                        <div>
                            <button className="btn btn-outline-success mr-2"
                                    onClick={() => handleFollowPlace()}>Follow
                            </button>
                            <button className="btn btn-warning" onClick={() => handlePlaceSquads()}>Check squads
                            </button>
                        </div>
                    </div>
                </Popup>
            </Marker>
        </>
    )
}

export default PlaceMarker;