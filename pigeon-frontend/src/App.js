import { ReactDOM } from 'react-dom/client';
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import SignupForm from './components/auth/SignupForm.js';
import LoginForm from './components/auth/LoginForm.js';

import logo from './logo.svg';
// import './style/App.css';

import ProjectView from './pages/project/ProjectView.js';
import TaskView from './pages/task/TaskView.js';
import Dashboard from './pages/Dashboard.js';
import UserHome from './pages/UserHome.js';
import ProjectDetails from './pages/project/ProjectDetails.js';

import NotFound from './pages/error handling/NotFound.js';
import IntroPage from './pages/IntroPage.js';

import './style/main.css';

const jwt = localStorage.getItem("jwt");


// {user.username ? ( <Route element={<Navigate to="/Home"/>}/>
// ) : (
//   <Route element={<Navigate to="/Login"/>}/>
  
  
// )}

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" >
            <Route exact path="" element={<IntroPage />} />
            <Route path="home" element={<UserHome />} />
            <Route path='projectview' element={<><Dashboard /><ProjectView /></>} />
            {/* <Route path='taskview' element={<><Dashboard /><TaskView /></>} /> */}
            <Route path='project' element={<Dashboard />}>
              <Route path=":id" element={<ProjectDetails />} />
            </Route>
            <Route path="login" element={<LoginForm />} />
            <Route Path="signup" element={<SignupForm />} />
            <Route path="*" element={<NotFound />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;