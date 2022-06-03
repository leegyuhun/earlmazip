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
        apiCallStatService.writeApiCallStat("STAT_ETC", "/stat_etc/population/" + term, "0");

        // 추계인구
        List<EcosDataResponseDto> population = ecosDataService.getEcosData("901Y028", "I35A", "", term);

        // 고령인구비율(65세~)
        List<EcosDataResponseDto> agingPopulation = ecosDataService.getEcosData("901Y028", "I35D", "", term);
        
        // 합계출산율
        List<EcosDataResponseDto> birthRate = ecosDataService.getEcosData("901Y028", "I35E", "", term);

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
        apiCallStatService.writeApiCallStat("STAT_ETC", "/stat_etc/income", "0");

        // 중위소득
        List<EcosDataResponseDto> halfIncome = ecosDataService.getEcosData("901Y031", "I38A", "30", "15");

        // 평균소득
        List<EcosDataResponseDto> avgIncome = ecosDataService.getEcosData("901Y031", "I38A", "40", "15");

        // 5분위
        List<EcosDataResponseDto> topIncome = ecosDataService.getEcosData("901Y031", "I38A", "405", "15");

        // 1분위
        List<EcosDataResponseDto> botIncome = ecosDataService.getEcosData("901Y031", "I38A", "401", "15");
        List<String> dates = halfIncome.stream().map(o->new String(o.getDate())).collect(Collectors.toList());
        List<String> halfIncomes = halfIncome.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> avgIncomes = avgIncome.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> topIncomes = topIncome.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> botIncomes = botIncome.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<IncomeDto> list = new ArrayList<>();
        for (int i = dates.size() - 1; i > -1; i--) {
            IncomeDto item = new IncomeDto(dates.get(i), halfIncomes.get(i), avgIncomes.get(i), topIncomes.get(i), botIncomes.get(i));
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

    /**
     * 개인신용카드 사용 통계
     * @param areacode
     * @param model
     * @return
     */
    @GetMapping("/stat_etc/creditCardUse/{areacode}")
    public String getStatEtcCreditCardUse(@PathVariable String areacode, Model model) {
        apiCallStatService.writeApiCallStat("STAT_ETC", "/stat_etc/creditCardUse/" + areacode, areacode);

        String code = "";
        if (areacode.equals("11")) {
            code = "A";
        } else if (areacode.equals("41")) {
            code = "L";
        } else {
            code = "D";
        }

        // 개인신용_서울_전체
        List<EcosDataResponseDto> totalUse = ecosDataService.getEcosData("043Y070", "A", "1000", "10");

        // 개인신용_서울_백화점
        List<EcosDataResponseDto> departmentUse = ecosDataService.getEcosData("043Y070", "A", "1110", "15");

        // 개인신용_서울_의료보건
        List<EcosDataResponseDto> medicalUse = ecosDataService.getEcosData("043Y070", "A", "1700", "15");

        // 개인신용_서울_의류잡화
        List<EcosDataResponseDto> clothUse = ecosDataService.getEcosData("043Y070", "A", "1400", "15");

        // 개인신용_서울_가구가전
        List<EcosDataResponseDto> furnitureUse = ecosDataService.getEcosData("043Y070", "A", "1600", "15");

        List<String> dates = totalUse.stream().map(o->new String(o.getDate())).collect(Collectors.toList());
        List<String> totalUses = totalUse.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> departmentUses = departmentUse.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> medicalUses = medicalUse.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> furnitureUses = furnitureUse.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<String> clothUses = clothUse.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());
        List<CreditCardUseDto> list = new ArrayList<>();
        for (int i = dates.size() - 1; i > -1; i--) {
            CreditCardUseDto item = new CreditCardUseDto(dates.get(i), totalUses.get(i), departmentUses.get(i), medicalUses.get(i), furnitureUses.get(i), clothUses.get(i));
            list.add(item);
        }
        String title = "-";
        if (areacode.equals("11")) {
            title = "개인신용카드사용현황 (서울)";
        } else if (areacode.equals("41")) {
            title = "개인신용카드사용현황 (경기)";
        } else {
            title = "개인신용카드사용현황 (인천)";
        }

        model.addAttribute("totalUses", totalUses);
        model.addAttribute("departmentUses", departmentUses);
        model.addAttribute("medicalUses", medicalUses);
        model.addAttribute("furnitureUses", furnitureUses);
        model.addAttribute("clothUses", clothUses);
        model.addAttribute("dates", dates);
        model.addAttribute("title", title);
        model.addAttribute("list", list);

        return "stat_etc/statCreditCardUse";
    }

    /**
     * 소비자 물가지수
     * @param model
     * @return
     */
    @GetMapping("/stat_etc/consumerPriceIndex")
    public String getStatEtcConsumerPriceIndex(Model model) {
        apiCallStatService.writeApiCallStat("STAT_ETC", "/stat_etc/consumerPriceIndex", "0");

        // 소비자물가지수(전국)
        List<EcosDataResponseDto> totIndex = ecosDataService.getEcosData("901Y009", "0", "", "16");

        // 주택매매가격지수(KB) 총지수(서울)
        List<EcosDataResponseDto> seoulTradeIndex = ecosDataService.getEcosData("901Y062", "P63AD", "", "16");

        // 주택전세가격지수(KB) 총지수(서울)
        List<EcosDataResponseDto> seoulLeaseIndex = ecosDataService.getEcosData("901Y063", "P64AD", "", "16");

        List<String> dates = totIndex.stream().map(o->new String(o.getDate())).collect(Collectors.toList());
        List<Float> totIndexes = totIndex.stream().map(o->new Float(o.getDataValue())).collect(Collectors.toList());
        List<Float> seoulTradeIndexes = seoulTradeIndex.stream().map(o->new Float(o.getDataValue())).collect(Collectors.toList());
        List<Float> seoulLeaseIndexes = seoulLeaseIndex.stream().map(o->new Float(o.getDataValue())).collect(Collectors.toList());
        List<IndexDto> list = new ArrayList<>();
        for (int i = dates.size() - 1; i > -1; i--) {
            IndexDto item = new IndexDto(dates.get(i), totIndexes.get(i), seoulTradeIndexes.get(i), seoulLeaseIndexes.get(i));
            list.add(item);
        }

        String title = "-";
        String subtitle = "-";
        if (totIndex.size() > 0) {
            subtitle = " ※ 소비자물가(기준: " + totIndex.get(0).getUnitName() + ")";
        }
        if (seoulTradeIndex.size() > 0) {
            subtitle = subtitle + ", 매매지수(기준: " + seoulTradeIndex.get(0).getUnitName() + ")";
        }
        if (seoulLeaseIndex.size() > 0) {
            subtitle = subtitle + ", 전세지수(기준: " + seoulLeaseIndex.get(0).getUnitName() + ")";
        }
        model.addAttribute("totIndexes", totIndexes);
        model.addAttribute("seoulTradeIndexes", seoulTradeIndexes);
        model.addAttribute("seoulLeaseIndexes", seoulLeaseIndexes);
        model.addAttribute("dates", dates);
        model.addAttribute("title", title);
        model.addAttribute("subtitle", subtitle);
        model.addAttribute("list", list);

        return "stat_etc/statConsumerPriceIndex";
    }

    @Data
    static class IndexDto {
        private String date;
        private float value1;
        private float value2;
        private float value3;

        public IndexDto(String date, float value1, float value2, float value3) {
            this.date = date;
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
        }
    }

    @Data
    static class CreditCardUseDto {

        private String date;
        private String value1;
        private String value2;
        private String value3;
        private String value4;
        private String value5;

        public CreditCardUseDto(String date, String value1, String value2, String value3, String value4, String value5) {
            this.date = date;
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
            this.value4 = value4;
            this.value5 = value5;
        }
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
    static class IncomeDto {

        private String date;
        private String halfIncome;
        private String avgIncome;
        private String topIncome;
        private String botIncome;

        public IncomeDto(String date, String half, String avg, String top, String bot) {
            this.date = date;
            this.halfIncome = half;
            this.avgIncome = avg;
            this.topIncome = top;
            this.botIncome = bot;
        }
    }
}
