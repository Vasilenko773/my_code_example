package soc.tech.report.domain.systemforperiod;

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
public class SystemViewController {

    private final SystemService systemService;

    @GetMapping("view-systems-for-period/")
    @ApiOperation("Получение HTML страницы систем за определенный период")
    public String systemsForPeriod(Model model,@RequestParam(required = false, value = "from")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from, @RequestParam(required = false, value = "to")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        List<System> systems = systemService.systemsFromDb(from, to); // ВОТ ТУТ ЗАМЕНИЛ
        model.addAttribute("systems", systems);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        return "systemforperiod/system-period";
    }

    @GetMapping("view-systems-for-period-index")
    @ApiOperation("Получение промежуточной HTML страницы для формирования отчета за период")
    public String systemsForPeriodIndex(Model model) {
        model.addAttribute("start", systemService.startDateMonday());
        model.addAttribute("finish", LocalDate.now());
        return "systemforperiod/system-period-index";
    }

}
