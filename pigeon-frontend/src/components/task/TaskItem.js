import {useState, useEffect, useRef} from 'react';
import React from 'react';
import api from '../../api/axiosConfig.js';
import {BsExclamationCircleFill} from 'react-icons/bs';
import {BsXSquareFill} from 'react-icons/bs';

// import '../style/components/ProjectItem.css';


function TaskItem({passedTask, onDelete}) {
    const [task, setTask] = useState(passedTask);
    // const [priorityIcon, setPriorityIcon] = useState("");
    const [completed, setCompleted] = useState(task.isCompleted);
    const [displayedDueDate, setDisplayedDueDate] = useState("");
    const jwt = localStorage.getItem("jwt");
    // const [projectDataFromList, setProjectDataFromList] = useState('');
    // get data from parent list
    // const handleGetProject = () => {
    //     setProjectDataFromList('this comes from the parent list');
    // };

    useEffect(() =>  {
        if (completed != task.isCompleted) {
            handleUpdateProject(task);
        }
    }, [completed]);

    useEffect(() => {
        handleChangeDisplayedDueDate(task.dueDate);
    }, [task]);

    const handleChangeDisplayedDueDate = (date) => {
        if (date != null && date != "") {
            let dateObj = new Date(Date.parse(date));
            let month = dateObj.getMonth() + 1;
            let day = dateObj.getDate();
            let year = dateObj.getFullYear();
            let dateFormatted = "Due " + month + "/" + day + "/" + year;
            setDisplayedDueDate(dateFormatted);
        } else {
            setDisplayedDueDate("");
        }
    }

    const setPriority = () => {
        switch(task.priority) {
            case 0: return "priority-none";
            case 1: return "priority-low";
            case 2: return "priority-normal";
            case 3: return "priority-high";
            case 4: return "priority-urgent";
            default: return "priority-urgent";
        }
    }
    
    let priorityClass = setPriority();

    const handleUpdateProject = async () => {
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
        <>
            <div className="task-priority-icon-container"><BsExclamationCircleFill className={`priority-icon ${priorityClass}`} /></div>
            <div className="task-completed-icon-container">
                <input type="checkbox" className="task-completed-checkbox" checked={completed} onChange={e => setCompleted(e.target.checked)} />
            </div>
            <div className='delete-task-button-container'>
                <button name='delete-task' className='delete-task-button' onClick={onDelete}><BsXSquareFill /></button>
            </div>
            <p className='task-name'>{task.taskName}</p>
            <div className='task-desc-container'>
                {task.description != "" ? 
                    <p className='task-desc'>{task.description}</p>
                : <p className='task-desc no-desc'>(No Description)</p>}
            </div>
            <div className='task-duedate-container'>
                {displayedDueDate != "" ? 
                    <p className='task-duedate'>{displayedDueDate}</p>
                : <p className='task-duedate no-duedate'>No Due Date</p>}
            </div>
        </>
    );
}

export default TaskItem;