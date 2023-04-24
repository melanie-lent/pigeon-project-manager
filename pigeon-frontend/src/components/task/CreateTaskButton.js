import React from "react";
import { useState, useEffect } from "react";
import CreateTaskLightBox from "./CreateTaskLightBox";

const CreateTaskButton = ({onTaskCreated}) => {
    const [showLightBox, setShowLightBox]= useState(false);
    const [task, setTask] = useState({});

    useEffect(() => {
        onTaskCreated(task);
    }, [task]);

    const handleSetShowLightBox = () => {
        setShowLightBox(!showLightBox);
    }

    const addTask = (newTask) => {
        setTask(newTask);
    }

    return (
        <>
            <div className='task-item'>
                <div className='item add-task-caption' onClick={(e) => {
                    e.stopPropagation();
                    handleSetShowLightBox();
                }}>Add a task!</div>
            </div>
            <div id='lightbox-root'>
                {showLightBox && <CreateTaskLightBox onClose={handleSetShowLightBox} onAddTask={addTask} />}
            </div>
        </>
    );
}

export default CreateTaskButton;