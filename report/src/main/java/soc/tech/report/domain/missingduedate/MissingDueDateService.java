package soc.tech.report.domain.missingduedate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soc.tech.report.util.DateFormatBuilder;
import soc.tech.report.util.ReportDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissingDueDateService {

    private final MissingDueDateRepository missingDueDateRepository;
    private final DateFormatBuilder dateFormatBuilder;

    public List<MissingDueDate> missingDueDates() {
        return missingDueDateRepository.findAllMissingDueDates().stream()
                .peek(i -> i.setStartDate(validDate(i.getStartDate())))
                .collect(Collectors.toList());
    }

    private String validDate(String date) {
        if (date != null && !date.isBlank()) {
            LocalDate valid = LocalDate.parse(date.split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return dateFormatBuilder.dateByFormat(new ReportDate(valid, "dd.MM.yyyy"));
        }
        return "";
    }
}