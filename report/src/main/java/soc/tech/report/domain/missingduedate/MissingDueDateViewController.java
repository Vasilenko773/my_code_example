package soc.tech.report.domain.missingduedate;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MissingDueDateViewController {
    private final MissingDueDateService missingDueDateService;

    @GetMapping("view-missing-duedate")
    @ApiOperation("Получение задач у которых отстутсвует оценка временных затрат")
    public String missingDueDate(Model model) {
        model.addAttribute("tasks", missingDueDateService.missingDueDates());
        return "missingduedate/missing-duedate";
    }
}

