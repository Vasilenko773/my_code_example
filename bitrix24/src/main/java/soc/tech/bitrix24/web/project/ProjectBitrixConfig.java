package soc.tech.bitrix24.web.project;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ProjectBitrixConfig {

    private @Value("${bitrix.all.project}") String allProjects;
    private @Value("${bitrix.project.by.id}") String projectById;
}
