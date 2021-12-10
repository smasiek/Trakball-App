import React from "react";
import {useHistory} from "react-router-dom";

const MessageView = (props) => {

    const history = useHistory();

    return (<div className={"alert mb-0 " + props.alert_type}
                 style={{display: "flex", flexDirection: "column", alignItems: "center"}}
                 role="alert">
        {props.message}
        <button className="btn btn-danger mt-3" onClick={() =>
            history.push("/login")}>Try to login!
        </button>
    </div>);
};

export default MessageView;