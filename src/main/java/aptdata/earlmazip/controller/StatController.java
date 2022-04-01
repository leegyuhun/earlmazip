package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.StatLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatResponseDto;
import aptdata.earlmazip.domain.RankYear;
import aptdata.earlmazip.domain.StatAreaYYMM;
import aptdata.earlmazip.domain.StatSidoYYMM;
import aptdata.earlmazip.service.ApiCallStatService;
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

    private final ApiCallStatService apiCallStatService;

    @GetMapping("/statistics/seoul")
    public String seoulList(Model model) {
        log.info("/statistics/seoul");
        apiCallStatService.writeApiCallStat("STAT", "/statistics/seoul");
        List<StatResponseDto> areas = statService.findStatSeoulList();
        model.addAttribute("list", areas);
        return "statistics/statSeoul";
    }

    @GetMapping("/statistics/seoul/{year}")
    public String seoulListYear(@PathVariable String year, Model model) {
        log.info("/statistics/seoul/" + year);
        apiCallStatService.writeApiCallStat("STAT", "/statistics/seoul/" + year);
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
        apiCallStatService.writeApiCallStat("STAT", "/statistics/seoul/usearea/" + ua);
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
        List<RankYear> tops;
        if (!sigungucode.equals("0")) {
            log.info("/statistics/seoul/top/" + year + "/" +  sigungucode);
            apiCallStatService.writeApiCallStat("TOP", "/statistics/seoul/top/" + sigungucode);
            if (StringUtils.hasText(sigungucode)) {
                tops = statService.findSeoulTopList(year, sigungucode);
            } else {
                tops = new ArrayList<>();
            }
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
        apiCallStatService.writeApiCallStat("STAT", "/statistics/gyunggi");
        List<StatResponseDto> areas = statService.findStatGyunggiList();
        model.addAttribute("list", areas);
        return "statistics/statGyunggi";
    }

    @GetMapping("/statistics/gyunggi/{year}")
    public String gyunggiListYear(@PathVariable String year, Model model) {
        log.info("/statistics/gyunggi/" + year);
        apiCallStatService.writeApiCallStat("STAT", "/statistics/gyunggi/" + year);
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
        apiCallStatService.writeApiCallStat("STAT", "/statistics/gyunggi/usearea/" + ua);
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
        List<StatResponseDto> stats;
        if (!sidoCode.equals("0")) {
            log.info("/statistics/gyunggisi/" + sidoCode);
            apiCallStatService.writeApiCallStat("STAT", "/statistics/gyunggisi/" + sidoCode);
            if (StringUtils.hasText(sidoCode)) {
                stats = statService.findStatGyunggiSiList(sidoCode);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            stats = new ArrayList<>();
        }
        model.addAttribute("list", stats);
        return "statistics/statGyunggiSi";
    }

    @GetMapping("/statistics/gyunggi/top/{year}/{sidocode}")
    public String gyunggiTopList(@PathVariable String year,
                                 @PathVariable String sidocode, Model model) {
        List<RankYear> tops;
        if (!sidocode.equals("0")) {
            log.info("/statistics/gyunggi/top/" + year + "/" + sidocode);
            apiCallStatService.writeApiCallStat("TOP", "/statistics/gyunggi/top/" + sidocode);
            if (StringUtils.hasText(sidocode)) {
                tops = statService.findGyunggiTopList(year, sidocode);
            } else {
                tops = new ArrayList<>();
            }
        } else {
            tops = new ArrayList<>();
        }
        model.addAttribute("list", tops);
        model.addAttribute("year", year);
        return "statistics/statGyunggiTop";
    }

    @GetMapping("/statistics/incheon/top/{year}/{sigungucode}")
    public String incheonTopList(@PathVariable String year,
                                 @PathVariable String sigungucode, Model model) {
        List<RankYear> tops;
        if (!sigungucode.equals("0")) {
            log.info("/statistics/incheon/top/" + year + "/" + sigungucode);
            apiCallStatService.writeApiCallStat("TOP", "/statistics/incheon/top/" + year + "/" + sigungucode);
            if (StringUtils.hasText(sigungucode)) {
                tops = statService.findIncheonTopList(year, sigungucode);
            } else {
                tops = new ArrayList<>();
            }
        } else {
            tops = new ArrayList<>();
        }
        model.addAttribute("list", tops);
        model.addAttribute("year", year);
        return "statistics/statIncheonTop";
    }

    @GetMapping("/stat_lease/sido/{sidocode}")
    public String statLeaseSido(@PathVariable String sidocode, Model model) {
        List<StatLeaseResponseDto> stats;
        if (!sidocode.equals("0")) {
            log.info("/stat_lease/sido/" + sidocode);
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease/sido/" + sidocode);
            if (StringUtils.hasText(sidocode)) {
                stats = statService.statLeaseSido(sidocode);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            stats = new ArrayList<>();
        }
        model.addAttribute("list", stats);
        return "stat_lease/statSido";
    }

    @GetMapping("/stat_lease_monthly/sido/{sidocode}")
    public String statLeaseMonthlySido(@PathVariable String sidocode, Model model) {
        List<StatLeaseResponseDto> stats;
        if (!sidocode.equals("0")) {
            log.info("/stat_lease_monthly/sido/" + sidocode);
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease_monthly/sido/" + sidocode);
            if (StringUtils.hasText(sidocode)) {
                stats = statService.statLeaseMonthlySido(sidocode);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            stats = new ArrayList<>();
        }
        model.addAttribute("list", stats);
        return "stat_lease/statSido_monthly";
    }
}
