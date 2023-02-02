package soc.tech.report.domain.index;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"/","main-page"})
    @ApiOperation("Получение HTML страницы главного меню")
    public String viewOpenIncidents() {
        return "index";
    }
}
