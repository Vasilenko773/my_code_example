package soc.tech.report.domain.employeeforperiod;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReportForEmployeeViewController {

    private final ReportForEmployeeService reportForEmployeeService;

    @GetMapping("view-employee-for-period/")
    @ApiOperation("Получение HTML страницы c затратами сотрудников за определенный период")
    public String systemsForPeriod(Model model, @RequestParam(required = false, value = "from")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from, @RequestParam(required = false, value = "to")
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        List<ReportForEmployee> employees = reportForEmployeeService.employeesForPeriod(from, to);
        model.addAttribute("employees", employees);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        return "reportemployeeforperiod/report-employee-period";
    }

    @GetMapping("view-employee-for-period-index")
    @ApiOperation("Получение промежуточной HTML страницы для формирования отчета за период")
    public String employeeForPeriodIndex(Model model) {
        model.addAttribute("start", reportForEmployeeService.startDateMonday());
        model.addAttribute("finish", LocalDate.now());
        return "reportemployeeforperiod/report-employee-period-index";
    }
}
