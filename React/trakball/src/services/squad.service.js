import axios from "axios";
import userToken from "./user-token";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/squads/";

const getSquadsBoard = () => {
  return axios.get(API_URL + "all", { headers: authHeader() });
};

const getYourSquadsBoard = () => {
  return axios.get(API_URL + "user/all", { headers: authHeader(), params: {token:userToken()}});
};

const publish = (placeName,city,street,sport, date, fee, maxMembers) => {
  return axios.post(API_URL, {
    placeName,
    city,
    street,
    sport, 
    date, 
    fee, 
    maxMembers,
  },
  {headers:authHeader(),
    params:{token:userToken()}
  });
};



export default {
  getSquadsBoard,
  getYourSquadsBoard,
  publish
};