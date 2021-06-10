import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/places/";

const getCitiesList = (city,street,place) => {
  return axios.get(API_URL + "cities", 
  { headers: authHeader(),
    params: {
      city: city,
      street: street,
      place: place
    }
  });
};


/*
const getCitiesList = (city,street,place) => {
  return axios({
    url: API_URL + "cities",
    method: 'GET',
    data: {
      city: 'kr'
    },
    headers: authHeader()
  });
};
*/

const getStreetsList = (city,street,place) => {
  return axios.get(API_URL + "streets", 
  { headers: authHeader(),
    params: {
      city: city,
      street: street,
      place: place
    }
  });
};

const getPlacesList = (city,street,place) => {
  return axios.get(API_URL + "names", 
  { headers: authHeader(),
    params: {
      city: city,
      street: street,
      place: place
    }
  });
};

/* eslint import/no-anonymous-default-export: [2, {"allowObject": true}] */
export default {
  getCitiesList,
  getStreetsList,
  getPlacesList
};