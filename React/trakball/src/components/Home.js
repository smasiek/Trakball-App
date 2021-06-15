import React, { useState, useEffect } from "react";
import PlaceService from "../services/place.service";
import { MapContainer, TileLayer, Marker, Popup, useMap} from 'react-leaflet';
import Markers from "./Markers";  
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

    const map = useMap();

    useEffect(() => {
      map.locate().on("locationfound", function (e) {
        setPosition(e.latlng);
        map.flyTo(e.latlng, 12);
      });
    }, [map]);

    return position === null ? null : (
      <Marker position={position}>
        <Popup>
          You are here. <br />
        </Popup>
      </Marker>
    );
  }


  return (
    <div className="container">
      <header style={{ display: 'flex', justifyContent: 'center' }}>
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