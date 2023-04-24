// import api from '../api/axiosConfig';
import {useState} from 'react';
import React from 'react';
import {Link} from 'react-router-dom';

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
                    pathname: `/project/${project.id}`,
                    state: project.id
                }} 
                params={project.id} className="current project-link"
            >
                <div className='project-item actual-project'>
                    {/* <img src="" className='project-icon'/> */}
                    <p className='project-name'>{project.name}</p>
                    <div className='project-desc-container'>
                        {project.description != "" ? 
                            <p className='project-desc'>{project.description}</p>
                        : <p className='project-desc no-desc'>(No Description)</p>}
                    </div>
                    {/* <p className='project-member-count'>{project.members.length} members</p> */}
                    {/* <img src='' className='project-settings-icon'/> */}
                </div>
            </Link>
        </div>
    );
}

export default ProjectItem;