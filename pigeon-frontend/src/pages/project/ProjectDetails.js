import React from 'react';
import api from '../../api/axiosConfig';
import { useParams } from 'react-router-dom';
import {useState, useEffect} from 'react';
import { useLocation } from 'react-router-dom';

const ProjectDetails = (props) => {
    const location = useLocation();
    const [project, setProject] = useState();
    const jwt = localStorage.getItem("jwt");
    
    console.log(props);

    const getProject = async () => {
        await api.get(`http://127.0.0.1:8080/project/${location.state.id}`, {
            headers: {
                'Authorization': `${jwt}`
            }
        })
            .then((res) => {
                    setProject(res.data);
                }
            )
            .catch((e) => console.log(e));
    }
    
    useEffect(() =>  {
        getProject();
    }, []);

    return (
        <div className='project-info-container'>
            <div className='project-name-header-container'>
                <h2 className='project-name-header'>{project.name}</h2>
            </div>
        </div>
    )
}

export default ProjectDetails;