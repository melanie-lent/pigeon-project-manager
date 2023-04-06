import React from "react";
import LoginForm from "../components/LoginForm";

const handleLoginFormSubmit = (username: string, password: string) => {
  // handle login logic here, e.g. send request to server
  console.log(`Logging in with username ${username} and password ${password}`);
};

const LoginPage = () => {
  return (
    <div>
      <h1>Login</h1>
      <LoginForm onSubmit={handleLoginFormSubmit} />
    </div>
  );
};

export default LoginPage;
