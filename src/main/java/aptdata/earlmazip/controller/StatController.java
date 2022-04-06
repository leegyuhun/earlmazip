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

import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;

    private final ApiCallStatService apiCallStatService;

    @GetMapping("/stat_trade/seoul")
    public String seoulList(Model model) {
        log.info("/stat_trade/seoul");
        apiCallStatService.writeApiCallStat("STAT", "/stat_trade/seoul");
        List<StatResponseDto> areas = statService.findStatSeoulList();
        List<String> dates = areas.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Integer> avgprc = areas.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> tradcnt = areas.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        Integer maxCnt = 0;
        if (tradcnt.size() > 0) {
            maxCnt = tradcnt.stream().max(Comparator.comparing(x -> x)).orElseThrow(NoSuchElementException::new);
            maxCnt = maxCnt * 2;
        }

        Collections.reverse(dates);
        Collections.reverse(avgprc);
        Collections.reverse(tradcnt);

        model.addAttribute("list", areas);
        model.addAttribute("dates", dates);
        model.addAttribute("avgprc", avgprc);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("maxcnt", maxCnt);
        return "stat_trade/seoul";
    }

    @GetMapping("/stat_trade/seoul/{year}")
    public String seoulListYear(@PathVariable String year, Model model) {
        log.info("/stat_trade/seoul/" + year);
        apiCallStatService.writeApiCallStat("STAT", "/stat_trade/seoul/" + year);
        List<StatResponseDto> areas;
        if (StringUtils.hasText(year)) {
            areas = statService.findStatSeoulListYear(year);
        }
        else{
            areas = new ArrayList<>();
        }
        model.addAttribute("list", areas);
        return "stat_trade/seoul";
    }

    @GetMapping("/stat_trade/seoul/usearea/{ua}")
    public String seoulListUA(@PathVariable String ua, Model model) {
        log.info("/stat_trade/seoul/usearea/" + ua);
        apiCallStatService.writeApiCallStat("STAT", "/stat_trade/seoul/usearea/" + ua);
        List<StatResponseDto> areas;
        if (StringUtils.hasText(ua)) {
            areas = statService.findStatSeoulListUA(ua);
        }
        else{
            areas = new ArrayList<>();
        }
        List<String> dates = areas.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Integer> avgprc = areas.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> tradcnt = areas.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        Integer maxCnt = 0;
        if (tradcnt.size() > 0) {
            maxCnt = tradcnt.stream().max(Comparator.comparing(x -> x)).orElseThrow(NoSuchElementException::new);
            maxCnt = maxCnt * 2;
        }

        Collections.reverse(dates);
        Collections.reverse(avgprc);
        Collections.reverse(tradcnt);

        model.addAttribute("list", areas);
        model.addAttribute("dates", dates);
        model.addAttribute("avgprc", avgprc);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("maxcnt", maxCnt);
        return "stat_trade/seoul";
    }

    @GetMapping("/stat_trade/seoul/top/{year}/{sigungucode}")
    public String seoulTopList(@PathVariable String year,
                               @PathVariable String sigungucode, Model model) {
        List<RankYear> tops;
        if (!sigungucode.equals("0")) {
            log.info("/stat_trade/seoul/top/" + year + "/" +  sigungucode);
            apiCallStatService.writeApiCallStat("TOP", "/stat_trade/seoul/top/" + sigungucode);
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
        return "stat_trade/seoulTop";
    }

    @GetMapping("/stat_trade/gyunggi")
    public String gyunggiList(Model model) {
        log.info("/stat_trade/gyunggi");
        apiCallStatService.writeApiCallStat("STAT", "/stat_trade/gyunggi");
        List<StatResponseDto> areas = statService.findStatGyunggiList();
        List<String> dates = areas.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Integer> avgprc = areas.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> tradcnt = areas.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        Integer maxCnt = 0;
        if (tradcnt.size() > 0) {
            maxCnt = tradcnt.stream().max(Comparator.comparing(x -> x)).orElseThrow(NoSuchElementException::new);
            maxCnt = maxCnt * 2;
        }

        Collections.reverse(dates);
        Collections.reverse(avgprc);
        Collections.reverse(tradcnt);

        model.addAttribute("list", areas);
        model.addAttribute("dates", dates);
        model.addAttribute("avgprc", avgprc);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("maxcnt", maxCnt);
        return "stat_trade/gyunggi";
    }

    @GetMapping("/stat_trade/gyunggi/{year}")
    public String gyunggiListYear(@PathVariable String year, Model model) {
        log.info("/stat_trade/gyunggi/" + year);
        apiCallStatService.writeApiCallStat("STAT", "/stat_trade/gyunggi/" + year);
        List<StatResponseDto> areas;
        if (StringUtils.hasText(year)) {
            areas = statService.findStatGyunggiListYear(year);
        } else{
            areas = new ArrayList<>();
        }
        model.addAttribute("list", areas);
        return "stat_trade/gyunggi";
    }

    @GetMapping("/stat_trade/gyunggi/usearea/{ua}")
    public String gyunggiListUA(@PathVariable String ua, Model model) {
        log.info("/stat_trade/gyunggi/usearea/" + ua);
        apiCallStatService.writeApiCallStat("STAT", "/stat_trade/gyunggi/usearea/" + ua);
        List<StatResponseDto> areas;
        if (StringUtils.hasText(ua)) {
            areas = statService.findStatGyunggiListUA(ua);
        } else{
            areas = new ArrayList<>();
        }
        model.addAttribute("list", areas);
        return "stat_trade/gyunggi";
    }

    @GetMapping("/stat_trade/gyunggiByCity/{sidoCode}")
    public String gyunggisiList(@PathVariable String sidoCode, Model model) {
        List<StatResponseDto> stats;
        if (!sidoCode.equals("0")) {
            log.info("/stat_trade/gyunggiByCity/" + sidoCode);
            apiCallStatService.writeApiCallStat("STAT", "/stat_trade/gyunggiByCity/" + sidoCode);
            if (StringUtils.hasText(sidoCode)) {
                stats = statService.findStatGyunggiSiList(sidoCode);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            stats = new ArrayList<>();
        }
        List<String> dates = stats.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Integer> avgprc = stats.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> tradcnt = stats.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        Integer maxCnt = 0;
        if (tradcnt.size() > 0) {
            maxCnt = tradcnt.stream().max(Comparator.comparing(x -> x)).orElseThrow(NoSuchElementException::new);
            maxCnt = maxCnt * 2;
        }


        Collections.reverse(dates);
        Collections.reverse(avgprc);
        Collections.reverse(tradcnt);

        model.addAttribute("list", stats);
        model.addAttribute("dates", dates);
        model.addAttribute("avgprc", avgprc);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("maxcnt", maxCnt);
        return "stat_trade/gyunggiByCity";
    }

    @GetMapping("/stat_trade/gyunggi/top/{year}/{sidocode}")
    public String gyunggiTopList(@PathVariable String year,
                                 @PathVariable String sidocode, Model model) {
        List<RankYear> tops;
        if (!sidocode.equals("0")) {
            log.info("/stat_trade/gyunggi/top/" + year + "/" + sidocode);
            apiCallStatService.writeApiCallStat("TOP", "/stat_trade/gyunggi/top/" + sidocode);
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
        return "stat_trade/gyunggiByCityTop";
    }

    @GetMapping("/stat_trade/incheon/top/{year}/{sigungucode}")
    public String incheonTopList(@PathVariable String year,
                                 @PathVariable String sigungucode, Model model) {
        List<RankYear> tops;
        if (!sigungucode.equals("0")) {
            log.info("/stat_trade/incheon/top/" + year + "/" + sigungucode);
            apiCallStatService.writeApiCallStat("TOP", "/stat_trade/incheon/top/" + year + "/" + sigungucode);
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
        return "stat_trade/incheonTop";
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
