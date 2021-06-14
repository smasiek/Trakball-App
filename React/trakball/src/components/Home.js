import React, { useState, useEffect } from "react";
import PlaceService from "../services/place.service";
import { MapContainer, TileLayer, Marker, Popup, useMap} from 'react-leaflet';
import Markers from "./Markers";
import L from "leaflet";
import "../assets/css/map.css";

const Home = () => {

  const [places, setPlaces] = useState([]);

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
        //setSearchResult(_content);
      }
    );
  }, []);


  const YourLocationMarker = ()=>{
    const [position, setPosition] = useState(null);
    const [bbox, setBbox] = useState([]);

    const map = useMap();

    useEffect(() => {
      map.locate().on("locationfound", function (e) {
        setPosition(e.latlng);
        map.flyTo(e.latlng, 12);
        const radius = 100;
        const circle = L.circle(e.latlng, radius);
        circle.addTo(map);
        setBbox(e.bounds.toBBoxString().split(","));
      });
    }, [map]);

    return position === null ? null : (
      <Marker position={position}>
        <Popup>
          You are here. <br />
          Map bbox: <br />
          <b>Southwest lng</b>: {bbox[0]} <br />
          <b>Southwest lat</b>: {bbox[1]} <br />
          <b>Northeast lng</b>: {bbox[2]} <br />
          <b>Northeast lat</b>: {bbox[3]}
        </Popup>
      </Marker>
    );
  }


  return (
    <div className="container">
      <header className="jumbotron" style={{ display: 'flex', justifyContent: 'center' }}>
       <h3>Map of places</h3>
      </header>
      <div className="map">

      <MapContainer center={[51.919437, 19.145136]} zoom={12} scrollWheelZoom={true}>
          <TileLayer
            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          <YourLocationMarker />
          <Markers places={places}/>
        </MapContainer>
      </div>

    </div>
  );
};

export default Home;