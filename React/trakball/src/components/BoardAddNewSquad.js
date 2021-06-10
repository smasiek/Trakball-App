import React, { useState, useRef } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import SquadService from "../services/squad.service";
import PlaceService from "../services/place.service";

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
  const [city, setCity] = useState("");
  const [citiesList, setCitiesList] = useState([]);
  
  const [street, setStreet] = useState("");
  const [streetsList, setStreetsList] = useState([]);
  
  const [place, setPlace] = useState("");
  const [placesList, setPlacesList] = useState([]);

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const changeFormData = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
  }

  const changeCityInput = (e) => {
    const city = e.target.value
    setCity(city)
    document.getElementById("cityErr").style.display = "none";
    handleCitiesInputChange()
  }

  const handleCitiesInputChange = () => {
    if (city && city.length > 1) {
      if (city.length % 2 === 0) {
        fetchCitiesList()
      }
    }
  }

  const fetchCitiesList = () => {
    PlaceService.getCitiesList(city,street,place).then(
      (response) => {
        setCitiesList(response.data);
      },
      (error) => {
        const _content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        setCitiesList(_content);
      }
    );
  }

  const CitySuggestions = (e) => {
    const options = e.results.map((r,index) => (
       <option value={r} key={index}/>
    ))
    return <datalist id="cities">{options}</datalist>
  }

  
  const changeStreetInput = (e) => {
    const street = e.target.value
    setStreet(street)
    
    document.getElementById("streetErr").style.display = "none";
    handleStreetsInputChange(e)
  }

  const handleStreetsInputChange = (e) => {
    if (e.target.value && e.target.value.length > 1) {
      if (e.target.value.length % 2 === 0) {
        fetchStreetsList()
      }
    }
  }

  const fetchStreetsList = () => {
    PlaceService.getStreetsList(city,street,place).then(
      (response) => {
        setStreetsList(response.data);
      },
      (error) => {
        const _content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        setStreetsList(_content);
      }
    );
  }

  const StreetSuggestions = (e) => {
    const options = e.results.map((r,index) => (
       <option value={r} key={index}/>
    ))
    return <datalist id="streets">{options}</datalist>
  }


  const changePlaceInput = (e) => {
    const place = e.target.value
    setPlace(place)
    
    document.getElementById("placeErr").style.display = "none";
    handlePlacesInputChange(e)
  }

  const handlePlacesInputChange = (e) => {
    if (e.target.value && e.target.value.length > 1) {
      if (e.target.value.length % 2 === 0) {
        fetchPlacesList()
      }
    }
  }

  const fetchPlacesList = () => {
    PlaceService.getPlacesList(city,street,place).then(
      (response) => {
        setPlacesList(response.data);
      },
      (error) => {
        const _content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

          setPlacesList(_content);
      }
    );
  }

  const PlaceSuggestions = (e) => {
    const options = e.results.map((r,index) => (
       <option value={r} key={index}/>
    ))
    return <datalist id="places">{options}</datalist>
  }


  const handleNewSquad = (e) => {
    e.preventDefault();

    setMessage("");
    setLoading(true);

    let cityCheck = citiesList.filter((val) => val===city);
    let streetCheck= streetsList.filter((val) => val===street);
    let placeCheck= placesList.filter((val) => val===place);
    
    let noErrorFound=true;

    if(cityCheck.length===0 && city){noErrorFound=false;document.getElementById("cityErr").style.display = "block";}
    if(streetCheck.length===0 && street){noErrorFound=false;document.getElementById("streetErr").style.display = "block";}
    if(placeCheck.length===0 && place){noErrorFound=false;document.getElementById("placeErr").style.display = "block";}

    form.current.validateAll();

    if (checkBtn.current.context._errors.length === 0 && noErrorFound) {
      SquadService.publish(place, city, street,
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
              value={city}
              list="cities"
              onChange={changeCityInput}
              autoComplete="new-password"
              validations={[required]}
            />

            <Input
              type="text"
              className="form-control"
              value={city}
              list="cities"
              
              style={{display: 'none'}}
            />

            <CitySuggestions results={citiesList} />
            <div id="cityErr" className="alert alert-danger" role="alert" style={{display:"none"}}>
        No such city in database
      </div>

            
          </div>

          <div className="form-group">
            <label htmlFor="street">Street</label>
            <Input
              type="text"
              className="form-control"
              name="street"
              value={street}
              list="streets"
              onChange={changeStreetInput}
              validations={[required]}
              autoComplete="new-password"
            />

<div id="streetErr" className="alert alert-danger" role="alert" style={{display:"none"}}>
        No such street in database
      </div>

          </div>

          <StreetSuggestions results={streetsList} />

          <div className="form-group">
            <label htmlFor="placeName">Place name</label>
            <Input
              type="text"
              className="form-control"
              name="placeName"
              value={place}
              list="places"
              onChange={changePlaceInput}
              validations={[required]}
              autoComplete="new-password"
            />

<div id="placeErr" className="alert alert-danger" role="alert" style={{display:"none"}}>
        No such place in database
      </div>

          </div>
          
          <PlaceSuggestions results={placesList} />

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

