import React, {useState, useEffect, useRef} from "react";
import PlaceService from "../services/place.service";
import {MapContainer, TileLayer, Marker, Popup, useMap} from 'react-leaflet';
import Markers from "./Markers";
import "../assets/css/map.css";
import {useParams} from "react-router-dom";
import BoardAddNewPlace from "./BoardAddNewPlace";

const Home = () => {
    const [places, setPlaces] = useState([]);
    const [message, setMessage] = useState("");
    const [isLatLngPassed, setLatLngPassed] = useState(false);
    const [addNewPlaceDisplay, setAddNewPlaceDisplay] = useState('none');

    const newPlaceLat = useRef(0);
    const newPlaceLng = useRef(0);
    const [newPlaceMarkers, setNewPlaceMarkers] = useState([]);

    const newPlaceMarker = useRef();
    const mapRef = useRef();

    const CENTER_OF_POLAND = [51.919437, 19.145136];

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
                let latLng;
                if (newPlaceMarkers.length !== 0) {
                    latLng=[newPlaceMarkers[0]['lat'],newPlaceMarkers[0]['lng']];
                } else if (props.latLngPassed) {
                    latLng = [lat, lng];
                } else {
                    latLng = e.latlng;
                }
                map.flyTo(latLng, 12);
            });

            map.locate().on('locationerror', function () {
                map.flyTo(CENTER_OF_POLAND, 12);
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

    const getOnClick = () => {
        if (addNewPlaceDisplay === 'none') {
            setAddNewPlaceDisplay('inherit')
        } else {
            setAddNewPlaceDisplay('none')
        }
    }

    const newPlaceMarkerRefresh = () => {
        setNewPlaceMarkers([{'lat': newPlaceLat.current, 'lng': newPlaceLng.current}])
    }

    return (
        <div className={'home-container'}>
            <header style={{display: 'flex', justifyContent: 'center'}}>
                <h3>Map of places</h3>
            </header>

            <div className="map-container">
                <div className={" card p-0 m-0 w-100 add-new-place"} style={{display: addNewPlaceDisplay}}>
                    <BoardAddNewPlace newPlaceLat={newPlaceLat} newPlaceLng={newPlaceLng}/>

                    <button className="btn btn-danger btn-block" onClick={newPlaceMarkerRefresh}>Show on map</button>
                </div>
                <div className="map">

                    {message && (
                        <div className="form-group">
                            <div className="alert alert-danger" role="alert">
                                {message}
                            </div>
                        </div>
                    )}

                    <MapContainer ref={mapRef} center={CENTER_OF_POLAND} zoom={12} scrollWheelZoom={true}>
                        <TileLayer
                            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        />
                        <YourLocationMarker latLngPassed={isLatLngPassed} lat={lat} lng={lng}/>
                        <Markers places={places}/>

                        <Marker ref={newPlaceMarker} position={[0, 0]}>
                            <Popup>
                                Search result.<br/>
                            </Popup>
                        </Marker>

                        {newPlaceMarkers.length !== 0 && newPlaceMarkers.map(m => (
                            <Marker ref={newPlaceMarker} position={[m['lat'], m['lng']]}>
                                <Popup>
                                    Search result.<br/>
                                </Popup>
                            </Marker>
                        ))}
                    </MapContainer>

                </div>
            </div>
            <h4>Didn't find your place? Try to <button className={'btn btn-warning'} onClick={getOnClick}>Add new sport
                center</button></h4>
        </div>
    );
};

export default Home;