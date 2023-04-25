import { useEffect, useState, useRef } from "react";
import { createPortal } from "react-dom";
import { useLocation } from 'react-router-dom';
import api from '../../api/axiosConfig';

import '../../style/components/LightBox.css';
import { RadioGroup } from "@mui/material";

const CreateTaskLightBox = ({onClose, onAddTask}) => {
    const ref = useRef(false);
    const location = useLocation();
    useEffect(() => {
        const checkClickOutside = (e) => {
            if (ref.current != false && !ref.current.contains(e.target)) {
                onClose();
            }
        };
        document.addEventListener("click", checkClickOutside);
        return () => {
            document.removeEventListener("click", checkClickOutside);
        }
    }, [onClose]);

    const [taskName, setTaskName] = useState("");
    const [taskDesc, setTaskDesc] = useState("");
    const [taskPriority, setTaskPriority] = useState(0);
    const [taskDueDate, setTaskDueDate] = useState();
    const [taskTags, setTaskTags] = useState([]);
    const [taskAssignees, setTaskAssignees] = useState([]);
    const [showSuccess, setShowSuccess] = useState(false);
    const [error, setError] = useState("");
    
    const jwt = localStorage.getItem("jwt");
    const userId = localStorage.getItem("userId").replace(/['"]+/g, '');
    const pathname = location.pathname;
    const projectId = pathname.split("/")[2];

    const handleCreateTask = async () => {
        if (!taskName) {
          setError("Please give your task a name!");
        } else {
            if (!taskDueDate) {
                setTaskDueDate(null);
            }
            let createdOn = new Date();
            console.log(taskPriority);  
          await api.post('http://127.0.0.1:8080/task', {
            taskName: taskName,
            description: taskDesc,
            createdBy: userId,
            projectId: projectId,
            priority: taskPriority,
            dueDate: taskDueDate,
            isCompleted: false,
            createdOn: createdOn,
            tags: taskTags
          }, {
            headers: {
                'Authorization': `${jwt}`
            }
        })
        .then((res) => {
            if (res.status == 201) {
              setShowSuccess(true);
              const taskData = {
                id: res.data.id,
                taskName: taskName,
                description: taskDesc,
                createdBy: userId,
                projectId: projectId,
                priority: taskPriority,
                dueDate: taskDueDate,
                isCompleted: false,
                tags: taskTags
              };
              onAddTask(taskData);
            } else {
              setError('Looks like there was an issue. Try talking to the developer.');
            }
          })
          .catch((e) => console.log(e));
        }
    }

    const handleChangeTaskName = (event) => {
        setTaskName(event.target.value);
    }

    const handleChangeTaskDesc = (event) => {
        setTaskDesc(event.target.value);
    }

    const handleChangeTaskPriority = (event) => {
        setTaskPriority(event.target.value);
    }

    const handleChangeTaskDueDate = (event) => {
        setTaskDueDate(event.target.value);
    }

    const handleChangeTaskTags = (event) => {
        setTaskTags(event.target.value);
    }

    return (
        createPortal(
            <div className="lightbox">
                <div className="dialog" ref={ref}>
                {!showSuccess ? (
                    <div className='task-create-form'>
                        <label className='task-create-form-prompt task-label' />
                        <input name='task-name-input' className='task-name-input task-input' type='text' maxLength={64} required onChange={handleChangeTaskName} placeholder={"Task Name"} />
                        <textarea name='task-desc-input task-input' placeholder="Task Description" onChange={handleChangeTaskDesc} className="task-desc-input" />
                        <div className="priority-radio">
                            <RadioGroup row>
                                <label className="priority-label" for="zero">None</label>
                                <input className="priority-input" type="radio" name="priority" value={0} onChange={handleChangeTaskPriority} />
                                <label className="priority-label" for="one">Low</label>
                                <input className="priority-input" type="radio" name="priority" value={1} onChange={handleChangeTaskPriority} />
                                <label className="priority-label"  for="two">Normal</label>
                                <input className="priority-input" type="radio" name="priority" value={2} onChange={handleChangeTaskPriority} />
                                <label className="priority-label"  for="three">High</label>
                                <input className="priority-input" type="radio" name="priority" value={3} onChange={handleChangeTaskPriority} />
                                <label className="priority-label"  for="four">Urgent</label>
                                <input className="priority-input" type="radio" name="priority" value={4} onChange={handleChangeTaskPriority} />
                            </RadioGroup>
                        </div>
                        <label className="task-duedate task-label" />
                        <input type="date" className="task-input" onChange={handleChangeTaskDueDate}  />
                        <button type="submit" name="submit" className="task-submit" onClick={handleCreateTask}>Create</button>
                    </div>
                ) : error ? (
                    <div className="error-container">
                        <p className="error-message">{error}</p>
                    </div>
                ) : (
                    <div className="success-message-container">
                        <p className="success-message">Your task was created!</p>
                    </div>
                )}
                </div>
            </div>,
            document.getElementById('lightbox-root')
        )
    );
}

export default CreateTaskLightBox;