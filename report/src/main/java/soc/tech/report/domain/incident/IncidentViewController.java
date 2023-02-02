package soc.tech.report.domain.incident;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IncidentViewController {
    private final IncidentService incidentService;

    @GetMapping("/view-open-incidents")
    @ApiOperation("Получение HTML страницы с инцидентами, со статусом -  открыто")
    public String viewOpenIncidents(Model model) {
        model.addAttribute("incidents", incidentService.newIncidentsDb());
        return "incidents/open-incidents";
    }

    @GetMapping("/view-overdue-incidents")
    @ApiOperation("Получение HTML страницы с просроченными инцидентами")
    public String viewOverdueIncidents(Model model) {
        model.addAttribute("incidents", incidentService.overdueDb());
        return "incidents/overdue-incidents";
    }
}
