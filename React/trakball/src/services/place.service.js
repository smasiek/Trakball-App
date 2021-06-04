import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/places/";

const getCitiesList = (city) => {
  return axios.get(API_URL + "cities", {
    city,
  },{ headers: authHeader()});
};

const getStreetsList = (city,street,place) => {
  return axios.get(API_URL + "streets", {
    city,
    street,
    place,
  },{ headers: authHeader()});
};

const getPlacesList = (city,street,place) => {
  return axios.get(API_URL + "names", {
    city,
    street,
    place,
  },{ headers: authHeader()});
};

export default {
  getCitiesList,
  getStreetsList,
  getPlacesList
};