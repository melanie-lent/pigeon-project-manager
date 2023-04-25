import api from '../../api/axiosConfig';
import {useState, useEffect} from 'react';
import Spinner from "react-bootstrap/Spinner";
import React from 'react';

import TaskItem from './TaskItem.js';
import CreateTaskButton from './CreateTaskButton.js';

import '../../style/components/TaskList.css';

const ProjectTaskList = (props) => {
    const [tasks, setTasks] = useState([]);
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
    
    useEffect(() =>  {
        getTasks();
    }, []);

    const handleTaskCreated = (task) => {
        setTasks([...tasks, task]);
    }

    const handleDeleteTask = async (index, id) => {
        await api.delete(`http://127.0.0.1:8080/task/${id}`, {
            headers: {
                'Authorization': `${jwt}`
            }
        }).then(
            removeTaskFromList(index)
        ).error((e) => {
            console.log(e);
        });
      };

      

    const removeTaskFromList = (index) => {
        console.log("removing task at index", index);
        setTasks((prevTasks) => {
          const newTasks = [...prevTasks];
          newTasks.splice(index, 1);
          return newTasks;
        });
      };

    return (
        <>
            <div className="task-list-header-container">
                <h3 className='task-list-header'>To Do</h3>
            </div>
            
            <div className='container'>
                {isLoadingTasks && <div class="spinner-container"><Spinner animation="border" variant="light" className="task-list-spinner" /></div>}
                {tasks.map((task, index) => 
                    <>
                        {(!task.isCompleted && task.taskName != null) ? (
                            <div className='task-item-container'>
                                <div className='task-item'>
                                    <TaskItem passedTask={task} className='item' onDelete={() => handleDeleteTask(index, task.id)} />
                                </div>
                            </div>
                        ) : (
                            <></>
                        )} 
                    </>
                )}
                {!isLoadingTasks && <CreateTaskButton className='item' onTaskCreated={handleTaskCreated} />}
            </div>
            <div className="task-list-header-container">
                <h3 className='task-list-header'>Completed</h3>
            </div>
            <div className='container'>
                {isLoadingTasks && <div class="spinner-container"><Spinner animation="border" variant="light" className="task-list-spinner" /></div>}
                {tasks.map((task, index) =>
                    task.id != null ?
                        <>
                            {task.isCompleted ? (
                                <div className='task-item-container'>
                                    <div className='task-item'>
                                        <TaskItem passedTask={task} className='item' onDelete={() => handleDeleteTask(index, task.id)} />
                                    </div>
                                </div>
                            ) : (
                                <></>
                            )}
                        </>
                    : <></>
                )}
            </div>
        </>
    );   
}

export default ProjectTaskList;