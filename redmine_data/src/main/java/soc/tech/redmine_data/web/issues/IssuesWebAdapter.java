package soc.tech.redmine_data.web.issues;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
@Component
@RequiredArgsConstructor
@Log4j2
public class IssuesWebAdapter {
    private final RestTemplate restTemplate;
    private final IssuesConfig issuesConfig;
    public List<Issues> issuesListByProject(int project) {
        Map<String, Object> keys = Map.of("project", project);
        ResponseEntity<JsonDataIssues> issuesResponse =
                restTemplate.getForEntity(issuesConfig.getRedmine().concat(issuesConfig.getIssues()),
                        JsonDataIssues.class, keys);
        return issuesResponse.getBody().getIssues();
    }

    public Issues issuesBuId(String id) {
        String url = issuesConfig.getRedmine().concat(issuesConfig.getIssuesById());
        url = url.replace("{id}", id);
        ResponseEntity<JsonDataIssueById> issuesResponse =
                restTemplate.getForEntity(url, JsonDataIssueById.class);

        return issuesResponse.getBody().getIssue().orElse(new Issues());
    }

    public List<Issues> allIssuesList(int offset) {
        ResponseEntity<JsonDataIssues> issuesResponse =
                restTemplate.getForEntity(issuesConfig.getRedmine().concat(issuesConfig.getAllIssues()),
                        JsonDataIssues.class, offset);
        return issuesResponse.getBody().getIssues();
    }

    public JsonDataIssues issuesInfo() {
        ResponseEntity<JsonDataIssues> issuesResponse =
                restTemplate.getForEntity(issuesConfig.getRedmine().concat(issuesConfig.getAllIssues()),
                        JsonDataIssues.class, 0);
        return issuesResponse.getBody();
    }
}
