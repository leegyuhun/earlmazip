package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.EcosDataResponseDto;
import aptdata.earlmazip.service.ApiCallStatService;
import aptdata.earlmazip.service.EcosDataService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StatEtcController {
    private final EcosDataService ecosDataService;
    private final ApiCallStatService apiCallStatService;

    @GetMapping("/stat_etc/population/{term}")
    public String getStatEtcPopulation(@PathVariable String term, Model model) {
        apiCallStatService.writeApiCallStat("STAT_ETC", "/stat_etc/population/" + term);

        // 추계인구
        List<EcosDataResponseDto> population = ecosDataService.getEcosData("080Y035", "I35A", "", term);

        // 고령인구비율(65세~)
        List<EcosDataResponseDto> agingPopulation = ecosDataService.getEcosData("080Y035", "I35D", "", term);
        
        // 합계출산율
        List<EcosDataResponseDto> birthRate = ecosDataService.getEcosData("080Y035", "I35E", "", term);

        List<String> dates = population.stream().map(o->new String(o.getDate())).collect(Collectors.toList());
        List<String> birthRates = birthRate.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> agingPopulations = agingPopulation.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> populations = population.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());

        String title = "-";
        if (population.size() > 0) {
            title = population.get(0).getStatName();
        }

        model.addAttribute("population", populations);
        model.addAttribute("agingPopulation", agingPopulations);
        model.addAttribute("birthRate", birthRates);
        model.addAttribute("dates", dates);
        model.addAttribute("title", title);

        return "stat_etc/statPopulation";
    }

    @GetMapping("/stat_etc/income")
    public String getStatEtcIncome(Model model) {
        apiCallStatService.writeApiCallStat("STAT_ETC", "/stat_etc/income");

        // 중위소득
        List<EcosDataResponseDto> halfIncome = ecosDataService.getEcosData("080Y038", "I38A", "30", "15");

        // 평균소득
        List<EcosDataResponseDto> avgIncome = ecosDataService.getEcosData("080Y038", "I38A", "40", "15");

        // 5분위
        List<EcosDataResponseDto> topIncome = ecosDataService.getEcosData("080Y038", "I38A", "405", "15");
        List<String> dates = halfIncome.stream().map(o->new String(o.getDate())).collect(Collectors.toList());
        List<String> halfIncomes = halfIncome.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> avgIncomes = avgIncome.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> topIncomes = topIncome.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<ItemDto> list = new ArrayList<>();
        for (int i = dates.size() - 1; i > -1; i--) {
            ItemDto item = new ItemDto(dates.get(i), halfIncomes.get(i), avgIncomes.get(i), topIncomes.get(i));
            list.add(item);
        }
        String title = "균등화 중위/평균/5분위 소득";

        model.addAttribute("halfIncomes", halfIncomes);
        model.addAttribute("avgIncomes", avgIncomes);
        model.addAttribute("topIncomes", topIncomes);
        model.addAttribute("dates", dates);
        model.addAttribute("title", title);
        model.addAttribute("list", list);

        return "stat_etc/statIncome";
    }

    @Data
    static class ItemDto {

        private String date;
        private String halfIncome;
        private String avgIncome;
        private String topIncome;

        public ItemDto(String date, String half, String avg, String top) {
            this.date = date;
            this.halfIncome = half;
            this.avgIncome = avg;
            this.topIncome = top;
        }
    }
}
