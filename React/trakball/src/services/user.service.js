import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/user";

const getPublicContent = () => {
  return axios.get(API_URL + "all");
};

const getUserBoard = () => {
  return axios.get(API_URL + "user", { headers: authHeader() });
};

const getAdminBoard = () => {
  return axios.get(API_URL + "admin", { headers: authHeader() });
};

const editData = (email,password,name,surname, phone) => {

  return axios.put(API_URL,{
    email,
    password,
    name,
    surname, 
    phone, 
  },
  { headers: authHeader() }) 
  .then((response) => {
    if (response.data.accessToken) {
      localStorage.removeItem("user");
      localStorage.setItem("user", JSON.stringify(response.data));
    }
    return response.data;
  });;
};

/* eslint import/no-anonymous-default-export: [2, {"allowObject": true}] */
export default {
  getPublicContent,
  getUserBoard,
  getAdminBoard,
  editData
};