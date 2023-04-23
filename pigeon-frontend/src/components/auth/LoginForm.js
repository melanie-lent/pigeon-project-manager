import React, { useState, useEffect } from "react";
import api from '../../api/axiosConfig';
import { useNavigate } from "react-router-dom";
import { useLocalState } from "../../api/util/useLocalStorage";

import '../../style/Login.css';

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [error, setError] = useState("");

  const [jwt, setJwt] = useLocalState("", "jwt");
  const [storedId, setStoredId] = useLocalState("", "userId");
  
  const navigate = useNavigate();

  const handleSubmit = async () => {
    if (!username || !password) {
      setError("Please enter username and password");
    } else {
      await api.post('http://127.0.0.1:8080/login', {
        username: username,
        password: password
      })
      .then((res) => {
        if (res.status == 200 || res.data == {}) {
          setJwt(res.headers.get("authorization").replace(/['"]+/g, ''));
          setStoredId(res.data.replace(/['"]+/g, ''));
        } else {
          console.log(res);
          setError('Please make sure you\'ve entered the correct username and password.');
        }
      })
      .catch ((e) => {
        console.log(e);
        setError('Please make sure you\'ve entered the correct username and password.');
      }
      );
      // .error('Something\'s gone wrong. Please contact the developer or try again later.');
    }
  }

  useEffect(() => {
    if (jwt != "" && storedId != "") {
      navigate("/projectview");
    }
  })

  const handleChangeUsername = (event) => {
    setUsername(event.target.value);
  }

  const handleChangePassword = (event) => {
    setPassword(event.target.value);
  }

  return (
    <>
    {jwt == "" || storedId == "" ? (
    <div className="login-container">
      <div className="form-row username-container">
        <input className="username-input" name="username" type="text" placeholder="Username" onChange={handleChangeUsername} />
      </div>
      <div className="form-row password-container">
        <input className="password-input" name="password" type="password"placeholder="Password" onChange={handleChangePassword}/>
      </div>
      <div className="form-row button-container">
        <button type="submit" name="submit" className="login-submit" onClick={handleSubmit}>Login</button>
      </div>
      {error && <div className="form-row error-container"><p className="error-text">{error}</p></div>}
    </div>
    ) : (
      <div className="login-container"></div>
    )}
    </>
  );

}

// interface LoginFormProps {
//   onSubmit: (username: string, password: string) => handleSubmit;
// }

// const LoginForm: React.FC<LoginFormProps> = ({ onSubmit }) => {
//   const [username, setUsername] = useState<string>("");
//   const [password, setPassword] = useState<string>("");
//   const [error, setError] = useState<string | null>(null);

//   const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
//     setUsername(event.target.value);
//   };

//   const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
//     setPassword(event.target.value);
//   };

//   const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
//     event.preventDefault();

//     if (!username || !password) {
//       setError("Please enter both username and password");
//       fetch('/login', {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json',
//         },
//         body: JSON.stringify({ username, password }),
//       })
//         .then((response) => {
//           // Handle response from server
//         })
//         .catch((error) => {
//           console.error('Error:', error);
//         });
//       return;
//     }

//     onSubmit(username, password);
//   };

//   return (
//     <form onSubmit={handleSubmit}>
//       <div>
//         <label htmlFor="username">Username</label>
//         <input
//           type="text"
//           id="username"
//           name="username"
//           value={username}
//           onChange={handleUsernameChange}
//         />
//       </div>
//       <div>
//         <label htmlFor="password">Password</label>
//         <input
//           type="password"
//           id="password"
//           name="password"
//           value={password}
//           onChange={handlePasswordChange}
//         />
//       </div>
//       {error && <div>{error}</div>}
//       <button type="submit">Login</button>
//     </form>
//   );
// };

export default LoginForm;
