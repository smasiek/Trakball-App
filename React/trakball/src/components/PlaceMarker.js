import React from "react";
import { Marker, Popup} from 'react-leaflet'
import { useHistory } from 'react-router-dom';

const PlaceMarker = (props) => {
    const history = useHistory();

    const handlePlaceSquads = (e)=>{
        history.push("/squads/" +props.place.id);
    }
    

    return (
        <> 
        <Marker position={[props.place.latitude, props.place.longitude]}>
            <Popup>
                <div className="popup">
                    <h6>{props.place.name}</h6>
                    <span>ul. {props.place.street}</span>
                    <button className="btn btn-warning" onClick={e=>handlePlaceSquads(e)}>Check squads</button>
                </div>
            </Popup>
        </Marker>
       </>
    )



}

export default PlaceMarker;