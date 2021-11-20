import React, {useEffect, useState} from "react";
import "../assets/css/map.css";
import "../assets/css/admin_board.css";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import PlaceService from "../services/place.service";
import {TiTick, TiTimes} from "react-icons/all";
import SquadGenerator from "./SquadGenerator";
import AlertTemplate from "./AlertTemplate";
import {positions, Provider} from "react-alert";

const BoardAdmin = () => {

    const [placeRequests, setPlaceRequests] = useState([]);

    const options = {
        timeout: 5000,
        position: positions.BOTTOM_CENTER,
    };

    useEffect(() => {
        PlaceService.getPlaceRequests().then(
            (response) => {

                let requestsList = [];
                const parseFoundPlaces = (item, index) => {
                    let secondary = (item.name) + ' ' + ((item.street) ? item.street : '') +
                        ((item.city) ? item.city : '') + ' lat: ' + item.latitude + ' lng: ' + item.longitude
                    requestsList.push({
                        "index": index,
                        "primary": secondary,
                        "secondary": item.requester,
                        "coords": [item.latitude, item.longitude],
                        "place_request_id": item.place_request_id,
                    })
                }
                response.data.forEach(parseFoundPlaces)
                setPlaceRequests(requestsList);
            })
    }, [])

    const handleRemoveRequest = (index, id) => {
        setPlaceRequests(placeRequests.filter(item => item.index !== index))

        PlaceService.removeRequest(id).then(
            (response) => {
            },
            (error) => {
                /*                const _content =
                                    (error.response &&
                                        error.response.data &&
                                        error.response.data.message) ||
                                    error.message ||
                                    error.toString();*/
            }
        )
    }

    const handleApproveRequest = (index, id) => {
        setPlaceRequests(placeRequests.filter(item => item.index !== index))

        PlaceService.approveRequest(id).then(
            (response) => {
                // alertReact.show(response);
            },
            (error) => {
                const _content =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                //alertReact.show(_content);
            }
        )
    }

    return (
        <div className="admin-boxed">
            <div className="container-fluid">
                <div className="intro">
                    <h2 className="text-center">Admin board </h2>
                </div>
                <div className="row col-12">
                    <div className="item col-lg-6 col-md-6 col-sm-12">
                        <div className="box">
                            <div className="intro">
                                <h2 className="text-center">Place requests</h2>
                            </div>

                            <div className="place-requests">

                                <List dense sx={{width: '100%', bgcolor: 'background.paper'}}>
                                    {placeRequests.map(row => {
                                        const labelId = `checkbox-list-secondary-label-${row.index}`;

                                        return (

                                            <ListItem
                                                key={row.index}
                                                secondaryAction={
                                                    /*              TODO przyda se do wizualizacji na mapie ale to drugorzędne
                                                                                                           <Radio
                                                                                                            checked={selectedValue === row.index}
                                                                                                            onChange={()=>{setSelectedValue(row.index)}}
                                                                                                            value={'' + row.index}
                                                                                                            name="radio-buttons"
                                                                                                            inputProps={{'aria-label': row.index}}
                                                                                                        />*/
                                                    <>
                                                        <button className="btn btn-danger m-1"
                                                                onClick={() => handleRemoveRequest(row.index, row.place_request_id)}>
                                                            <TiTimes/>
                                                        </button>
                                                        <button className="btn btn-success"
                                                                onClick={() => handleApproveRequest(row.index, row.place_request_id)}>
                                                            <TiTick/>
                                                        </button>
                                                    </>

                                                }
                                                disablePadding>

                                                <ListItemButton>
                                                    <ListItemText id={labelId} primary={`${row.primary}`}
                                                                  secondary={`${row.secondary}`}/>
                                                </ListItemButton>
                                            </ListItem>

                                        );
                                    })}
                                </List>
                            </div>
                        </div>
                    </div>
                    <div className="item col-lg-6 col-md-6 col-sm-12">
                        <div className="box">
                            <div className="intro">
                                <h2 className="text-center">Place requests</h2>
                            </div>
                        </div>
                    </div>
                    <div className="item col-lg-6 col-md-6 col-sm-12">
                        <div className="box">
                            <Provider template={AlertTemplate} {...options}><SquadGenerator/></Provider>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};
export default BoardAdmin;