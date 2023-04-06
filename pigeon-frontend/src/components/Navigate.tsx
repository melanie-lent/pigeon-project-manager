import React from 'react';
import '../style/Navigate.css';

const Navigate = () => {
    return (
        <div className='navigate-container'>
            <nav className='navigate'>
                <div className='navigate-bg-gradient'>
                    <ul className='navigate-list'>
                        <li className='navigate-list-item'><a href="/taskview">My Tasks</a></li>
                        <li className='navigate-list-item'><a href="/projectview">My Projects</a></li>
                        <li className='navigate-list-item'><a href="/settings">Profile</a></li>
                    </ul>
                </div>
            </nav>
        </div>
    )
}

export default Navigate;