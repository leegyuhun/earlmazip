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

    /**
     * 인구통계 (추계인구, 고령인구비율, 합계 출산율)
     * @param term
     * @param model
     * @return
     */
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
        birthRates.add("0");
        List<PopulationDto> list = new ArrayList<>();
        for (int i = dates.size() - 1; i > -1; i--) {
            PopulationDto item = new PopulationDto(dates.get(i), populations.get(i), agingPopulations.get(i), birthRates.get(i));
            list.add(item);
        }
        birthRates.remove(birthRates.size()-1);
        String title = "-";
        if (population.size() > 0) {
            title = population.get(0).getStatName();
        }

        model.addAttribute("population", populations);
        model.addAttribute("agingPopulation", agingPopulations);
        model.addAttribute("birthRate", birthRates);
        model.addAttribute("dates", dates);
        model.addAttribute("title", title);
        model.addAttribute("list", list);

        return "stat_etc/statPopulation";
    }

    /**
     * 소득통계 (1분위,중위,평균,5분위소득)
     * @param model
     * @return
     */
    @GetMapping("/stat_etc/income")
    public String getStatEtcIncome(Model model) {
        apiCallStatService.writeApiCallStat("STAT_ETC", "/stat_etc/income");

        // 중위소득
        List<EcosDataResponseDto> halfIncome = ecosDataService.getEcosData("080Y038", "I38A", "30", "15");

        // 평균소득
        List<EcosDataResponseDto> avgIncome = ecosDataService.getEcosData("080Y038", "I38A", "40", "15");

        // 5분위
        List<EcosDataResponseDto> topIncome = ecosDataService.getEcosData("080Y038", "I38A", "405", "15");

        // 1분위
        List<EcosDataResponseDto> botIncome = ecosDataService.getEcosData("080Y038", "I38A", "401", "15");
        List<String> dates = halfIncome.stream().map(o->new String(o.getDate())).collect(Collectors.toList());
        List<String> halfIncomes = halfIncome.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> avgIncomes = avgIncome.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> topIncomes = topIncome.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> botIncomes = botIncome.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<ItemDto> list = new ArrayList<>();
        for (int i = dates.size() - 1; i > -1; i--) {
            ItemDto item = new ItemDto(dates.get(i), halfIncomes.get(i), avgIncomes.get(i), topIncomes.get(i), botIncomes.get(i));
            list.add(item);
        }
        String title = "균등화 중위/평균/5분위 소득";

        model.addAttribute("halfIncomes", halfIncomes);
        model.addAttribute("avgIncomes", avgIncomes);
        model.addAttribute("topIncomes", topIncomes);
        model.addAttribute("botIncomes", botIncomes);
        model.addAttribute("dates", dates);
        model.addAttribute("title", title);
        model.addAttribute("list", list);

        return "stat_etc/statIncome";
    }

    @GetMapping("/stat_etc/consumerPriceIndex")
    public String getStatEtcConsumerPriceIndex(Model model) {
        apiCallStatService.writeApiCallStat("STAT_ETC", "/stat_etc/consumerPriceIndex");

        // 소비자물가지수(총 지수)
        List<EcosDataResponseDto> totIndex = ecosDataService.getEcosData("021Y125", "0", "", "15");

        // 소비자물가지수(전세)
        List<EcosDataResponseDto> leaseIndex = ecosDataService.getEcosData("021Y125", "D01101", "", "15");

        // 소비자물가지수(월세)
        List<EcosDataResponseDto> monthlyIndex = ecosDataService.getEcosData("021Y125", "D01102", "", "15");

        List<String> dates = totIndex.stream().map(o->new String(o.getDate())).collect(Collectors.toList());
        List<String> totIndexes = totIndex.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> leaseIndexes = leaseIndex.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> monthlyIndexes = monthlyIndex.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<ItemDto> list = new ArrayList<>();
        for (int i = dates.size() - 1; i > -1; i--) {
//            ItemDto item = new ItemDto(dates.get(i), halfIncomes.get(i), avgIncomes.get(i), topIncomes.get(i), botIncomes.get(i));
//            list.add(item);
        }
        String title = "소비자물가지수(총지수,전세,월세)";

        model.addAttribute("totIndexes", totIndexes);
        model.addAttribute("leaseIndexes", leaseIndexes);
        model.addAttribute("monthlyIndexes", monthlyIndexes);
        model.addAttribute("dates", dates);
        model.addAttribute("title", title);
        model.addAttribute("list", list);

        return "stat_etc/statConsumerPriceIndex";
    }

    @Data
    static class PopulationDto {

        private String date;
        private String population;
        private String agingPopulation;
        private String birthRate;

        public PopulationDto(String date, String population, String agingPopulation, String birthRate) {
            this.date = date;
            this.population = population;
            this.agingPopulation = agingPopulation;
            this.birthRate = birthRate;
        }
    }
    @Data
    static class ItemDto {

        private String date;
        private String halfIncome;
        private String avgIncome;
        private String topIncome;
        private String botIncome;

        public ItemDto(String date, String half, String avg, String top, String bot) {
            this.date = date;
            this.halfIncome = half;
            this.avgIncome = avg;
            this.topIncome = top;
            this.botIncome = bot;
        }
    }
}
