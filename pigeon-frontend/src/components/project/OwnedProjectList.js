import api from '../../api/axiosConfig';
import {useState, useEffect} from 'react';
import React from 'react';
import ProjectItem from './ProjectItem';
import CreateProjectButton from './CreateProjectButton';

import Spinner from "react-bootstrap/Spinner";

import '../../style/components/ProjectList.css';

const OwnedProjectList = () => {
    const [ownedProjects, setOwnedProjects] = useState([]);
    const jwt = localStorage.getItem("jwt");
    const userId = localStorage.getItem("userId").replace(/['"]+/g, '');
    const [isLoading, setIsLoading] = useState(true);

    const getOwnedProjects = async () => {
        await api.get(`http://127.0.0.1:8080/user/ownedprojects/${userId}`, {
            headers: {
                'Authorization': `${jwt}`
            }
        })
            .then((res) => {
                    setOwnedProjects(res.data);
                    setIsLoading(false);
                }
            )
            .catch((e) => console.log(e));
    }
    
    useEffect(() =>  {
        getOwnedProjects();
    }, []);

    const handleProjectCreated = (project) => {
        setOwnedProjects([...ownedProjects, project]);
    }

    return (
        <div className='project-list-container'>
            <div className='container'>
                { isLoading ? (
                    <div class="spinner-container"><Spinner animation="border" variant="light" className="project-list-spinner" /></div>
                ) :
                    <>
                        {Object.keys(ownedProjects).map((project) =>
                                ownedProjects[project].id != null ? 
                                    <ProjectItem props={ownedProjects[project]} className='item'/>
                                : <></>
                        )}
                        <CreateProjectButton className='item' onProjectCreated={handleProjectCreated} />
                    </>
                }
            </div>
        </div>
    );   
}

export default OwnedProjectList;