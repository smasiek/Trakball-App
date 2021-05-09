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
      <div className="col-sm-4 col-md-4 col-lg-4 item">

        <div className="box">
          <img class="rounded-circle" src={def} />
          <h3 className="name">{this.props.info.creatorName}</h3>
          <p className="sport">{this.props.info.sport}</p>
          <div className="description">
            {/* <p>ID: {this.props.info.id}</p> */}
            <p>Liczebność skladu: {this.props.info.maxMembers}</p>
          <p>Opłata: {this.props.info.fee}</p>
          <p>Miejsce: {this.props.info.placeName}</p>
          <p>Adres: {this.props.info.address}</p>
          <p>Data: {this.props.info.date}</p>
          </div>
          <div className="social"><a href="#"><i class="fa fa-facebook-official"></i></a><a href="#"><i class="fa fa-twitter"></i></a><a href="#"><i class="fa fa-instagram"></i></a></div>
        </div>
      </div>
    );
  }
}

export default Squad