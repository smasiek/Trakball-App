import axios from "axios";
import authHeader from "./auth-header";
import userToken from "./user-token";

const API_URL = "http://localhost:8080/api/";

const getPublicContent = () => {
  return axios.get(API_URL + "all");
};

const getUserBoard = () => {
  return axios.get(API_URL + "user", { headers: authHeader() });
};

const getAdminBoard = () => {
  return axios.get(API_URL + "admin", { headers: authHeader() });
};


//TODO Stworzyc squad.service i tam adc metody do squadów, dodac akcje dodawania skladu do your squad, potem dodac okno z dodawaniem squadu ogolnie

export default {
  getPublicContent,
  getUserBoard,
  getAdminBoard,
};