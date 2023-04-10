import { ReactDOM } from 'react-dom/client';
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import SignupForm from './components/SignupForm.js';
import LoginForm from './components/LoginForm.js';

import logo from './logo.svg';
import './style/App.css';

import ProjectView from './pages/ProjectView';
import Dashboard from './pages/layouts/Dashboard.js';
import UserHome from './pages/UserHome.tsx';
import ProjectDetails from './pages/ProjectDetails.js';
import AuthenticationPage from './pages/layouts/AuthenticationPage.js';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route exact path="/" element={<Dashboard />}>
            <Route path="home" element={<UserHome />} />
            <Route path='projectview' element={<ProjectView />} />
            <Route path='taskview' />
            {/* <Route path="*" element={<NoPage />} /> */}
            <Route path='project/*' element={<ProjectDetails />} />
          </Route>
          <Route path="/auth" >
            <Route index={true} element={<AuthenticationPage />} />
            <Route path="login" element={<LoginForm />} />
            <Route Path="signup" element={<SignupForm />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;