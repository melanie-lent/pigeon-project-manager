import React from "react";

import '../../style/Error.css';

const Forbidden = () => {
    return (
        <div className="page-container">
            <h1 class="error-header">403 - Forbidden</h1>
            <h3 class="error-message">You don't have permission to access this.</h3>
        </div>
    );
}

export default Forbidden;