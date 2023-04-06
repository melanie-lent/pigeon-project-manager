import api from '../api/axiosConfig';
import {useState, useEffect} from 'react';
import React from 'react';
import ProjectItem from './ProjectItem';

const ProjectList = () => {
    const [projects, setProjects] = useState([]);

    const getProjects = async () => {
        const projects = await api.get('http://localhost:8080/project/all')
            .then((res) => {
                console.log(res);
                    setProjects(res.data);
                }
            )
            .catch((e) => console.log(e));
    }
    
    useEffect(() =>  {
        getProjects();
    }, [])
    return (
        <div className='project-list-container'>
            <div className='container'>
                {projects.map(project => 
                    <ProjectItem props={project} className='item'/>
                )}
            </div>
        </div>
    )
}

export default ProjectList;