package soc.tech.data.repository.db.timeentries.components.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectDbService {

    private final ProjectDbRepository projectDbRepository;

    public void saveOrUpdate(ProjectDb project) {
        ProjectDb projectDb = projectDbRepository.projectByProjectIdAndSystemId(project.getProjectId(), project.getSystemId());
        if (projectDb.getId() == null) {
            projectDbRepository.save(project);
        } else {
            projectDbRepository.update(project);
        }
    }

    public ProjectDb projectByProjectIdAndSystemId(int projectId, int systemId) {
        return projectDbRepository.projectByProjectIdAndSystemId(projectId, systemId);
    }

    public ProjectDb projectById(int id) {
        return projectDbRepository.projectById(id);
    }


}
