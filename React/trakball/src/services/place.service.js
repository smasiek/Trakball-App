import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/places/";

const getPlaces = () => {
    return axios.get(API_URL + "all", {headers: authHeader()});
};

const getPlace = (id) => {
    return axios.get(API_URL, {headers: authHeader(), params: {id: id}});
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
    getPlace,
    followPlace,
    unfollowPlace,
    getCitiesList,
    getStreetsList,
    getPlacesList
};