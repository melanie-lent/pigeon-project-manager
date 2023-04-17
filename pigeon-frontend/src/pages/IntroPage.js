import React from "react";
import { Link } from "react-router-dom";
import "../style/IntroPage.css";


const IntroPage = () => {
    return (
        <div className="intro-page-container">
            <div className="intro-content-container">
                <div className="intro-header-container">
                    <h2 className="intro-header">Pigeon Project Manager</h2>
                </div>
                <div className="auth-button-container">
                    <Link to={{pathname: '/login'}}>
                        <button className="auth-button to-login">Sign in</button>
                    </Link>
                    <Link to={{pathname: '/signup'}}>
                        <button className="auth-button to-signup">Create an Account</button>
                    </Link>
                </div>
                <div className="intro-text-container">
                    <h3 className="intro-text small-header">Welcome to Pigeon!</h3>
                    <p className="intro-text">Pigeon is a project management application, delivering quick and simple solutions for keeping your goals organized.</p>
                    <p className="intro-text">Here, you aren't limited to one set of tasks. You can create as many different projects as you please and add descriptions to go with them. You can also add unlimited tasks, and add in details such as descriptions, priority, and due dates.</p>
                </div>
            </div>
        </div>
    )
}

export default IntroPage;