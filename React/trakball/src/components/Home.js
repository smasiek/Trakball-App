import React, {useEffect, useState} from "react";
import {renderToStaticMarkup} from 'react-dom/server';
import PlaceService from "../services/place.service";
import {MapContainer, Marker, Popup, TileLayer, useMap} from 'react-leaflet';
import Markers from "./Markers";
import "../assets/css/map.css";
import {useParams} from "react-router-dom";
import BoardAddNewPlace from "./BoardAddNewPlace";
import {divIcon} from "leaflet/dist/leaflet-src.esm";
import {FaMapMarkerAlt} from "react-icons/all";
import AlertTemplate from "./AlertTemplate";
import {positions, Provider} from "react-alert";

const Home = () => {
    const [places, setPlaces] = useState([]);
    const [message, setMessage] = useState("");
    const [isLatLngPassed, setLatLngPassed] = useState(false);
    const [addNewPlaceDisplay, setAddNewPlaceDisplay] = useState('none');

    const [newPlaceMarkers, setNewPlaceMarkers] = useState([]);

    const [newPlaceLatLng, setNewPlaceLatLng] = useState([])
    const [shouldLocateOnInit, setLocateLocateOnInit] = useState(true);

    const CENTER_OF_POLAND = [51.919437, 19.145136];

    const {lat, lng} = useParams();

    const options = {
        timeout: 5000,
        position: positions.BOTTOM_CENTER
    };

    useEffect(() => {
        PlaceService.getPlaces().then(
            (response) => {
                setPlaces(response.data);
                console.log(response.data);
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

    useEffect(() => {
        if (newPlaceLatLng && newPlaceLatLng.length === 2) {
            setNewPlaceMarkers([{'lat': newPlaceLatLng[1], 'lng': newPlaceLatLng[0]}])
        }
    }, [newPlaceLatLng])

    const setSearchResultLatLng = (latLng) => {
        setLocateLocateOnInit(false);
        if (latLng.length === 2) {
            setNewPlaceLatLng(latLng)
            setLatLngPassed(false);
        }
    }

    const YourLocationMarker = (props) => {
        const [position, setPosition] = useState(null);
        const map = useMap();
        const iconMarkup = renderToStaticMarkup(<FaMapMarkerAlt
            style={{width: '100%', height: '100%', color: "#28a745"}}/>);
        const customMarkerIcon = divIcon({
            iconSize: [25, 41],
            iconAnchor: [0, 40],
            popupAnchor: [13, -35],
            html: iconMarkup,
        });

        useEffect(() => {
            map.invalidateSize(true);
            if (props.shouldLocateOnInit) {
                map.locate().on("locationfound", function (e) {
                    setPosition(e.latlng);
                    let latLng;
                    if (props.latLngPassed) {
                        latLng = [props.lat, props.lng];
                    } else {
                        latLng = e.latlng;
                    }
                    map.flyTo(latLng, 12);
                });
                map.locate().on('locationerror', function () {
                    map.flyTo(CENTER_OF_POLAND, 12);
                });
            }
        }, [map, props.lat, props.latLngPassed, props.lng, props.shouldLocateOnInit]);

        return position === null ? null : (
            <Marker position={position} icon={customMarkerIcon}>
                <Popup>
                    You are here. <br/>
                </Popup>
            </Marker>
        );
    }

    const SearchResultMarker = (props) => {
        const [position, setPosition] = useState(props.position);
        const map = useMap();

        const iconMarkup = renderToStaticMarkup(<FaMapMarkerAlt
            style={{width: '100%', height: '100%', color: "#dc3545"}}/>);
        const customMarkerIcon = divIcon({
            iconSize: [25, 41],
            iconAnchor: [0, 40],
            popupAnchor: [13, -35],
            html: iconMarkup,
        });

        useEffect(() => {
            map.flyTo(position, 12);

        }, [map, position]);

        return position === null ? null : (
            <Marker position={position} icon={customMarkerIcon}>
                <Popup>
                    Search result. <br/>
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

    return (
        <div className={'home-container'}>
            <header style={{display: 'flex', justifyContent: 'center'}}>
                <h3>Map of places</h3>
            </header>

            <div className="map-container">
                <div className={"add-new-place"} style={{display: addNewPlaceDisplay}}>
                    <Provider template={AlertTemplate} {...options}>
                        <BoardAddNewPlace setNewPlaceLatLng={setSearchResultLatLng}
                                          setNewPlaceMarker={setNewPlaceMarkers}/>
                    </Provider>
                </div>
                <div className="map">

                    {message && (
                        <div className="form-group">
                            <div className="alert alert-danger" role="alert">
                                {message}
                            </div>
                        </div>
                    )}

                    <MapContainer center={CENTER_OF_POLAND} zoom={12} scrollWheelZoom={true}>
                        <TileLayer
                            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        />
                        <YourLocationMarker shouldLocateOnInit={shouldLocateOnInit} latLngPassed={isLatLngPassed}
                                            lat={lat} lng={lng}/>
                        <Markers places={places}/>

                        {newPlaceMarkers.length !== 0 && newPlaceMarkers.map(m => (
                            <SearchResultMarker //ref={newPlaceMarker}
                                position={[m['lat'], m['lng']]}>
                                <Popup>
                                    Search result.<br/>
                                </Popup>
                            </SearchResultMarker>
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