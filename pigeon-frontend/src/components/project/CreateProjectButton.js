import React from "react";
import LightBox from "./CreateProjectLightBox";
import { useState } from "react";

const CreateProjectButton = () => {
    const [showLightBox, setShowLightBox]= useState(false);

    const handleSetShowLightBox = () => {
        setShowLightBox(!showLightBox);
    }

    return (
        <div className="project-container">
            <div className='project-item'>
                <div className='item add-project' onClick={(e) => {
                    e.stopPropagation();
                    handleSetShowLightBox();
                }}><p>Add a project!</p></div>
            </div>
            <div id='lightbox-root'>
                {showLightBox && <LightBox onClose={handleSetShowLightBox} />}
            </div>
        </div>
    );
}

export default CreateProjectButton;