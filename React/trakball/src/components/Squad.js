import def from '../assets/img/default.png';
import { useAlert} from "react-alert";
import SquadService from "../services/squad.service";
import { useHistory } from 'react-router-dom';
import { TiUserAdd, TiContacts,TiUserDelete} from "react-icons/ti";
import { RiDeleteBin5Fill } from "react-icons/ri";



const Squad = (props) => {

  //const [message, setMessage] = useState("");
  const alertReact = useAlert();

  const history = useHistory();

  const handleTextOrganizer = () =>{
    let creator = props.info.creator
    let phone =creator.userDetails.phone?creator.userDetails.phone:'-'
    let info = 'Organiser: ' + creator.userDetails.name +' ' + creator.userDetails.surname
      +' \nPhone: ' + phone + ' \nE-mail: ' + creator.email;
    alert(info);
  }

  const handleJoinSquad = () => {
    SquadService.joinSquad(props.info.squad_id).then(
      () => {
        
        /*const message =
          (response &&
            response.data &&
            response.data.message) ||
            response.message ||
          response.toString();
          alertReact.show(message);*/
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
          alertReact.show(message);
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
          alertReact.show(message);*/
        window.location.reload();
        
      },
      (error) => {
        const message =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
          alertReact.show(message);
        //setMessage(message);
      }
    );
  }


  const handleDeleteSquad = () => {
    SquadService.deleteSquad(props.info.squad_id).then(
      () => {
        //history.push("/your_squads");
        /*const message =
          (response &&
            response.data &&
            response.data.message) ||
            response.message ||
          response.toString();
          alertReact.show(message);*/
        window.location.reload();
        
      },
      (error) => {
        const message =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
          alertReact.show(message);
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
      return <div className="row">
<div className="social">
{(props.board==="squads")? 
      <div><button onClick= {handleTextOrganizer} ><TiContacts style={{margin:'5px'}}/>Text organizer</button><button onClick= {handleJoinSquad} ><TiUserAdd  style={{margin:'5px'}}/>Join squad</button></div>
        :
        <div><button onClick= {handleTextOrganizer} ><TiContacts style={{margin:'5px'}}/>Text organizer</button><button onClick= {handleLeaveSquad} ><TiUserDelete style={{margin:'5px'}}/>Leave squad</button></div>
  }
        {props.mod && (
            <button onClick= {handleDeleteSquad}  style={{color:'lightcoral'}}><RiDeleteBin5Fill style={{margin:'5px'}}/>Delete</button>
        )
        }
        </div>
      </div>
  }


    return (
      <div className="col-sm-12 col-md-6 col-lg-4 item" key={props.info.squad_id}>
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