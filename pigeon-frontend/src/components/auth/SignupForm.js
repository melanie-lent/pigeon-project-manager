import React, { useState, useEffect } from "react";
import api from '../../api/axiosConfig';
import { useNavigate } from "react-router-dom";
import { useLocalState } from "../../api/util/useLocalStorage";

import '../../style/Login.css';

const SignUpForm = () => {
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");

  const [error, setError] = useState("");

  const storedJwt = localStorage.getItem("jwt");
  const [jwt, setJwt] = useLocalState("", "jwt");
  const [storedId, setStoredId] = useLocalState("", "userId");
  
  const navigate = useNavigate();

  const handleLogin = async () => {
    await api.post('http://127.0.0.1:8080/login', {
      username: username,
      password: password
    })
    .then((res) => {
      if (res.status == 200 || res.data == {}) {
        setJwt(res.headers.get("authorization").replace(/['"]+/g, ''));
        setStoredId(res.data.replace(/['"]+/g, ''));
      } else {
        setError("Something went wrong while signing in. Please visit the login page and try again with your username and password.");
      }
    });
  }

  const handleSubmit = async () => {
    if (!username || !password || !confirmPassword || !email || !firstName || !lastName) {
      setError("Please fill out all the fields!");
    } else if (password != confirmPassword) {
      setError("Passwords do not match")
    } else {
      await api.post('http://127.0.0.1:8080/signup', {
        username: username,
        password: password,
        email: email,
        firstName: firstName,
        lastName: lastName
      })
      .then((res) => {
        if (res.status == 201) {
          handleLogin();
        } else {
          setError("There is already a user with that username or email.");
        }
      })
      .catch ((e) => {
        console.log(e);
        setError("Something's gone wrong. Please contact the developer or try again later.");
      }
      );
      // .error('Something\'s gone wrong. Please contact the developer or try again later.');
    }
  }

  const handleChangeUsername = (event) => {
    setUsername(event.target.value);
  }

  const handleChangePassword = (event) => {
    setPassword(event.target.value);
  }

  const handleChangeConfirmPassword = (event) => {
    setConfirmPassword(event.target.value);
  }

  const handleChangeFirstName = (event) => {
    setFirstName(event.target.value);
  }

  const handleChangeLastName = (event) => {
    setLastName(event.target.value);
  }

  const handleChangeEmail = (event) => {
    setEmail(event.target.value);
  }
  
  useEffect(() => {
    console.log(storedJwt, storedId);
    if (storedJwt != "" && storedId != "") {
      navigate("/projectview");
    }
  }, [storedJwt, storedId]);

  return (
    <>
    {storedJwt == "" || storedId == "" ? (
    <div className="signup-container">
      <div className="form-inline names multi-field">
          <input className="firstname-input subfield" name="firstname" type="text"placeholder="First Name" onChange={handleChangeFirstName}/>
          <input className="lastname-input subfield" name="lastname" type="text"placeholder="Last Name" onChange={handleChangeLastName}/>
      </div>
      <div className="form-row email-container">
        <input className="email-input" name="email" type="email"placeholder="Email" onChange={handleChangeEmail}/>
      </div>
      <div className="form-row username-container">
        <input className="username-input" name="username" type="text" placeholder="Username" onChange={handleChangeUsername} />
      </div>
      <div className="form-row password-and-confirm multi-field">
          <input className="password-input subfield" name="password" type="password"placeholder="Password" onChange={handleChangePassword}/>
          <input className="confirm-password-input subfield" name="confirm-password" type="password"placeholder="Confirm Password" onChange={handleChangeConfirmPassword}/>
      </div>
      <div className="form-row button-container">
        <button type="submit" name="submit" className="signup-submit" onClick={handleSubmit}>Sign up!</button>
      </div>
      {error && <div className="form-row error-container"><p className="error-text">{error}</p></div>}
    </div>
    ) : (
      <div className="signup-container"></div>
    )}
    </>
  );
}

export default SignUpForm;