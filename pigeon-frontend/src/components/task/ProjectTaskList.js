import api from '../../api/axiosConfig';
import {useState, useEffect} from 'react';
import Spinner from "react-bootstrap/Spinner";
import React from 'react';

import TaskItem from './TaskItem.js';
import CreateTaskButton from './CreateTaskButton.js';

// import '../style/components/TaskList.css';

const ProjectTaskList = (props) => {
    const [tasks, setTasks] = useState({});
    const [isLoadingTasks, setIsLoadingTasks] = useState(true);
    const jwt = localStorage.getItem("jwt");

    const getTasks = async () => {
        await api.get(`http://127.0.0.1:8080/project/tasks/${props.props}`, {
            headers: {
                'Authorization': `${jwt}`
            }
        })
        .then((res) => {
                setTasks(res.data);
                setIsLoadingTasks(false);
            }
        )
        .catch((e) => console.log(e));
    }

    const deleteTask = (e) => {
        tasks.remove(e.target.value)
    }

    const handleDeleteTask = async (task) => {
        // console.log("updated: ", newTask);
        await api.delete(`http://127.0.0.1:8080/task/${task.id}`, {
            headers: {
                'Authorization': `${jwt}`
            }
        }).then((res) => {
            if (res.status == 200) {
                deleteTask(res.data);
            }
        });
    }
    
    useEffect(() =>  {
        getTasks();
    }, []);

    return (
        <div className='container'>
            {isLoadingTasks && <div class="spinner-container"><Spinner animation="border" variant="light" className="task-list-spinner" /></div>}
            {Object.keys(tasks).map((task) => 
                <div className='task-item-container'>
                    <TaskItem props={tasks[task]} className='item' />
                    <div className='delete-task-button-container'>
                        <button name='delete-task' className='delete-task-button' onClick={handleDeleteTask(tasks[task])}>X</button>
                    </div>
                </div>
            )}
            <CreateTaskButton className='item' props={props.props} />
        </div>
    );   
}

export default ProjectTaskList;