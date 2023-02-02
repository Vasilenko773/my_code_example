package soc.tech.report.domain.systemforperiod;

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
public class SystemApiController {

    private final SystemService systemService;

    @GetMapping("api-systems-for-period/{from}/{to}")
    @ApiOperation("Получение систем за определенынй период")
    public List<System> systemsForPeriod(@PathVariable("from")
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                   @PathVariable("to")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return systemService.systemsFromDb(from, to);
    }
}
