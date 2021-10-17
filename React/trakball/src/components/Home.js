import React, {useState, useEffect} from "react";
import PlaceService from "../services/place.service";
import {MapContainer, TileLayer, Marker, Popup, useMap} from 'react-leaflet';
import Markers from "./Markers";
import "../assets/css/map.css";
import {useParams} from "react-router-dom";

const Home = () => {
    const [places, setPlaces] = useState([]);
    const [message, setMessage] = useState("");
    const [isLatLngPassed, setLatLngPassed] = useState(false);

    const {lat, lng} = useParams();

    useEffect(() => {
        PlaceService.getPlaces().then(
            (response) => {
                setPlaces(response.data);

            },
            (error) => {
                const _content =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
                setMessage(_content);
            }
        );
    }, []);

    useEffect(() => {
        if (lat && lng)
            setLatLngPassed(true);
    }, [lat, lng])

    const YourLocationMarker = (props) => {
        const [position, setPosition] = useState(null);
        const map = useMap();

        useEffect(() => {
            map.locate().on("locationfound", function (e) {
                setPosition(e.latlng);
                const latLng = props.latLngPassed ? [lat, lng] : e.latlng;
                map.flyTo(latLng, 12);
            });
        }, [map, props.latLngPassed]);

        return position === null ? null : (
            <Marker position={position}>
                <Popup>
                    You are here. <br/>
                </Popup>
            </Marker>
        );
    }

    return (
        <div className="container">
            <header style={{display: 'flex', justifyContent: 'center'}}>
                <h3>Map of places</h3>
            </header>
            {message && (
                <div className="form-group">
                    <div className="alert alert-danger" role="alert">
                        {message}
                    </div>
                </div>
            )}
            <div className="map">

                <MapContainer center={[51.919437, 19.145136]} zoom={12} scrollWheelZoom={true}>
                    <TileLayer
                        attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                    <YourLocationMarker latLngPassed={isLatLngPassed} lat={lat} lng={lng}/>
                    <Markers places={places}/>
                </MapContainer>
            </div>

        </div>
    );
};

export default Home;