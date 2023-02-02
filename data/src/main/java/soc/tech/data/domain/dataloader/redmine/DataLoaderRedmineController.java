package soc.tech.data.domain.dataloader.redmine;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DataLoaderRedmineController {

    private final DataLoaderRedmineService dataLoaderRedmineService;

    @GetMapping("data-load-redmine")
    @ApiOperation("ПЕРВОНОЧАЛЬНАЯ ЗАГРУЗКА ДАННЫХ ИЗ РЕДМАЙН. ВЫПОЛЯНЕТСЯ ПЕРЕД НАЧАЛОМ РАБОТЫ ПРИЛОЖЕНИЯ")
    public void dataLoadRedmine() {
        dataLoaderRedmineService.saveAllElement();
    }
}
