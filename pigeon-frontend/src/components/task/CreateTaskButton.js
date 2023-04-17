import React from "react";
import { useState } from "react";
import CreateTaskLightBox from "./CreateTaskLightBox";

const CreateTaskButton = (projectId) => {
    const [showLightBox, setShowLightBox]= useState(false);

    const handleSetShowLightBox = () => {
        setShowLightBox(!showLightBox);
    }

    return (
        <>
            <div className='task-item'>
                <div className='item add-task' onClick={(e) => {
                    e.stopPropagation();
                    handleSetShowLightBox();
                }}>Add a task!</div>
            </div>
            <div id='lightbox-root'>
                {showLightBox && <CreateTaskLightBox onClose={handleSetShowLightBox} />}
            </div>
        </>
    );
}

export default CreateTaskButton;