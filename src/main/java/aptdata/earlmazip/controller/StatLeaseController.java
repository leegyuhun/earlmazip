package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.*;
import aptdata.earlmazip.service.*;
import aptdata.earlmazip.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease/gyunggi/" + title + "/" + term, sidocode);
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

        // ???????????? ????????????
        List<EcosDataResponseDto> rates = ecosDataService.getEcosData("722Y001", "0101000", "",  "6");
        List<String> interestRates = rates.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());

        model.addAttribute("dates", dates);
        model.addAttribute("avgDeposits", avgDeposits);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("title",  "[ "+ title + " - ?????? ?????? ]");
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
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease/seoul/" + title + "/" + term, sigungucode);
            if (StringUtils.hasText(sigungucode)) {
                stats = statLeaseService.getStatLeaseList_Seoul(sigungucode, term);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            stats = new ArrayList<>();
        }
        List<String> dates = stats.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Float> avgDeposits = stats.stream().map(o->new Float((float) o.getAvgDeposit()/10000)).collect(Collectors.toList());
        List<Integer> tradcnt = stats.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgDeposits);
        Collections.reverse(tradcnt);

        // ???????????? ????????????
        List<EcosDataResponseDto> rates = ecosDataService.getEcosData("722Y001", "0101000", "", Integer.toString(term));
        List<String> interestRates = rates.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());

        model.addAttribute("dates", dates);
        model.addAttribute("avgDeposits", avgDeposits);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("title",  title);
        model.addAttribute("interestRates", interestRates);
        model.addAttribute("list", stats);
        model.addAttribute("term", term);
        model.addAttribute("sigungucode", sigungucode);
        model.addAttribute("termStr", Common.makeTermString(term));
        return "stat_lease/seoul";
    }

    /**
     * ?????? ?????? ????????? ????????????
     * @param sigungucode
     * @param term
     * @param model
     * @return
     */
    @GetMapping("/stat_lease/seoul84/{sigungucode}/{gubn}/{term}")
    public String getStatLeaseList_Seoul84(@PathVariable String sigungucode,
                                           @PathVariable int gubn,
                                         @PathVariable int term,
                                         Model model) {
        List<StatLeaseResponseDto> stats;
        String title = "-";
        if (!sigungucode.equals("0")) {
            title = codeInfoService.getCodeName(sigungucode);
            log.info("/stat_lease84/seoul/" + sigungucode);
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease/seoul84/" + title + "/" + gubn + "/" + term, sigungucode);
            if (StringUtils.hasText(sigungucode)) {
                stats = statLeaseService.getStatLeaseList_Seoul84(sigungucode, gubn, term);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            stats = new ArrayList<>();
        }
        List<String> dates = stats.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Float> avgDeposits = stats.stream().map(o->new Float((float)o.getAvgDeposit()/10000)).collect(Collectors.toList());
        List<Integer> avgMonthlyRents = stats.stream().map(o->new Integer(o.getAvgMonthlyrent())).collect(Collectors.toList());
        List<Integer> tradcnt = stats.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgDeposits);
        Collections.reverse(avgMonthlyRents);
        Collections.reverse(tradcnt);

        // ???????????? ????????????
        List<EcosDataResponseDto> rates = ecosDataService.getEcosData("722Y001", "0101000", "", Integer.toString(term));
        List<String> interestRates = rates.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());

        model.addAttribute("dates", dates);
        model.addAttribute("avgDeposits", avgDeposits);
        model.addAttribute("avgMonthlyRents", avgMonthlyRents);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("title", title);
        model.addAttribute("term", term);
        model.addAttribute("sigungucode", sigungucode);
        model.addAttribute("termStr", Common.makeTermString(term));
        model.addAttribute("interestRates", interestRates);
        model.addAttribute("list", stats);
        if (gubn == 0) {
            return "stat_lease/seoul84";
        } else {
            return "stat_lease/seoul_Monthly84";
        }
    }

    @GetMapping("/stat_lease/seoul84/{sigungucode}/{term}") // ?????? ??????
    public String getStatLeaseList_Seoul84Bak(@PathVariable String sigungucode,
                                           @PathVariable int term,
                                           Model model) {
        List<StatLeaseResponseDto> stats;
        String title = "-";
        if (!sigungucode.equals("0")) {
            title = codeInfoService.getCodeName(sigungucode);
            log.info("/stat_lease84/seoul/" + sigungucode);
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease/seoul84/" + title + "/" + term, sigungucode);
            if (StringUtils.hasText(sigungucode)) {
                stats = statLeaseService.getStatLeaseList_Seoul84(sigungucode, 0, term);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            stats = new ArrayList<>();
        }
        List<String> dates = stats.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Float> avgDeposits = stats.stream().map(o->new Float((float)o.getAvgDeposit()/10000)).collect(Collectors.toList());
        List<Integer> tradcnt = stats.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgDeposits);
        Collections.reverse(tradcnt);

        // ???????????? ????????????
        List<EcosDataResponseDto> rates = ecosDataService.getEcosData("722Y001", "0101000", "", Integer.toString(term));
        List<String> interestRates = rates.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());

        model.addAttribute("dates", dates);
        model.addAttribute("avgDeposits", avgDeposits);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("title", title);
        model.addAttribute("term", term);
        model.addAttribute("sigungucode", sigungucode);
        model.addAttribute("termStr", Common.makeTermString(term));
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
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease_monthly/seoul/sigungu/" + title, sigungucode);
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

        // ???????????? ????????????
        List<EcosDataResponseDto> rates = ecosDataService.getEcosData("722Y001", "0101000", "", "6");
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
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease_monthly/sido/" + title, sidocode);
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

    @GetMapping("/stat_lease/top")
    public String getTopLeaseSigungu(@RequestParam(value="sigunguCode", defaultValue = "") String sigunguCode,
                                     @RequestParam(value="uaType", defaultValue = "UA01") String uaType,
                                      @RequestParam(value="leaseType", defaultValue = "0") int leaseType,
                                      Model model) {
        List<RankLeaseResponseDto> ranks;
        String title = "-";
        if (!sigunguCode.equals("0")) {
            title = codeInfoService.getCodeName(sigunguCode);
            log.info("/stat_lease/top?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&leaseType=" + leaseType);
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease/top?sigunguCode=" + title + "&uaType=" + uaType + "&leaseType=" + leaseType, sigunguCode);

            if (StringUtils.hasText(sigunguCode)) {
                ranks = statLeaseService.getTopLeaseSigungu(sigunguCode, uaType, leaseType);
            } else {
                ranks = new ArrayList<>();
            }
        } else {
            ranks = new ArrayList<>();
        }

        model.addAttribute("list", ranks);
        model.addAttribute("sigungucode", sigunguCode);
        model.addAttribute("uatype", uaType);
        model.addAttribute("leasetype", leaseType);
        if (leaseType == 0) {
            model.addAttribute("title",  title + " 2022 ?????? TOP 100");
        } else {
            model.addAttribute("title",  title + " 2022 ?????? TOP 100");
        }
        model.addAttribute("subtitle", codeInfoService.getCodeName(uaType));

        if (sigunguCode.substring(0, 2).equals("11")) {
            return "stat_lease/seoulTop";
        } else if (sigunguCode.substring(0, 2).equals("41")) {
            return "stat_lease/gyunggiTop";
        } else {
            return "stat_lease/incheonTop";
        }
    }
}
