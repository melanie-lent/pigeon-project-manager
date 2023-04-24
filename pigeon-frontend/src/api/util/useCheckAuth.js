import {useEffect, useState} from "react";
import api from "../axiosConfig.js";

function useCheckAuth() {
    const jwt = localStorage.getItem("jwt");
    
    const doNotAuthenticated = () => {
        localStorage.removeItem("userId");
        localStorage.removeItem("jwt");
        setIsAuthenticated(false);
    }

    const [isAuthenticated, setIsAuthenticated] = useState(async () => {
        await api.get("http://127.0.0.1:8080/checkauth", {
        headers: {
            'Authorization': `${jwt}`
        }})
        .then((res) => 
            (res.status == 200) ? setIsAuthenticated(true) : doNotAuthenticated())
        });

    useEffect(() => {
    }, [isAuthenticated, setIsAuthenticated]);
    
    return isAuthenticated;
}

export default useCheckAuth;