import {Outlet} from 'react-router-dom';
import React from 'react';
import NavBar from '../components/NavBar.js';
import {useState, useEffect} from 'react';
import api from '../api/axiosConfig.js';
import { Navigate } from "react-router-dom";



const Layout = () => {
    const [auth, setAuth] = useState(true);
    // const getAuthenticated = async () => await api.get('http://127.0.0.1:8080/checkauth')
    // .then((res) => {
    //     console.log(auth, "hi");
    //     if (res.status == 200) {
    //         setAuth(true);
    //     }
    // })
    // .catch((e) => console.log(e));

    useEffect(() => {
        // getAuthenticated();
    }, []);
    
    return auth ? (
        <main>
            <NavBar />
            <Outlet />
        </main>
    ) : (
        <div>
            <Navigate replace to="/auth/login" />
        </div>
    )
}

export default Layout;