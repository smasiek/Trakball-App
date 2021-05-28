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

const getSquadsBoard = () => {
  return axios.get(API_URL + "squads/all", { headers: authHeader() });
};

const getYourSquadsBoard = () => {
  return axios.get(API_URL + "squads/user/all", { headers: authHeader(), params: {token:userToken()}});
};

const getAdminBoard = () => {
  return axios.get(API_URL + "admin", { headers: authHeader() });
};

export default {
  getPublicContent,
  getUserBoard,
  getSquadsBoard,
  getYourSquadsBoard,
  getAdminBoard,
};