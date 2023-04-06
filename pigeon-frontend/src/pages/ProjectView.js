import React from 'react';

import { BrowserRouter, Routes, Route } from 'react-router-dom';

// import Layout from '../pages/layouts/Layout';
import ProjectList from '../components/ProjectList.js';

const UserHome = () => {
    return (
        <div>
            <ProjectList />
        </div>
    )
}

export default UserHome;