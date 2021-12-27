package aptdata.earlmazip.controller;

import aptdata.earlmazip.domain.stat_area_yymm;
import aptdata.earlmazip.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;

    @GetMapping("/statistics/seoul")
    public String seoulList(Model model) {
        log.info("/statistics/seoul");
        List<stat_area_yymm> areas = statService.findStatSeoulList();
        model.addAttribute("list", areas);
        return "statistics/statSeoul";
    }

    @GetMapping("/statistics/seoul/{year}")
    public String seoulListYear(@PathVariable String year, Model model) {
        log.info("/statistics/seoul/" + year);
        List<stat_area_yymm> areas = statService.findStatSeoulListYear(year);
        model.addAttribute("list", areas);
        return "statistics/statSeoul";
    }

    @GetMapping("/statistics/gyunggi")
    public String gyunggiList(Model model) {
        log.info("/statistics/gyunggi");
        List<stat_area_yymm> areas = statService.findStatGyunggiList();
        model.addAttribute("list", areas);
        return "statistics/statGyunggi";
    }

    @GetMapping("/statistics/gyunggi/{year}")
    public String gyunggiListYear(@PathVariable String year, Model model) {
        log.info("/statistics/gyunggi/" + year);
        List<stat_area_yymm> areas = statService.findStatGyunggiListYear(year);
        model.addAttribute("list", areas);
        return "statistics/statGyunggi";
    }
}
