package soc.tech.report.domain.duedatelessfact;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soc.tech.report.util.DateFormatBuilder;
import soc.tech.report.util.ReportDate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DueDateLessFactService {

    private final DueDateLessFactRepository dueDateLessFactRepository;
    private final DateFormatBuilder dateFormatBuilder;

    public List<DueDateLessFact> dueDateLessFactList() {
        return dueDateLessFactRepository.findAllDueDateLessFact().stream()
                .filter(i -> i.getEstimatedHours() < i.getHours())
                .peek(i -> {
                    i.setStartDate(validDate(i.getStartDate()));
                    i.setDeadline(validDate(i.getDeadline()));
                    i.setHours(hours(i.getHours()));
                })
                .collect(Collectors.toList());
    }

    private String validDate(String date) {
        if (date != null && !date.isBlank()) {
            LocalDate valid = LocalDate.parse(date.split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return dateFormatBuilder.dateByFormat(new ReportDate(valid, "dd.MM.yyyy"));
        }
        return "";
    }

    private Double hours(Double d) {
        return BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }
}


