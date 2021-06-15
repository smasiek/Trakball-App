import L from 'leaflet';

const IconPerson = new L.Icon({
    iconUrl: require('../assets/img/marker.png'),
    iconRetinaUrl: require('../assets/img/marker.png'),
    iconAnchor: null,
    popupAnchor: null,
    shadowUrl: null,
    shadowSize: null,
    shadowAnchor: null,
    iconSize: new L.Point(60, 75),
    className: 'leaflet-div-icon'
});

export { IconPerson };