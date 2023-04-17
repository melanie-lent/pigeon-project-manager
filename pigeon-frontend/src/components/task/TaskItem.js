import {useState, useEffect, useRef} from 'react';
import React from 'react';
import api from '../../api/axiosConfig.js';

// import '../style/components/ProjectItem.css';


function TaskItem(props) {
    const [task, setTask] = useState(props.props);
    const [priorityStr, setPriorityStr] = useState(0);
    const [completed, setCompleted] = useState(task.isCompleted);
    const jwt = localStorage.getItem("jwt");
    // const [projectDataFromList, setProjectDataFromList] = useState('');
    // get data from parent list
    // const handleGetProject = () => {
    //     setProjectDataFromList('this comes from the parent list');
    // };

    useEffect(() =>  {
        var p = "None"; 
        switch(task.priority) {
            case 0: p = "None";
            case 1: p = "Low";
            case 2: p = "Normal";
            case 3: p = "High";
            case 4: p = "Urgent";
            default: p = "Urgent";
            setPriorityStr(p);
        }
        handleUpdateProject(task);
    }, [completed]);

    const handleUpdateProject = async () => {
        // console.log("updated: ", newTask);
        await api.put(`http://127.0.0.1:8080/task`, {
            id: task.id,
            createdBy: task.createdBy,
            projectId: task.projectId,
            taskName: task.taskName,
            description: task.description,
            priority: task.priority,
            dueDate: task.dueDate,
            lastEdited: new Date,
            createdOn: task.createdOn,
            isCompleted: completed
        },  {
            headers: {
                'Authorization': `${jwt}`
            }
        }).then((res) => {
            if (res.status == 200) {
              setTask(res.data);
            }
        });
    }
    return (
        <div className='task-item'>
            <div className="task-priority-icon-container">
                <p className="task-priority-label">Priority: {priorityStr}</p>
            </div>
            <div className="task-completed-icon-container">
                <input type="checkbox" className="task-completed-checkbox" checked={completed} onChange={e => setCompleted(e.target.checked)} />
            </div>
            <p className='task-name'>{task.taskName}</p>
            <div className='task-desc-container'>
                {task.description != "" ? 
                    <p className='task-desc'>{task.description}</p>
                : <p className='task-desc no-desc'>(No Description)</p>}
            </div>
            <div className="completed-icon-container">
                {task.iscompleted ? <p>done</p> : <p>not done</p>}
            </div>
        </div>
    );
}

export default TaskItem;