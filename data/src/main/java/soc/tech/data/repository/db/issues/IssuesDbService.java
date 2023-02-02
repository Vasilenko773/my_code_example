package soc.tech.data.repository.db.issues;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssuesDbService {

    private final IssuesDbRepository issuesDbRepository;

    public void saveOrUpdate(IssuesDb issues) {
        IssuesDb issuesDb = issuesDbRepository.issuesByIssueIdAndSystemId(issues.getIssueId(), issues.getSystemId());
        if (issuesDb.getId() == null) {
            issuesDbRepository.save(issues);
        } else {
            issuesDbRepository.update(issues);
        }
    }

    public List<IssuesDb> allIssues() {
        return issuesDbRepository.allIssues();
    }

    public IssuesDb issuesById(int issueId, int systemId) {
        return issuesDbRepository.issuesByIssueIdAndSystemId(issueId, systemId);
    }
}
