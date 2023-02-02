package soc.tech.report.domain.devandchangtask;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DevAndChangTaskApiController {

    private final DevAndChangTaskService devAndChangTaskService;

    @GetMapping("api-dev-and-change-14-days")
    @ApiOperation("Получение запросов на изменение и на развитие, со сроком сдачи заказчику менее 14 дней")
    public List<DevAndChangTask> tasksFourTeenDays() {
        return devAndChangTaskService.validTasksTwoWeeks();
    }

    @GetMapping("api-dev-and-change-tasks-7-days")
    @ApiOperation("Получение запросов на изменение и на развитие, со сроком сдачи заказчику менее 7 дней")
    public List<DevAndChangTask> tasksSevenDays() {
        return devAndChangTaskService.validTasksOneWeeks();
    }
}
