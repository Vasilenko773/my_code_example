package soc.tech.report.domain.duedatelessfact;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DueDateLessFactViewController {

    private final DueDateLessFactService dueDateLessFactService;

    @GetMapping("view-duedate-less-fact")
    @ApiOperation("Получение задач у которых время выполнения превышает ожидаемое время на задачу")
    public String viewTasksSevenDays(Model model) {
        model.addAttribute("tasks", dueDateLessFactService.dueDateLessFactList());
        return "duedatelessfact/duedate-less-fact";
    }
}
