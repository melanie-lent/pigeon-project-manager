import api from '../api/axiosConfig';
import {useState, useEffect} from 'react';
import React from 'react';

import '../style/components/ProjectItem.css';


function ProjectItem(props) {
    // const [project, setProject] = useState({});
    // const [projectDataFromList, setProjectDataFromList] = useState('');
    // get data from parent list
    // const handleGetProject = () => {
    //     setProjectDataFromList('this comes from the parent list');
    // };
    
    return (
        <div className='project-container'>
            <div className='project-item'>
                <img src="" className='project-icon'/>
                <p className='project-name'>{props.props.name}</p>
                {/* <p className='project'></p> */}
                <img src='' className='project-settings-icon'/>
            </div>
        </div>
    );
}

export default ProjectItem;