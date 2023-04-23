import React, { useEffect } from "react";
import LightBox from "./CreateProjectLightBox";
import { useState } from "react";

const CreateProjectButton = ({onProjectCreated}) => {
    const [showLightBox, setShowLightBox]= useState(false);
    const [project, setProject] = useState({});

    useEffect(() => {
        onProjectCreated(project);
    }, [project]);

    const handleSetShowLightBox = () => {
        setShowLightBox(!showLightBox);
    }

    const addProject = (newProject) => {
        setProject(newProject);
    }

    return (
        <div className="project-container">
            <div className='project-item'>
                <div className='item add-project' onClick={(e) => {
                    e.stopPropagation();
                    handleSetShowLightBox();
                }}><p className="add-project-caption">Add a project!</p></div>
            </div>
            <div id='lightbox-root'>
                {showLightBox && <LightBox onClose={handleSetShowLightBox} onAddProject={addProject} />}
            </div>
        </div>
    );
}

export default CreateProjectButton;