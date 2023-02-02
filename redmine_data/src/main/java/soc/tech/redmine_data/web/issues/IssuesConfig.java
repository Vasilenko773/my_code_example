package soc.tech.redmine_data.web.issues;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class IssuesConfig {

    private @Value("${redmine.url}") String redmine;
    private @Value("${issues.url}")String issues;
    private @Value("${issues.id.url}")String issuesById;
    private @Value("${issues.all.url}")String allIssues;
}
