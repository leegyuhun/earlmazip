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

    @GetMapping("/statistics/seoul/UA02")
    public String seoulListUA02(Model model) {
        log.info("/statistics/seoul/UA02");
        List<stat_area_yymm> areas = statService.findStatSeoulListUA02();
        model.addAttribute("list", areas);
        return "statistics/statSeoul";
    }

    @GetMapping("/statistics/seoul/UA03")
    public String seoulListUA03(Model model) {
        log.info("/statistics/seoul/UA03");
        List<stat_area_yymm> areas = statService.findStatSeoulListUA03();
        model.addAttribute("list", areas);
        return "statistics/statSeoul";
    }

    @GetMapping("/statistics/seoul/UA04")
    public String seoulListUA04(Model model) {
        log.info("/statistics/seoul/UA04");
        List<stat_area_yymm> areas = statService.findStatSeoulListUA04();
        model.addAttribute("list", areas);
        return "statistics/statSeoul";
    }

    @GetMapping("/statistics/seoul/UA05")
    public String seoulListUA05(Model model) {
        log.info("/statistics/seoul/UA05");
        List<stat_area_yymm> areas = statService.findStatSeoulListUA05();
        model.addAttribute("list", areas);
        return "statistics/statSeoul";
    }

    @GetMapping("/statistics/seoul/UA06")
    public String seoulListUA06(Model model) {
        log.info("/statistics/seoul/UA06");
        List<stat_area_yymm> areas = statService.findStatSeoulListUA06();
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
