import {Outlet} from 'react-router-dom';
import React from 'react';
import Navigate from '../../components/Navigate.tsx';

const Layout = () => {
    return (
        <main>
            <Navigate />
            <Outlet />
        </main>
    )
}

export default Layout;