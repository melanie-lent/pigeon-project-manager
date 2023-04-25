import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const CheckAuth = () => {
    const navigate = useNavigate();
    const jwt = localStorage.getItem("jwt");

    useEffect(() => {

    axios.get("http://127.0.0.1:8080/checkauth", {
        headers: {
        'Authorization': `${jwt}`
        },
    })
    .then((res) => {
            if (res.status != 200) {
                localStorage.removeItem("jwt");
                localStorage.removeItem("userId");
                navigate("/projectview");
            }
            // navigate("/");
        }
    )
    .catch((e) => {
        navigate("/");
      });
  }, []);

  return null;
};

export default CheckAuth;
