import React, { useState, useEffect, useRef} from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import Squad from "./Squad"

import UserService from "../services/user.service";
import "../assets/css/squad.css";

const BoardYourSquads = () => {
  const form = useRef();
  const checkBtn = useRef();


  const [content, setContent] = useState([]);
  const [search, setSearch] = useState("");

  const [loading, setLoading] = useState(false);
  const [foundResults,setFound]=useState(false);

  useEffect(() => {
    UserService.getYourSquadsBoard().then(
      (response) => {
        setContent(response.data);

        if(response.data.length!==0)setFound(true);

      },
      (error) => {
        const _content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        setContent(_content);
        setFound(false);
      }
    );
  }, []);


  const changeSearchInput = (e) => {
    const search = e.target.value
    setSearch(search)
  }

  const handleSearch = (e) =>{

    setLoading(true);
    setFound(false);

    content.map((squad,index)=>{
      let show=false;
      if(squad.creator.userDetails===search) {show=true;setFound(true);}
      if(squad.sport===search) {show=true;setFound(true);}
      if(squad.place.name===search) {show=true;setFound(true);}
      if(squad.place.city===search) {show=true;setFound(true);}
      if(squad.place.street===search) {show=true;setFound(true);}

      if(show)
        document.getElementById(index).style.display = "block";
      else
      document.getElementById(index).style.display = "none";
    });
    
    setLoading(false);
  }

  return (
    <div class="squads-boxed">
      <div className="container-fluid">
        <div class="intro">
          <h2 class="text-center">Squads </h2>
          <p class="text-center">search for squads in your city</p>
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
          {content.map((squad,index) => <Squad info={squad} key={index} id={index}/>)}
        </div>
      </div>
    </div>

  );
};

export default BoardYourSquads;