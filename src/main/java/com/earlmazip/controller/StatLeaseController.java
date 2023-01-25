package com.earlmazip.controller;

import com.earlmazip.controller.dto.*;
import com.earlmazip.service.*;
import com.earlmazip.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
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

    private final RequestService requestService;

    @GetMapping("/stat_lease")
    public String getStatLeaseList(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                   @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                   @RequestParam(value = "term", defaultValue = "0") String term,
                                   Model model) {
        List<StatLeaseResponseDto> stats;
        String title = "-";
        if (!sigunguCode.equals("0")) {
            title = codeInfoService.getCodeName(sigunguCode);
            log.info("/stat_lease?" + sigunguCode);
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                stats = statLeaseService.getStatLeaseList(sigunguCode, uaType, term);
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

        // 한국은행 기준금리
        List<EcosDataResponseDto> rates = ecosDataService.getEcosData("722Y001", "0101000", "", term);
        List<String> interestRates = rates.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());

        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("dates", dates);
        model.addAttribute("avgDeposits", avgDeposits);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("title",  title);
        model.addAttribute("interestRates", interestRates);
        model.addAttribute("list", stats);
        model.addAttribute("term", term);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("termStr", Common.makeTermString(term));
        if (sigunguCode.substring(0, 2).equals("11")) {
            return "stat_lease/seoul";
        } else if (sigunguCode.substring(0, 2).equals("41")) {
            return "stat_lease/gyunggi";
        } else {
            return "stat_lease/incheon";
        }
    }

    @GetMapping("/stat_lease/top")
    public String getTopLeaseSigungu(@RequestParam(value="sigunguCode", defaultValue = "") String sigunguCode,
                                     @RequestParam(value="uaType", defaultValue = "UA01") String uaType,
                                      @RequestParam(value="leaseType", defaultValue = "0") int leaseType,
                                     @RequestParam(value="dealYear", defaultValue = "2022") int dealYear,
                                     HttpServletRequest request,
                                      Model model) throws UnknownHostException {
        List<RankLeaseResponseDto> ranks;
        String title = "-";
        requestService.getClientIPAddress(request);
        if (!sigunguCode.equals("0")) {
            title = codeInfoService.getCodeName(sigunguCode);
            log.info("/stat_lease/top?dealYear="+dealYear+"&sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&leaseType=" + leaseType);
            apiCallStatService.writeApiCallStat("STAT_LEASE", "/stat_lease/top?dealYear="+dealYear+"&sigunguCode=" + title + "&uaType=" + uaType + "&leaseType=" + leaseType, sigunguCode);

            if (StringUtils.hasText(sigunguCode)) {
                ranks = statLeaseService.getTopLeaseSigungu(sigunguCode, uaType, leaseType, dealYear);
            } else {
                ranks = new ArrayList<>();
            }
        } else {
            ranks = new ArrayList<>();
        }

        model.addAttribute("list", ranks);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("leaseType", leaseType);
        model.addAttribute("dealYear", dealYear);
        if (leaseType == 0) {
            model.addAttribute("title",  title + " "  + dealYear + " 전세 TOP 100");
        } else {
            model.addAttribute("title",  title + " "  + dealYear + " 월세 TOP 100");
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
