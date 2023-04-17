import React from 'react';
import '../style/NavBar.css';
import {Link} from 'react-router-dom';


const NavBar = () => {
    return (
        <div className='navbar-container'>
            <nav className='navbar'>
                <div className='navbar-bg-gradient'>
                    <ul className='navbar-list'>
                        {/* <li className='navbar-list-item'><Link to="/taskview">My Tasks</Link></li> */}
                        <li className='navbar-list-item'><Link to="/projectview">My Projects</Link></li>
                        {/* <li className='navbar-list-item'><Link to="/settings">Profile</Link></li> */}
                        <li className='navbar-list-item'><Link to="/logout">Log Out</Link></li>
                    </ul>
                </div>
            </nav>
        </div>
    )
}

export default NavBar;