import React from "react";
import '../../style/Error.css';

const Unauthorized = () => {
    return (
        <div className="page-container">
            <h1 class="error-header">401 - Unauthorized</h1>
            <h3 class="error-message">You aren't authenticated properly. Log in, then try again.</h3>
        </div>
    );
}

export default Unauthorized;