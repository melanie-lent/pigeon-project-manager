import React from 'react';

// import Layout from '../pages/layouts/Layout';
import OwnedProjectList from '../../components/project/OwnedProjectList.js';

import '../../style/components/ProjectList.css';

const ProjectView = () => {
    return (
        <>
            <div className="project-list-header-container">
                <h2 className='project-list-header'>My Projects</h2>
                <OwnedProjectList />
            </div>
            {/* <div className="project-list-header-container">
                <h2 className='project-list-header'>Projects I'm In</h2>
            </div> */}
        </>
    )
}

export default ProjectView;