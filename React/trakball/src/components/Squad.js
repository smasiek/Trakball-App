import def from '../assets/img/default.png';
import { useAlert } from "react-alert";
import SquadService from "../services/squad.service";
import { useHistory } from 'react-router-dom';



const Squad = (props) => {

  //const [message, setMessage] = useState("");
  const alert = useAlert();

  const history = useHistory();

  const handleJoinSquad = () => {
    SquadService.joinSquad(props.info.squad_id).then(
      () => {
        
        /*const message =
          (response &&
            response.data &&
            response.data.message) ||
            response.message ||
          response.toString();
          alert.show(message);*/
        history.push("/your_squads");
        window.location.reload();
      },
      (error) => {
        const message =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
          alert.show(message);
        //setMessage(message);
      }
    );
  }

  const handleLeaveSquad = () => {
    SquadService.leaveSquad(props.info.squad_id).then(
      () => {
        //history.push("/your_squads");
        /*const message =
          (response &&
            response.data &&
            response.data.message) ||
            response.message ||
          response.toString();
          alert.show(message);*/
        window.location.reload();
        
      },
      (error) => {
        const message =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
          alert.show(message);
        //setMessage(message);
      }
    );
  }

  const UserDetails = (e) =>{
    if(e.userDetails){
      return <h3 className="name">{e.userDetails.name} {e.userDetails.surname}</h3>
    }
    return ;
  }

  const Footer = () => {
      return (props.board==="squads")?
      <div className="social"><button><i className="fa fa-facebook-official"></i>Text organizer</button><button onClick= {handleJoinSquad} ><i className="fa fa-twitter"></i>Join squad</button></div>
        :
        <div className="social"><button><i className="fa fa-facebook-official"></i>Text organizer</button><button onClick= {handleLeaveSquad} ><i className="fa fa-twitter"></i>Leave squad</button></div>
  }


    return (
      <div className="col-sm-4 col-md-4 col-lg-4 item" key={props.info.squad_id}>
        <div className="box">
          <img className="rounded-circle user-photo" src={def} alt="creatorPhoto"/>
          <UserDetails userDetails={props.info.creator.userDetails} />
          <p className="sport">{props.info.sport}</p>
          <div className="description">
          <p>Liczebność skladu: {props.info.maxMembers}</p>
          <p>Opłata: {props.info.fee}</p>
          <p>Miejsce: {props.info.place.name}</p>
          <p>Miasto: {props.info.place.city}</p>
          <p>Ulica: {props.info.place.street}</p>
          <p>Data: {props.info.date}</p>
          </div>
          <Footer />
        </div>
      </div>
    );
  };

export default Squad