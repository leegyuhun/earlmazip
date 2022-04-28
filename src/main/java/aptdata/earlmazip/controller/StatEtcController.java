package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.EcosDataResponseDto;
import aptdata.earlmazip.service.ApiCallStatService;
import aptdata.earlmazip.service.EcosDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
