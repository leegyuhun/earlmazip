package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.EcosDataResponseDto;
import aptdata.earlmazip.controller.dto.RankYearResponseDto;
import aptdata.earlmazip.controller.dto.StatLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatResponseDto;
import aptdata.earlmazip.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StatLeaseController {

    private final StatLeaseService statLeaseService;

    private final ApiCallStatService apiCallStatService;

    private final CodeInfoService codeInfoService;

    private final EcosDataService ecosDataService;

    @GetMapping("/stat_lease/gyunggiByCity/{sidocode}/{term}")
    public String getStatLeaseList_Gyunggi(@PathVariable String sidocode,
                                @PathVariable int term,
                                Model model) {
        List<StatLeaseResponseDto> stats;
        String title = "-";
        if (!sidocode.equals("0")) {
            title = codeInfoService.getCodeName(sidocode);
            log.info("/stat_lease/gyunggi/" + sidocode);
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease/gyunggi/" + title + "/" + term);
            if (StringUtils.hasText(sidocode)) {
                stats = statLeaseService.getStatLeaseList_Gyunggi(sidocode, term);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            stats = new ArrayList<>();
        }
        List<String> dates = stats.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Integer> avgDeposits = stats.stream().map(o->new Integer(o.getAvgDeposit())).collect(Collectors.toList());
        List<Integer> tradcnt = stats.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgDeposits);
        Collections.reverse(tradcnt);

        // 한국은행 기준금리
        List<EcosDataResponseDto> rates = ecosDataService.getEcosData("098Y001", "0101000", "",  "6");
        List<String> interestRates = rates.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());

        model.addAttribute("dates", dates);
        model.addAttribute("avgDeposits", avgDeposits);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("title",  "[ "+ title + " - 전세 통계 ]");
        model.addAttribute("interestRates", interestRates);
        model.addAttribute("list", stats);
        return "stat_lease/gyunggiByCity";
    }

    @GetMapping("/stat_lease/seoul/{sigungucode}/{term}")
    public String getStatLeaseList_Seoul(@PathVariable String sigungucode,
                                         @PathVariable int term,
                                         Model model) {
        List<StatLeaseResponseDto> stats;
        String title = "-";
        if (!sigungucode.equals("0")) {
            title = codeInfoService.getCodeName(sigungucode);
            log.info("/stat_lease/seoul/" + sigungucode);
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease/seoul/" + title + "/" + term);
            if (StringUtils.hasText(sigungucode)) {
                stats = statLeaseService.getStatLeaseList_Seoul(sigungucode, term);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            stats = new ArrayList<>();
        }
        List<String> dates = stats.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Integer> avgDeposits = stats.stream().map(o->new Integer(o.getAvgDeposit())).collect(Collectors.toList());
        List<Integer> tradcnt = stats.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgDeposits);
        Collections.reverse(tradcnt);

        // 한국은행 기준금리
        List<EcosDataResponseDto> rates = ecosDataService.getEcosData("098Y001", "0101000", "", "6");
        List<String> interestRates = rates.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());

        model.addAttribute("dates", dates);
        model.addAttribute("avgDeposits", avgDeposits);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("title",  "[ "+ title + " - 전세 통계 ]");
        model.addAttribute("interestRates", interestRates);
        model.addAttribute("list", stats);
        return "stat_lease/seoul";
    }

    @GetMapping("/stat_lease/seoul84/{sigungucode}/{term}")
    public String getStatLeaseList_Seoul84(@PathVariable String sigungucode,
                                         @PathVariable int term,
                                         Model model) {
        List<StatLeaseResponseDto> stats;
        String title = "-";
        if (!sigungucode.equals("0")) {
            title = codeInfoService.getCodeName(sigungucode);
            log.info("/stat_lease84/seoul/" + sigungucode);
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease/seoul84/" + title + "/" + term);
            if (StringUtils.hasText(sigungucode)) {
                stats = statLeaseService.getStatLeaseList_Seoul84(sigungucode, term);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            stats = new ArrayList<>();
        }
        List<String> dates = stats.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Integer> avgDeposits = stats.stream().map(o->new Integer(o.getAvgDeposit())).collect(Collectors.toList());
        List<Integer> tradcnt = stats.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgDeposits);
        Collections.reverse(tradcnt);

        // 한국은행 기준금리
        List<EcosDataResponseDto> rates = ecosDataService.getEcosData("098Y001", "0101000", "", "6");
        List<String> interestRates = rates.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());

        model.addAttribute("dates", dates);
        model.addAttribute("avgDeposits", avgDeposits);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("title",  "[ "+ title + " - 국평 전세 통계 ]");
        model.addAttribute("interestRates", interestRates);
        model.addAttribute("list", stats);
        return "stat_lease/seoul84";
    }

    @GetMapping("/stat_lease_monthly/seoul/sigungu/{sigungucode}")
    public String getStatLeaseMonthlySigungu(@PathVariable String sigungucode, Model model) {
        List<StatLeaseResponseDto> stats;
        String title = "-";
        if (!sigungucode.equals("0")) {
            title = codeInfoService.getCodeName(sigungucode);
            log.info("/stat_lease_monthly/seoul/sigungu/" + sigungucode);
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease_monthly/seoul/sigungu/" + title);
            if (StringUtils.hasText(sigungucode)) {
                stats = statLeaseService.getStatLeaseMonthlySigungu(sigungucode);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            stats = new ArrayList<>();
        }
        List<String> dates = stats.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Integer> avgMonthlyrent = stats.stream().map(o->new Integer(o.getAvgMonthlyrent())).collect(Collectors.toList());
        List<Integer> tradcnt = stats.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgMonthlyrent);
        Collections.reverse(tradcnt);

        // 한국은행 기준금리
        List<EcosDataResponseDto> rates = ecosDataService.getEcosData("098Y001", "0101000", "", "6");
        List<String> interestRates = rates.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());

        model.addAttribute("dates", dates);
        model.addAttribute("avgMonthlyrent", avgMonthlyrent);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("interestRates", interestRates);
        model.addAttribute("list", stats);
        return "stat_lease/statMonthlySeoul_Sigungu";
    }

    @GetMapping("/stat_lease_monthly/sido/{sidocode}")
    public String statLeaseMonthlySido(@PathVariable String sidocode, Model model) {
        List<StatLeaseResponseDto> stats;
        String title = "-";
        if (!sidocode.equals("0")) {
            title = codeInfoService.getCodeName(sidocode);
            log.info("/stat_lease_monthly/sido/" + sidocode);
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease_monthly/sido/" + title);
            if (StringUtils.hasText(sidocode)) {
                stats = statLeaseService.statLeaseMonthlySido(sidocode);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            stats = new ArrayList<>();
        }
        List<String> dates = stats.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Integer> avgMonthlyRent = stats.stream().map(o->new Integer(o.getAvgMonthlyrent())).collect(Collectors.toList());
        List<Integer> tradcnt = stats.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgMonthlyRent);
        Collections.reverse(tradcnt);

        model.addAttribute("dates", dates);
        model.addAttribute("avgMonthlyRent", avgMonthlyRent);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("list", stats);
        return "stat_lease/statSido_monthly";
    }

}
