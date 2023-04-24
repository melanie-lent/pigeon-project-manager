import React from "react";
import '../../style/Error.css';

const NotFound = () => {
    return (
        <div className="page-container">
            <h1 class="error-header">404 - Not Found</h1>
            <h3 class="error-message">Looks like this resource doesn't exist. Try something else or contact the dev.</h3>
        </div>
    );
}

export default NotFound;