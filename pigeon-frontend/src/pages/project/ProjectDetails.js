import React from 'react';
import api from '../../api/axiosConfig';
import {useState, useEffect} from 'react';
import { useLocation } from 'react-router-dom';
import Spinner from "react-bootstrap/Spinner";

import ProjectTaskList from "../../components/task/ProjectTaskList.js";

const ProjectDetails = () => {
    const location = useLocation();
    const pathname = location.pathname;
    const id = pathname.split("/")[2];
    const [project, setProject] = useState({});
    const jwt = localStorage.getItem("jwt");
    const [isLoadingProjects, setIsLoadingProjects] = useState(true);

    const getProject = async () => {
        await api.get(`http://127.0.0.1:8080/project/${id}`, {
            headers: {
                'Authorization': `${jwt}`
            }
        })
            .then((res) => {
                    setProject(res.data);
                    setIsLoadingProjects(false);
                }
            )
            .catch((e) => console.log(e));
    }
    
    useEffect(() =>  {
        getProject();
    }, []);

    return (
        <div className='project-info-container'>
            {isLoadingProjects && <div class="spinner-container"><Spinner animation="border" variant="light" className="project-list-spinner" /></div>}
            <div className='project-name-header-container'>
                <h2 className='project-name-header'>{project.name}</h2>
            </div>
            <div className="project-description-container">
                <p>{project.description}</p>
            </div>
            <div className="task-list-container">
                <ProjectTaskList props={id} />
            </div>
        </div>
    )
}

export default ProjectDetails;