package soc.tech.report.domain.employeeforperiod;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportForEmployeeApiController {

    private final ReportForEmployeeService reportForEmployeeService;

    @GetMapping("api-employee-for-period/{from}/{to}")
    @ApiOperation("Получение сотрудников с времеными затратами за определенынй период")
    public List<ReportForEmployee> reportEmployeeForPeriod(@PathVariable("from")
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                           @PathVariable("to")
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return reportForEmployeeService.employeesForPeriod(from, to);
    }
}
