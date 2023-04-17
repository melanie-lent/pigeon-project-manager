import React from "react";
import { useLocation, Navigate } from 'react-router-dom';
import useCheckAuth from "../../api/util/useCheckAuth.js";
import IntroPage from "../../pages/IntroPage.js";

const AuthDependent = () => {
    const isAuthenticated = useCheckAuth();
    const location = useLocation();
    const pathname = location.pathname;

    console.log(isAuthenticated);

    console.log(pathname)
    return (
        <>
            {/* {isAuthenticated == true ?
                console.log(isAuthenticated) : console.log(isAuthenticated)
            } */}
            {/* {isAuthenticated == true ? (
                pathname == "/" ? (
                    <Navigate replace={true} to="/home" />
                ): (
                    <></>
                )
            ) : (
                    pathname == "/" || pathname == "/signup" || pathname == "/login" ? (
                        <></>
                    ): (
                        <Navigate replace={true} to="/login" />
                    )
                )
            } */}
        </>
    )
}

export default AuthDependent;