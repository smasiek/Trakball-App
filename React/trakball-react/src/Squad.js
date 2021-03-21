import React, {Component} from 'react';

class Squad extends Component {

  render() {
    return (
      <div style={{backgroundColor: 'red'}}>
        <p>-----------</p>
        <p>ID: {this.props.info.id}</p>
        <p>Twórca składu: {this.props.info.creatorName}</p>
        <p>Sport: {this.props.info.sport}</p>
        <p>Liczebność skladu: {this.props.info.maxMembers}</p>
        <p>Opłata: {this.props.info.fee}</p>
        <p>Miejsce: {this.props.info.placeName}</p>
        <p>Adres: {this.props.info.address}</p>
        <p>Data: {this.props.info.date}</p>
      </div>
    );
  }
}

export default Squad;
