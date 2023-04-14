import React from 'react';
import api from '../../api/axiosConfig';

// import Layout from '../pages/layouts/Layout';
// import OwnedProjectList from '../components/OwnedProjectList.js';
// import TaskItem from './TaskItem';
import {useState, useEffect} from 'react';

import '../../style/components/ProjectList.css';

const TaskView = () => {
    const [tasks, setTasks] = useState([]);
    // const [tasksByProject, setTasksByProject] = useState([]);

    const getTasks = async () => {
        await api.get('http://127.0.0.1:8080/user/task/{id}') // todo: dynamic query
            .then((res) => {
                // groupByTask = (data, key) {
                //     return data.reduce(function(project, task) {
                //         project[task[key]] = project[task[key]] || []).push(task); 
                //     }
                    setTasks(res.data);
                });
            };
    
    useEffect(() =>  {
        getTasks();
    }, []);

    return (
        <div>
            {Object.keys(tasks).map(project => (
                console.log(project))
                // <div className="project-list-header-container">
                //     <h2 className='project-list-header'></h2>
                // </div>
            )}

        </div>
    );
}

export default TaskView;