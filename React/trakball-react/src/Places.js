import React, {Component} from 'react';
import Place from "./Place"


export default class Places extends Component {

  state =
  {
      data:[]
  }

  componentDidMount(){
    fetch('http://localhost:8080/places')
    .then(response=>response.json())
    .then(data=>{
      console.log(data);
      this.setState({data})
    });
  }

  render() {
        return (
          <div>
           {this.state.data.map(place=><Place info={place}/>)}
          </div>
        );
  }
}