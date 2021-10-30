import React, {useState, useRef, useEffect} from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import Radio from '@mui/material/Radio';

import OpenRouteService from "../services/openroute.service";
import ListItemText from "@mui/material/ListItemText";

import "../assets/css/place.css";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

const BoardAddNewPlace = (props) => {
  const form = useRef();
  const checkBtn = useRef();

  const [formData, setFormData] = useState({});
  const [city, setCity] = useState("");
  const [citiesList, setCitiesList] = useState([]);

  const [street, setStreet] = useState("");
  const [streetsList, setStreetsList] = useState([]);

  const [place, setPlace] = useState("");
  const [placesList, setPlacesList] = useState([]);

  const [searchResult,setSearchResult] = useState([]);
  const [isResultFound,setIsResultFound] = useState(false);

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  useEffect(() =>{
    setIsResultFound(searchResult.length > 0);
    console.log(isResultFound);
  },[searchResult])

  const changeFormData = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
  }

  const changeCityInput = (e) => {
    const city = e.target.value
    setCity(city)
  }


  const changeStreetInput = (e) => {
    const street = e.target.value
    setStreet(street)
  }



  const changePlaceInput = (e) => {
    const place = e.target.value
    setPlace(place)

  }



  const isValid = () => {
    let isValid = true;

    let cityCheck = citiesList.filter((val) => val === city);
    let streetCheck = streetsList.filter((val) => val === street);
    let placeCheck = placesList.filter((val) => val === place);

    if (cityCheck.length === 0 && city) { isValid = false; document.getElementById("cityErr").style.display = "block"; }
    if (streetCheck.length === 0 && street) { isValid = false; document.getElementById("streetErr").style.display = "block"; }
    if (placeCheck.length === 0 && place) { isValid = false; document.getElementById("placeErr").style.display = "block"; }

    return isValid;
  }

  const handleNewSquad = (e) => {
    e.preventDefault();

    setMessage("");
    setLoading(true);

    if (!city && !street && !place){
      setLoading(false);
      return
    }

    OpenRouteService.getLatLngFromAddress(city,street,place).then(
          (response) => {
            let foundResult=[];
            const parseFoundPlaces = (item,index) => {
              let secondary =((item.properties.street)?item.properties.street +' ' :'')+
                  ((item.properties.housenumber)?item.properties.housenumber + ', ':'')+
                  ((item.properties.locality)?item.properties.locality:'')
              foundResult.push({"index":index,"primary":item.properties.name, "secondary":secondary ,"coords":item.geometry.coordinates})
            }
            response.data.features.forEach(parseFoundPlaces)
            setSearchResult(foundResult);
            console.log(isResultFound);
            setLoading(false);
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

  };

  const [selectedValue, setSelectedValue] = useState(0);


  //const [[newPlaceLat,newPlaceLng],setNewPlaceCoors] = useState([]);

  const handleChange = (event) => {
    setSelectedValue(parseInt(event.target.value));
    [props.newPlaceLng.current,props.newPlaceLat.current]=(searchResult.filter((place) => ''+place.index===event.target.value)[0].coords);
    // eslint-disable-next-line no-undef
    //props.newPlaceLat.current=1;
    console.log(props.newPlaceLat.current + ' ' + props.newPlaceLng.current);
  };



  return (
      <div style={{padding:'20px 40px'}}>

        <div className="intro">
          <h4 className="text-center">Add new place</h4>
        </div>
        <Form onSubmit={handleNewSquad} ref={form}>


          <div className="form-groups">
          <div className="form-group">
            <label htmlFor="city">City</label>
            <Input
              type="text"
              className="form-control"
              placeholder="type and choose from list..."
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

              style={{ display: 'none' }}
            />

            <div id="cityErr" className="alert alert-danger" role="alert" style={{ display: "none" }}>
              No such city in database
            </div>


          </div>

          <div className="form-group">
            <label htmlFor="street">Street</label>
            <Input
              type="text"
              className="form-control"
              placeholder="type and choose from list..."
              name="street"
              value={street}
              onChange={changeStreetInput}
              validations={[required]}
              autoComplete="new-password"
            />

            <div id="streetErr" className="alert alert-danger" role="alert" style={{ display: "none" }}>
              No such street in database
            </div>

          </div>

          <div className="form-group">
            <label htmlFor="placeName">Place name</label>
            <Input
              type="text"
              className="form-control"
              placeholder="type and choose from list..."
              name="placeName"
              value={place}
              onChange={changePlaceInput}
              validations={[required]}
              autoComplete="new-password"
            />

          </div>
          </div>
          <div className="form-group" style={{ marginBottom: 0 }}>
            <button className="btn btn-danger btn-block" disabled={loading}>
              {loading && (
                <span className="spinner-border spinner-border-sm"></span>
              )}
              <span>Find</span>
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
        <Form>

          {isResultFound && (
              <>
                <div className={"result-list"}>
                  <List dense sx={{ width: '100%', bgcolor: 'background.paper'}}>
                    {searchResult.map((row) => {
                      const labelId = `checkbox-list-secondary-label-${row.index}`;

                      return (
                          <ListItem
                              key={row.index}
                              secondaryAction={
                                <Radio
                                    checked={selectedValue === row.index}
                                    onChange={handleChange}
                                    value={''+row.index}
                                    name="radio-buttons"
                                    inputProps={{ 'aria-label': row.index }}
                                />
                              }
                              disablePadding
                          >
                            <ListItemButton>
                              <ListItemText id={labelId} primary={`${row.primary}`} secondary={`${row.secondary}`} />
                            </ListItemButton>
                          </ListItem>
                      );
                    })}
                  </List>

                </div>
                <div className="form-group" style={{ marginBottom: 0 }}>
                  <button className="btn btn-danger btn-block" disabled={loading}>
                    {loading && (
                        <span className="spinner-border spinner-border-sm"></span>
                    )}
                    <span>Send request</span>
                  </button>
                </div>
              </>

          )}
        </Form>
      </div>
  );
};

export default BoardAddNewPlace;