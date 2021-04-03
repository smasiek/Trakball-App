import React, {Component} from 'react';

class Place extends Component {

  render() {
    return (
      <div style={{backgroundColor: 'blue'}}>
        <p>-----------</p>
         <p>ID: {this.props.info.id}</p>
        <p>Nazwa: {this.props.info.name}</p>
        <p>Miasto: {this.props.info.city}</p>
        <p>Kod pocztowy: {this.props.info.postal_code}</p>
        <p>Ulica: {this.props.info.street}</p>
        <p>Lat: {this.props.info.latitude}</p>
        <p>Lng: {this.props.info.logitude}</p>
        <p>Zdjecie: {this.props.info.photo}</p>
      </div>
    );
  }
}

export default Place;
