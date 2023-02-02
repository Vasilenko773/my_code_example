package soc.tech.redmine_data.web.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectRepository {
    private final ProjectWebAdapter projectWebAdapter;
    public List<Project> findAllProjectAndParentNotNull() {
        List<Project> projects = projectWebAdapter.projects();
        return projects.stream().filter(i -> i.getParent() != null).collect(Collectors.toList());
    }

    public List<Project> findAllProject() {
       return projectWebAdapter.projects();
    }
}
