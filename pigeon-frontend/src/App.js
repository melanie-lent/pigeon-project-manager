import { ReactDOM } from 'react-dom/client';
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import SignupForm from './components/SignupForm.js';
import LoginForm from './components/LoginForm.js';

import logo from './logo.svg';
import './style/App.css';

import ProjectView from './pages/project/ProjectView.js';
import TaskView from './pages/task/TaskView.js';
import Dashboard from './pages/Dashboard.js';
import UserHome from './pages/UserHome.js';
import ProjectDetails from './pages/project/ProjectDetails.js';
import AuthenticationPage from './pages/AuthenticationPage.js';

import NotFound from './pages/error handling/NotFound.js';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route exact path="/" >
            <Route path="home" element={<UserHome />} />
            <Route path='projectview' element={<><Dashboard /><ProjectView /></>} />
            <Route path='taskview' element={<><Dashboard /><TaskView /></>} />
            <Route path='project' element={<Dashboard />}>
              <Route path=":id" element={<ProjectDetails />} />
            </Route>
            <Route path="auth" >
              <Route path="login" element={<LoginForm />} />
              <Route Path="signup" element={<SignupForm />} />
            </Route>
            <Route path="*" element={<NotFound />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;