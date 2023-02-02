package soc.tech.report.domain.devandchangtask;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DevAndChangTaskViewController {

    private final DevAndChangTaskService devAndChangTaskService;

    @GetMapping("view-dev-and-change-tasks-7-days")
    @ApiOperation("Получение запросов на изменение, со сроком сдачи заказчику менее 7 дней")
    public String viewTasksSevenDays(Model model) {
        model.addAttribute("tasks", devAndChangTaskService.validTasksOneWeeks());
        return "devandchangetask/dev-and-change-tasks-one-weeks";
    }

    @GetMapping("view-dev-and-change-tasks-14-days")
    @ApiOperation("Получение запросов на изменение, со сроком сдачи заказчику менее 14 дней")
    public String viewTasksFourTeenDays(Model model) {
        model.addAttribute("tasks", devAndChangTaskService.validTasksTwoWeeks());
        return "devandchangetask/dev-and-change-tasks-two-weeks";
    }
}


