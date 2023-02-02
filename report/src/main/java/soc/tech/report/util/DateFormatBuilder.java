package soc.tech.report.util;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class DateFormatBuilder {

    public String dateByFormat(ReportDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(date.getFormat());
        return date.getDate().format(formatter);
    }
}
