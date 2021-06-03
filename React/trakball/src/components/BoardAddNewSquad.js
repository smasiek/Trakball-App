import React, { useState, useRef } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import SquadService from "../services/squad.service";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

const BoardAddNewSquad = (props) => {
  const form = useRef();
  const checkBtn = useRef();

  const [formData, setFormData] = useState({});
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const changeFormData = (e) =>{
    setFormData({
      ...formData,
      [e.target.name]:e.target.value
    })
  }

  const handleNewSquad = (e) => {
    e.preventDefault();

    setMessage("");
    setLoading(true);

    form.current.validateAll();

    if (checkBtn.current.context._errors.length === 0) {
      SquadService.publish(formData.placeName,formData.city,formData.street,
        formData.sport, formData.date, 
        formData.fee, formData.maxMembers).then(
        () => {
          props.history.push("/squads");
          window.location.reload();
        },
        (error) => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          setLoading(false);
          setMessage(resMessage);
        }
      );
    } else {
      setLoading(false);
    }
  };

  return (
    <div className="col-md-12">
      <div className="card card-container">
        {/* <img
          src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
          alt="profile-img"
          className="profile-img-card"
        /> */}

        <Form onSubmit={handleNewSquad} ref={form}>

        <div className="form-group">
            <label htmlFor="city">City</label>
            <Input
              type="text"
              className="form-control"
              name="city"
              value={formData.city}
              onChange={changeFormData}
              validations={[required]}
            />
          </div>

          <div className="form-group">
            <label htmlFor="street">Street</label>
            <Input
              type="text"
              className="form-control"
              name="street"
              value={formData.street}
              onChange={changeFormData}
              validations={[required]}
            />
          </div>

          <div className="form-group">
            <label htmlFor="placeName">Place name</label>
            <Input
              type="text"
              className="form-control"
              name="placeName"
              value={formData.placeName}
              onChange={changeFormData}
              validations={[required]}
            />
          </div>

          <div className="form-group">
            <label htmlFor="sport">Sport</label>
            <Input
              type="text"
              className="form-control"
              name="sport"
              value={formData.sport}
              onChange={changeFormData}
              validations={[required]}
            />
          </div>


            <div className="form-group">
            <label htmlFor="date">Date</label>
            <Input
              type="datetime-local"
              className="form-control"
              name="date"
              value={formData.date}
              onChange={changeFormData}
              validations={[required]}
            />
            </div>

            <div className="form-group">
            <label htmlFor="fee">Fee</label>
            <Input
              type="text"
              className="form-control"
              name="fee"
              value={formData.fee}
              onChange={changeFormData}
              validations={[required]}
            />
            </div>

            <div className="form-group">
            <label htmlFor="maxMembers">Max members</label>
            <Input
              type="number"
              className="form-control"
              name="maxMembers"
              value={formData.maxMembers}
              onChange={changeFormData}
              validations={[required]}
            />
            </div>

          <div className="form-group">
            <button className="btn btn-primary btn-block" disabled={loading}>
              {loading && (
                <span className="spinner-border spinner-border-sm"></span>
              )}
              <span>Publish</span>
            </button>
          </div>

          {message && (
            <div className="form-group">
              <div className="alert alert-danger" role="alert">
                {message}
              </div>
            </div>
          )}
          <CheckButton style={{ display: "none" }} ref={checkBtn} />
        </Form>
      </div>
    </div>
  );
};

export default BoardAddNewSquad;