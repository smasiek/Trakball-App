import React from "react";
import PlaceMarker from "./PlaceMarker";

const Markers = (props) => {

    return props.places.map(place => {
        return <PlaceMarker place={place} key={place.id} //handlePetView={props.handlePetView}
        />
        })

}

export default Markers;