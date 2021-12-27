package aptdata.earlmazip.controller;

import aptdata.earlmazip.domain.stat_area_yymm;
import aptdata.earlmazip.service.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;

    @GetMapping("/statistics/seoul")
    public String seoulList(Model model) {
        List<stat_area_yymm> areas = statService.findStatSeoulList();
        model.addAttribute("list", areas);
        return "/statistics/stat";
    }

    @GetMapping("/statistics/seoul/{year}")
    public String seoulListYear(@PathVariable String year, Model model) {
        List<stat_area_yymm> areas = statService.findStatSeoulListYear(year);
        model.addAttribute("list", areas);
        return "/statistics/stat";
    }

    @GetMapping("/statistics/gyunggi")
    public String gyunggiList(Model model) {
        List<stat_area_yymm> areas = statService.findStatGyunggiList();
        model.addAttribute("list", areas);
        return "/statistics/stat";
    }
}
