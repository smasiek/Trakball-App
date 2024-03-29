import axios from "axios";
import userToken from "./user-token";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/squads/";

const getSquadsBoard = () => {
  return axios.get(API_URL + "all", { headers: authHeader() });
};

const getYourSquadsBoard = () => {
  return axios.get(API_URL + "user/all", { headers: authHeader() });
};

const getPlaceSquads = (placeId) => {
  return axios.get(API_URL + "place/all", { headers: authHeader(), params: {id: placeId} });
};

const joinSquad = (squadId) => {
  console.log(squadId);
  return axios.post(API_URL + "join",{}, { headers: authHeader(), params: {id: squadId}});
}

const leaveSquad = (squadId) => {
  console.log(squadId);
  return axios.post(API_URL + "leave",{}, { headers: authHeader(), params: {id: squadId}});
}

const deleteSquad = (squadId) => {
  console.log(squadId + 'asdas');
  return axios.delete(API_URL,{ headers: authHeader(), data: { id: squadId} });
}


const publish = (place,city,street,sport, date, fee, maxMembers) => {
  return axios.post(API_URL, {
    place,
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



/* eslint import/no-anonymous-default-export: [2, {"allowObject": true}] */
export default {
  getSquadsBoard,
  getYourSquadsBoard,
  getPlaceSquads,
  joinSquad,
  leaveSquad,
  deleteSquad,  
  publish
};