package soc.tech.redmine_data.web.project;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectWebAdapter {

    private final RestTemplate restTemplate;
    private final ProjectConfig projectConfig;

    public List<Project> projects() {
        ResponseEntity<JsonDataProjects> projectsResponse =
                restTemplate.getForEntity(projectConfig.getRedmine().concat(projectConfig.getProjects()),
                      JsonDataProjects.class);
        return projectsResponse.getBody().getProjects();
    }

}
