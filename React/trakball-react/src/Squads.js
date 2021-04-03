import React, {Component} from 'react';
import Squad from "./Squad"


export default class Squads extends Component {

  state =
  {
      data:[]
  }

  componentDidMount(){
    fetch('http://localhost:8080/squads')
    .then(response=>response.json())
    .then(data=>{
      console.log(data);
      this.setState({data})
    });
  }

  render() {
        return (
          <div>
           {this.state.data.map(squad=><Squad info={squad}/>)}
          </div>
        );
  }
}