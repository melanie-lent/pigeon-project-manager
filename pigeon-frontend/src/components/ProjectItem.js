// import api from '../api/axiosConfig';
import {useState} from 'react';
import React from 'react';
import {Link} from 'react-router-dom';

import '../style/components/ProjectItem.css';


function ProjectItem(props) {
    const [project, setProject] = useState(props.props);
    // const [projectDataFromList, setProjectDataFromList] = useState('');
    // get data from parent list
    // const handleGetProject = () => {
    //     setProjectDataFromList('this comes from the parent list');
    // };
    
    return (
        <div className='project-container'>
            <Link to=
                {{
                    pathname: `/project/${props.props.id}`,
                    state: props
                }} 
                params={project.id} className="current project-link"
            >
                <div className='project-item'>
                    <img src="" className='project-icon'/>
                    <p className='project-name'>{props.props.name}</p>
                    <p className='project-member-count'>{props.props.members.length} members</p>
                    <img src='' className='project-settings-icon'/>
                </div>
            </Link>
        </div>
    );
}

export default ProjectItem;