import { useEffect, useState, useRef } from "react";
import { createPortal } from "react-dom";
import api from '../api/axiosConfig';
import '../style/components/LightBox.css';

const LightBox = ({onClose}) => {
    const ref = useRef(false);
    useEffect(() => {
        const checkClickOutside = (e) => {
            if (ref.current != false && !ref.current.contains(e.target)) {
                console.log("ref: ", ref);
                onClose();
            }
        };
        document.addEventListener("click", checkClickOutside);
        return () => {
            document.removeEventListener("click", checkClickOutside);
        }
    }, [onClose]);

    const [projectName, setProjectName] = useState("");
    const [projectDesc, setProjectDesc] = useState("");
    const [showSuccess, setShowSuccess] = useState(false);
    const jwt = localStorage.getItem("jwt");
    const userId = localStorage.getItem("userId").replace(/['"]+/g, '');
    const [error, setError] = useState("");

    const handleCreateProject = async () => {
        console.log("handle create project");
        if (!projectName) {
          setError("Please enter username and password");
        } else {
          await api.post('http://127.0.0.1:8080/project', {
            name: projectName,
            description: projectDesc,
            ownerId: `${userId}`
          }, {
            headers: {
                'Authorization': `${jwt}`
            }
        })
        .then((res) => {
            if (res.status == 201) {
              setShowSuccess(true);
            } else {
              setError('Looks like there was an issue. Try talking to the developer.');
            }
          });
          // .catch('Something\'s gone wrong. Please contact the developer or try again later.');
        }
    }

    const handleChangeProjectName = (event) => {
        setProjectName(event.target.value);
    }

    const handleChangeProjectDesc = (event) => {
        setProjectDesc(event.target.value);
    }

    return (
        createPortal(
            <div className="lightbox">
                <div className="dialog" ref={ref}>
                {!showSuccess ? (
                    <div className='project-create-form'>
                        <p className='project-create-form-prompt'>Alright, let's make a new project! First, let's give it a name.</p>
                        <input name='project-name-input' className='project-name' type='text' maxLength={64} required onChange={handleChangeProjectName}/>
                        <p className='project-create-form-prompt' onChange={handleChangeProjectDesc}>Add a description too, if you want.</p>
                        <textarea name='project-desc-input' onChange={handleChangeProjectDesc} className="project-desc" />
                        <button type="submit" name="submit" className="project-submit" onClick={handleCreateProject}>Create</button>
                    </div>
                ) : error ? (
                    <div className="error-container">
                        <p className="error-message">{error}</p>
                    </div>
                ) : (
                    <div className="success-message-container">
                        <p className="success-message">Your project was created!</p>
                    </div>
                )}
                </div>
            </div>,
            document.getElementById('lightbox-root')
        )
    );
}

export default LightBox;