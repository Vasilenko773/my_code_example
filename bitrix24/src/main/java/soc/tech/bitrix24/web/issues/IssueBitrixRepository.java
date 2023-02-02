package soc.tech.bitrix24.web.issues;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soc.tech.bitrix24.web.issues.componensts.json.JsonBitrixDataIssues;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class IssueBitrixRepository {

    private final IssueBitrixWebAdapter issueBitrixWebAdapter;

    public JsonBitrixDataIssues issuesInfo() {
        return issueBitrixWebAdapter.issuesInfo();
    }

    public List<IssueBitrix> allIssue(int count) {
        return issueBitrixWebAdapter.allIssues(count);
    }

    public JsonBitrixDataIssues issuesInfoByDate(LocalDate date, int count) {
        return issueBitrixWebAdapter.issuesInfoByDate(date, count);
    }

    public List<IssueBitrix> allIssuesByDate(LocalDate date, int count) {
        return issueBitrixWebAdapter.allIssuesByDate(date, count);
    }
}
