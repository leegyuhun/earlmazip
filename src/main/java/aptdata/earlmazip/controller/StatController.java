package aptdata.earlmazip.controller;

import aptdata.earlmazip.domain.RankYear;
import aptdata.earlmazip.domain.StatAreaYYMM;
import aptdata.earlmazip.domain.StatSidoYYMM;
import aptdata.earlmazip.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;

    @GetMapping("/statistics/seoul")
    public String seoulList(Model model) {
        log.info("/statistics/seoul");
        List<StatAreaYYMM> areas = statService.findStatSeoulList();
        model.addAttribute("list", areas);
        return "statistics/statSeoul";
    }

    @GetMapping("/statistics/seoul/{year}")
    public String seoulListYear(@PathVariable String year, Model model) {
        log.info("/statistics/seoul/" + year);
        List<StatAreaYYMM> areas;
        if (StringUtils.hasText(year)) {
            areas = statService.findStatSeoulListYear(year);
        }
        else{
            areas = new ArrayList<>();
        }
        model.addAttribute("list", areas);
        return "statistics/statSeoul";
    }

    @GetMapping("/statistics/seoul/usearea/{ua}")
    public String seoulListUA(@PathVariable String ua, Model model) {
        log.info("/statistics/seoul/usearea/" + ua);
        List<StatAreaYYMM> areas;
        if (StringUtils.hasText(ua)) {
            areas = statService.findStatSeoulListUA(ua);
        }
        else{
            areas = new ArrayList<>();
        }
        model.addAttribute("list", areas);
        return "statistics/statSeoul";
    }

    @GetMapping("/statistics/seoul/top/{sigungucode}")
    public String seoulTopList(@PathVariable String sigungucode, Model model) {
        log.info("/statistics/seoul/top/" + sigungucode);
        List<RankYear> tops;
        if (StringUtils.hasText(sigungucode)) {
            tops = statService.findSeoulTopList(sigungucode);
        } else {
            tops = new ArrayList<>();
        }
        model.addAttribute("list", tops);
        return "statistics/statSeoulTop";
    }

    @GetMapping("/statistics/gyunggi")
    public String gyunggiList(Model model) {
        log.info("/statistics/gyunggi");
        List<StatAreaYYMM> areas = statService.findStatGyunggiList();
        model.addAttribute("list", areas);
        return "statistics/statGyunggi";
    }

    @GetMapping("/statistics/gyunggi/{year}")
    public String gyunggiListYear(@PathVariable String year, Model model) {
        log.info("/statistics/gyunggi/" + year);
        List<StatAreaYYMM> areas;
        if (StringUtils.hasText(year)) {
            areas = statService.findStatGyunggiListYear(year);
        } else{
            areas = new ArrayList<>();
        }
        model.addAttribute("list", areas);
        return "statistics/statGyunggi";
    }

    @GetMapping("/statistics/gyunggi/usearea/{ua}")
    public String gyunggiListUA(@PathVariable String ua, Model model) {
        log.info("/statistics/gyunggi/usearea/" + ua);
        List<StatAreaYYMM> areas;
        if (StringUtils.hasText(ua)) {
            areas = statService.findStatGyunggiListUA(ua);
        } else{
            areas = new ArrayList<>();
        }
        model.addAttribute("list", areas);
        return "statistics/statGyunggi";
    }

    @GetMapping("/statistics/gyunggisi/{sidoCode}")
    public String gyunggisiList(@PathVariable String sidoCode, Model model) {
        log.info("/statistics/gyunggisi/" + sidoCode);
        List<StatSidoYYMM> areas;
        if (StringUtils.hasText(sidoCode)) {
            areas = statService.findStatGyunggiSiList(sidoCode);
        } else{
            areas = new ArrayList<>();
        }
        model.addAttribute("list", areas);
        return "statistics/statGyunggiSi";
    }

    @GetMapping("/statistics/gyunggi/top/{sidocode}")
    public String gyunggiTopList(@PathVariable String sidocode, Model model) {
        log.info("/statistics/gyunggi/top/" + sidocode);
        List<RankYear> tops;
        if (StringUtils.hasText(sidocode)) {
            tops = statService.findGyunggiTopList(sidocode);
        } else{
            tops = new ArrayList<>();
        }
        model.addAttribute("list", tops);
        return "statistics/statGyunggiTop";
    }
}
