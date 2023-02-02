package soc.tech.bitrix24.web.issues;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class IssuesBitrixConfig {

    private @Value("${bitrix.allIssues.offset}") String issuesOffSet;
    private @Value("${bitrix.issues.by.date}") String issuesByChangeDate;
}
