import React from "react";
import AuthService from "../services/auth.service";
import { useHistory } from 'react-router-dom';
import def from '../assets/img/default.png';

const Profile = () => {
  const currentUser = AuthService.getCurrentUser();

  const history = useHistory();

  const handleEditProfile = () => {
    history.push("/edit_profile");
    window.location.reload();
  }

  return (
   <div className="squads-boxed" style={{ background: "white" }}>
     
      <div className="container-fluid">
        <div className="col-lg-12 item">
          <div className="box">
            <img className="rounded-circle user-photo" src={def} alt="creatorPhoto" />
            <h3>
              <strong>{currentUser.name} {currentUser.surname}</strong>
            </h3>
            <p>
              <strong>Email:</strong> {currentUser.email}
            </p>
            <p>
              <strong>Phone:</strong> {currentUser.phone}
            </p>

            <button className="btn btn-danger" onClick={handleEditProfile}>Edit profile</button>
          </div>
        </div>
      </div>
  </div>


  );
};

export default Profile;