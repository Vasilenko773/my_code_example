package soc.tech.report.domain.incident;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IncidentApiController {
    private final IncidentService incidentService;
    @GetMapping("api-open-incidents")
    @ApiOperation("Получение инцидентов со статусом -  открыто")
    public List<Incident> openIncidents() {
        return incidentService.newIncidentsDb();
    }

    @GetMapping("api-overdue-incidents")
    @ApiOperation("Получение просроченных инцидентов")
    public List<Incident> overdueIncidents() {
        return incidentService.overdueDb();
    }
}
