package soc.tech.bitrix24.web.issues;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import soc.tech.bitrix24.config.bitrix.BitrixConfig;
import soc.tech.bitrix24.web.issues.componensts.json.JsonBitrixDataIssues;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class IssueBitrixWebAdapter {

    private final RestTemplate restTemplate;
    private final BitrixConfig bitrixConfig;
    private final IssuesBitrixConfig issuesBitrixConfig;

    public JsonBitrixDataIssues issuesInfo() {
        ResponseEntity<JsonBitrixDataIssues> issuesResponse =
                restTemplate.getForEntity(bitrixConfig.getBitrix().concat(issuesBitrixConfig.getIssuesOffSet()),
                        JsonBitrixDataIssues.class, 0);
        return issuesResponse.getBody();
    }

    public List<IssueBitrix> allIssues(int count) {
        ResponseEntity<JsonBitrixDataIssues> issuesResponse =
                restTemplate.getForEntity(bitrixConfig.getBitrix().concat(issuesBitrixConfig.getIssuesOffSet()),
                        JsonBitrixDataIssues.class, count);

        return issuesResponse.getBody().getResult().getTasks();
    }

    public JsonBitrixDataIssues issuesInfoByDate(LocalDate date, int count) {
        Map<String, Object> keys = Map.of("date", date, "count", count);
        ResponseEntity<JsonBitrixDataIssues> issuesResponse =
                restTemplate.getForEntity(bitrixConfig.getBitrix().concat(issuesBitrixConfig.getIssuesByChangeDate()),
                        JsonBitrixDataIssues.class, keys);
        return issuesResponse.getBody();
    }

    public List<IssueBitrix> allIssuesByDate(LocalDate date, int count) {
        Map<String, Object> keys = Map.of("date", date, "count", count);
        ResponseEntity<JsonBitrixDataIssues> issuesResponse =
                restTemplate.getForEntity(bitrixConfig.getBitrix().concat(issuesBitrixConfig.getIssuesByChangeDate()),
                        JsonBitrixDataIssues.class, keys);
        return issuesResponse.getBody().getResult().getTasks();
    }
}
