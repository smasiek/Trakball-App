import defBuilding from '../assets/img/place1.jpg';
import PlaceService from "../services/place.service";
import {useAlert} from "react-alert";
import {useHistory} from 'react-router-dom';
import {FaMapMarkedAlt, GiCancel, IoPeople} from "react-icons/all";

const Place = (props) => {
    const history = useHistory();
    const alertReact = useAlert();

    const handleFollowPlace = () => {
        PlaceService.followPlace(props.info.place_id).then(
            () => {
                history.push("/your_places");
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
            }
        );
    }

    const handleShowOnMap = () => {
        history.push("/home/" + props.info.latitude + "/" + props.info.longitude);
        window.location.reload();
    }

    const handleUnfollowPlace = () => {
        PlaceService.unfollowPlace(props.info.place_id).then(
            () => {
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
            }
        );
    }
    const handlePlaceSquads = () => {
        history.push("/squads/" + props.info.place_id);
    }

    const Footer = () => {
        return <div className="place-footer">
            <h3 className="name">{props.info.name}</h3>
            <p>{props.info.street}, {props.info.city}</p>
            <div className="controls" style={{margin: 'auto'}}>
                <div>
                    <button onClick={() => handleUnfollowPlace()} style={{color: 'lightcoral'}}><GiCancel
                        style={{margin: '5px'}}/>Unfollow place
                    </button>
                    <button onClick={() => handleShowOnMap()}><FaMapMarkedAlt style={{margin: '5px'}}/>Show on map
                    </button>
                    <button onClick={() => handlePlaceSquads()}><IoPeople style={{margin: '5px'}}/>Show squads</button>
                </div>
            </div>
        </div>
    }

    return (
        <div className="item" key={props.info.place_id}>
            <img className="place-photo" src={defBuilding} alt="creatorPhoto"/>
            <Footer/>
        </div>
    );
};

export default Place