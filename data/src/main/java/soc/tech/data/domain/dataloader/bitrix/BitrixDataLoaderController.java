package soc.tech.data.domain.dataloader.bitrix;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import soc.tech.bitrix24.web.issues.IssueBitrix;
import soc.tech.bitrix24.web.timeentries.TimeEntriesBitrix;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class BitrixDataLoaderController {

    private final BitrixDataLoaderService dataLoadService;

    @GetMapping("data-load-bitrix24")
    @ApiOperation("ПЕРВОНОЧАЛЬНАЯ ЗАГРУЗКА ДАННЫХ ИЗ БИТРИКС24. ВЫПОЛЯНЕТСЯ ПЕРЕД НАЧАЛОМ РАБОТЫ ПРИЛОЖЕНИЯ")
    public void dataLoadBitrix24() {
        dataLoadService.saveAllElement();
    }

}


