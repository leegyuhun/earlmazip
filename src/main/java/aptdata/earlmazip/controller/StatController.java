package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.StatLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatResponseDto;
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
        List<StatResponseDto> areas = statService.findStatSeoulList();
        model.addAttribute("list", areas);
        return "statistics/statSeoul";
    }

    @GetMapping("/statistics/seoul/{year}")
    public String seoulListYear(@PathVariable String year, Model model) {
        log.info("/statistics/seoul/" + year);
        List<StatResponseDto> areas;
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
        List<StatResponseDto> areas;
        if (StringUtils.hasText(ua)) {
            areas = statService.findStatSeoulListUA(ua);
        }
        else{
            areas = new ArrayList<>();
        }
        model.addAttribute("list", areas);
        return "statistics/statSeoul";
    }

    @GetMapping("/statistics/seoul/top/{year}/{sigungucode}")
    public String seoulTopList(@PathVariable String year,
                               @PathVariable String sigungucode, Model model) {
        log.info("/statistics/seoul/top/" + sigungucode);
        List<RankYear> tops;
        if (StringUtils.hasText(sigungucode)) {
            tops = statService.findSeoulTopList(year, sigungucode);
        } else {
            tops = new ArrayList<>();
        }
        model.addAttribute("list", tops);
        model.addAttribute("year", year);
        return "statistics/statSeoulTop";
    }

    @GetMapping("/statistics/gyunggi")
    public String gyunggiList(Model model) {
        log.info("/statistics/gyunggi");
        List<StatResponseDto> areas = statService.findStatGyunggiList();
        model.addAttribute("list", areas);
        return "statistics/statGyunggi";
    }

    @GetMapping("/statistics/gyunggi/{year}")
    public String gyunggiListYear(@PathVariable String year, Model model) {
        log.info("/statistics/gyunggi/" + year);
        List<StatResponseDto> areas;
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
        List<StatResponseDto> areas;
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
        List<StatResponseDto> areas;
        if (StringUtils.hasText(sidoCode)) {
            areas = statService.findStatGyunggiSiList(sidoCode);
        } else{
            areas = new ArrayList<>();
        }
        model.addAttribute("list", areas);
        return "statistics/statGyunggiSi";
    }

    @GetMapping("/statistics/gyunggi/top/{year}/{sidocode}")
    public String gyunggiTopList(@PathVariable String year,
                                 @PathVariable String sidocode, Model model) {
        log.info("/statistics/gyunggi/top/" + sidocode);
        List<RankYear> tops;
        if (StringUtils.hasText(sidocode)) {
            tops = statService.findGyunggiTopList(year, sidocode);
        } else{
            tops = new ArrayList<>();
        }
        model.addAttribute("list", tops);
        model.addAttribute("year", year);
        return "statistics/statGyunggiTop";
    }

    @GetMapping("/stat_lease/sido/{sidocode}")
    public String statLeaseSido(@PathVariable String sidocode, Model model) {
        log.info("/stat_lease/sido/" + sidocode);
        List<StatLeaseResponseDto> stats;
        if (StringUtils.hasText(sidocode)) {
            stats = statService.statLeaseSido(sidocode);
        } else{
            stats = new ArrayList<>();
        }
        model.addAttribute("list", stats);
        return "stat_lease/statSido";
    }

    @GetMapping("/stat_lease_monthly/sido/{sidocode}")
    public String statLeaseMonthlySido(@PathVariable String sidocode, Model model) {
        log.info("/stat_lease_monthly/sido/" + sidocode);
        List<StatLeaseResponseDto> stats;
        if (StringUtils.hasText(sidocode)) {
            stats = statService.statLeaseMonthlySido(sidocode);
        } else{
            stats = new ArrayList<>();
        }
        model.addAttribute("list", stats);
        return "stat_lease/statSido_monthly";
    }
}
