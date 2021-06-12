import React, { useState, useEffect, useRef} from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import Squad from "./Squad"
import { positions, Provider } from "react-alert";
import AlertTemplate from "react-alert-template-basic";

import SquadService from "../services/squad.service";
import "../assets/css/squad.css";

const BoardSquads = () => {
  const form = useRef();
  const checkBtn = useRef();

  const [content, setContent] = useState([]);
  const [searchResult, setSearchResult] = useState([]);
  const [search, setSearch] = useState("");

  const [loading, setLoading] = useState(false);
  const [foundResults,setFound]=useState(true);

  const options = {
    timeout: 2000,
    position: positions.BOTTOM_CENTER
  };


  useEffect(() => {
    SquadService.getSquadsBoard().then(
      (response) => {
        setContent(response.data);
        setSearchResult(response.data);
        if(response.data.length===0)setFound(false);

      },
      (error) => {
        const _content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        setFound(false);
        setSearchResult(_content);
      }
    );
  }, []);


  const changeSearchInput = (e) => {
    const search = e.target.value
    setSearch(search)
  }

  const filterSquads=(squad)=>{
    let match = false;
    match=(squad.creator.userDetails.name.toLowerCase().includes(search) ||
    squad.creator.userDetails.surname.toLowerCase().includes(search) ||
    (squad.creator.userDetails.name+' '+squad.creator.userDetails.surname).toLowerCase().includes(search) ||
    squad.sport.toLowerCase().includes(search) ||
    squad.place.name.toLowerCase().includes(search) ||
    squad.place.city.toLowerCase().includes(search) ||
    squad.place.street.toLowerCase().includes(search)
    )
    console.log(search);
    return match
  }

  const handleFilterSquads = (squad)=>{
    if(filterSquads(squad)){
      setFound(true);
      return true;
    }
    return false;
  }

  const handleSearch = (e) =>{
    e.preventDefault();

    setLoading(true);
    setFound(false);

    setSearchResult(
      content.filter((squad)=>{
        return handleFilterSquads(squad);
      })
    )

    setLoading(false);
  }

  return (
    <div className="squads-boxed">
      <div className="container-fluid">
        <div className="intro">
          <h2 className="text-center">Squads </h2>
          <p className="text-center">search for squads in your city</p>
        </div>

        <Form onSubmit={handleSearch} ref={form}>
        <div className="form-group">
          <Input
            placeholder="type to search..."
            type="text"
            className="form-control"
            name="search"
            value={search}
            onChange={changeSearchInput}
          />
        </div>


        <div className="form-group">
            <button className="btn btn-primary btn-block" disabled={loading}>
              {loading && (
                <span className="spinner-border spinner-border-sm"></span>
              )}
              <span>search</span>
            </button>
          </div>

          {!foundResults && (
            <div className="form-group">
              <div className="alert alert-danger" role="alert">
                There was no squad matching 😓
              </div>
            </div>
          )}
          <CheckButton style={{ display: "none" }} ref={checkBtn} />

          </Form>


        <div className="row squads">
          {searchResult.map((squad,index) => <Provider template={AlertTemplate} {...options} key={index} ><Squad info={squad} board={"squads"}/></Provider>)}
        </div>
      </div>
    </div>

  );
};

export default BoardSquads;