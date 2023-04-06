import { ReactDOM } from 'react-dom/client';
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import SignupForm from './components/SignupForm.tsx';
import LoginForm from './components/LoginForm.tsx';

import logo from './logo.svg';
import './style/App.css';

import ProjectView from './pages/ProjectView';
import Layout from './pages/layouts/Layout.tsx';
import UserHome from './pages/UserHome.tsx';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
            {/* <Route index element={<Home />} /> */}
            <Route path="login" element={<LoginForm />} />
            <Route Path="signup" element={<SignupForm />} />
            <Route path='home' element={<UserHome />} />
            <Route path='projectview' element={<ProjectView />} />
            <Route path='taskview' />
            {/* <Route path="*" element={<NoPage />} /> */}
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;