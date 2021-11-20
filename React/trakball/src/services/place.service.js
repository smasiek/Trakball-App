import axios from "axios";
import authHeader from "./auth-header";
import userToken from "./user-token";

const API_URL = "http://localhost:8080/api/places/";

const getPlaces = () => {
    return axios.get(API_URL + "all", {headers: authHeader()});
};

const getPlaceRequests = () => {
    return axios.get(API_URL + "requests", {headers: authHeader()});
};

const removeRequest = (place_id) => {
    console.log(place_id)
    return axios.delete(API_URL + "requests", {headers: authHeader(), data: {placeRequestId: place_id}});
}

const approveRequest = (placeId) => {
    return axios.post(API_URL + "requests", {}, {headers: authHeader(), params: {placeRequestId: placeId}});
}

const getPlace = (id) => {
    return axios.get(API_URL, {headers: authHeader(), params: {id: id}});
};

const getPlacesFromCity = (city) => {
    return axios.get(API_URL + "city", {headers: authHeader(), params: {city: city}});
};

const newPlace = (name, street, city, latitude, longitude, postal_code) => {
    return axios.post(API_URL, {
            name: name,
            street: street,
            city: city,
            latitude: latitude,
            longitude: longitude,
            postal_code: postal_code
        },
        {
            headers: authHeader(),
            params: {token: userToken()}
        });
};

const followPlace = (placeId) => {
    return axios.post(API_URL + "follow", {}, {headers: authHeader(), params: {place_id: placeId}});
}

const unfollowPlace = (placeId) => {
    return axios.post(API_URL + "unfollow", {}, {headers: authHeader(), params: {place_id: placeId}});
}

const getCitiesList = (city, street, place) => {
    return axios.get(API_URL + "cities",
        {
            headers: authHeader(),
            params: {
                city: city,
                street: street,
                place: place
            }
        });
};


const getStreetsList = (city, street, place) => {
    return axios.get(API_URL + "streets",
        {
            headers: authHeader(),
            params: {
                city: city,
                street: street,
                place: place
            }
        });
};

const getPlacesList = (city, street, place) => {
    return axios.get(API_URL + "names",
        {
            headers: authHeader(),
            params: {
                city: city,
                street: street,
                place: place
            }
        });
};

/* eslint import/no-anonymous-default-export: [2, {"allowObject": true}] */
export default {
    getPlaces,
    getPlaceRequests,
    removeRequest,
    approveRequest,
    getPlace,
    getPlacesFromCity,
    newPlace,
    followPlace,
    unfollowPlace,
    getCitiesList,
    getStreetsList,
    getPlacesList
};