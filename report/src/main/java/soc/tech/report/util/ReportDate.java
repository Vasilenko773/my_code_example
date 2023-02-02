package soc.tech.report.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReportDate {

    private LocalDate date;
    private String format;
}
