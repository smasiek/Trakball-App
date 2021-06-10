import React, { Component } from 'react';
import def from '../assets/img/default.png';


class Squad extends Component {
  // constructor(){
  //   super();
  //   this.state={
  //     id:"not yet gotten"
  //   }
  // }
  // componentDidMount(){
  //   fetch('http://localhost:8080/squads')
  //   .then(response=>response.json())
  //   .then(response=>{
  //     this.setState({
  //       id:response[0].id
  //     }
  //     )
  //   }
  //    );
  // }

  render() {
    return (
      <div className="col-sm-4 col-md-4 col-lg-4 item" key={this.props.info.squad_id}>

        <div className="box">
          <img className="rounded-circle user-photo" src={def} alt="creatorPhoto"/>
          <ShowUserDetails userDetails={this.props.info.creator.userDetails} />
          <p className="sport">{this.props.info.sport}</p>
          <div className="description">
            {/* <p>ID: {this.props.info.id}</p> */}
          <p>Liczebność skladu: {this.props.info.maxMembers}</p>
          <p>Opłata: {this.props.info.fee}</p>
          <p>Miejsce: {this.props.info.place.name}</p>
          <p>Miasto: {this.props.info.place.city}</p>
          <p>Ulica: {this.props.info.place.street}</p>
          <p>Data: {this.props.info.date}</p>
          </div>
          <div className="social"><a href="#"><i className="fa fa-facebook-official"></i>Text organizer</a><a href="#"><i className="fa fa-twitter"></i>Join squad</a></div>
        </div>
      </div>
    );
  }
}
function ShowUserDetails(props){
    if(props.userDetails){
      return <h3 className="name">{props.userDetails.name} {props.userDetails.surname}</h3>
    }
    return ;
}

export default Squad