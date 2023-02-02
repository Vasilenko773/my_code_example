package soc.tech.redmine_data.web.project;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ProjectConfig {

    private @Value("${redmine.url}") String redmine;
    private @Value("${projects.url}") String projects;

}
