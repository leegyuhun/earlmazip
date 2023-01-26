package com.earlmazip.controller;

import com.earlmazip.controller.dto.StatLeaseAnalysisDto;
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
public class LeaseAnalysisController {
    private final LeaseAnalysisService leaseAnalysisService;
    private final ApiCallStatService apiCallStatService;
    private final CodeInfoService codeInfoService;
    private final RequestService requestService;
    private final IpCountService ipCountService;
    private final IpBlockService ipBlockService;

    @GetMapping("/lease_analysis")
    public String getLeaseAnalysis(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                   @RequestParam(value = "term", defaultValue = "0") int term,
                                   HttpServletRequest request,
                                   Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        List<StatLeaseAnalysisDto> anals;
        String title = "-";
        if (!sigunguCode.equals("0")) {
            log.info("/lease_analysis?sigunguCode=" + sigunguCode);
            title = codeInfoService.getCodeName(sigunguCode);
            apiCallStatService.writeApiCallStat("LEASE_ANAL", "/lease_analysis?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                anals = leaseAnalysisService.getLeaseAnalysisList(sigunguCode, term);
            } else {
                anals = new ArrayList<>();
            }
        } else {
            anals = new ArrayList<>();
        }
        List<String> dates = anals.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Float> rates = anals.stream().map(o->new Float(o.getRate())).collect(Collectors.toList());
        Collections.reverse(dates);
        Collections.reverse(rates);

        model.addAttribute("title",  title);
        model.addAttribute("term", term);
        model.addAttribute("termStr", Common.makeTermString(term));
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("list", anals);
        model.addAttribute("dates", dates);
        model.addAttribute("rates", rates);

        if (sigunguCode.substring(0, 2).equals("11")) {
            return "lease_analysis/seoul";
        } else if (sigunguCode.substring(0, 2).equals("41")) {
            return "lease_analysis/gyunggi";
        } else {
            return "lease_analysis/incheon";
        }
    }
    @GetMapping("/lease_analysis/seoul/{gubncode}")
    public String getLeaseList_Seoul(@PathVariable String gubncode,
                                     HttpServletRequest request,
                                     Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        List<StatLeaseAnalysisDto> anals;
        String title = "-";
        if (!gubncode.equals("0")) {
            log.info("/lease_analysis/seoul/" + gubncode);
            title = codeInfoService.getCodeName(gubncode);
            apiCallStatService.writeApiCallStat("LEASE_ANAL", "/lease_analysis/seoul/" + title, gubncode);
            if (StringUtils.hasText(gubncode)) {
                anals = leaseAnalysisService.getLeaseAnalysisList(gubncode,5);
            } else {
                anals = new ArrayList<>();
            }
        } else {
            anals = new ArrayList<>();
        }
        List<String> dates = anals.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Float> rates = anals.stream().map(o->new Float(o.getRate())).collect(Collectors.toList());
        Collections.reverse(dates);
        Collections.reverse(rates);

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("list", anals);
        model.addAttribute("dates", dates);
        model.addAttribute("rates", rates);

        return "lease_analysis/seoul";
    }

    @GetMapping("/lease_analysis/gyunggi/{gubncode}")
    public String getLeaseList_Gyunggi(@PathVariable String gubncode, Model model) {
        List<StatLeaseAnalysisDto> anals;
        String title = "-";
        if (!gubncode.equals("0")) {
            title = codeInfoService.getCodeName(gubncode);
            log.info("/lease_analysis/gyunggi/" + gubncode);
            apiCallStatService.writeApiCallStat("LEASE_ANAL", "/lease_analysis/gyunggi/" + title, gubncode);
            if (StringUtils.hasText(gubncode)) {
                anals = leaseAnalysisService.getLeaseAnalysisList(gubncode,5);
            } else {
                anals = new ArrayList<>();
            }
        } else {
            anals = new ArrayList<>();
        }
        List<String> dates = anals.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Float> rates = anals.stream().map(o->new Float(o.getRate())).collect(Collectors.toList());
        Collections.reverse(dates);
        Collections.reverse(rates);

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("list", anals);
        model.addAttribute("dates", dates);
        model.addAttribute("rates", rates);

        return "lease_analysis/gyunggi";
    }

    @GetMapping("/lease_analysis/incheon/{gubncode}")
    public String getLeaseanalysis_Incheon(@PathVariable String gubncode, Model model) {
        List<StatLeaseAnalysisDto> anals;
        String title = "-";
        if (!gubncode.equals("0")) {
            log.info("/lease_analysis/incheon/" + gubncode);
            title = codeInfoService.getCodeName(gubncode);
            apiCallStatService.writeApiCallStat("LEASE_ANAL", "/lease_analysis/incheon/" + title, gubncode);
            if (StringUtils.hasText(gubncode)) {
                anals = leaseAnalysisService.getLeaseAnalysisList(gubncode,5);
            } else {
                anals = new ArrayList<>();
            }
        } else {
            anals = new ArrayList<>();
        }
        List<String> dates = anals.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Float> rates = anals.stream().map(o->new Float(o.getRate())).collect(Collectors.toList());
        Collections.reverse(dates);
        Collections.reverse(rates);

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("list", anals);
        model.addAttribute("dates", dates);
        model.addAttribute("rates", rates);

        return "lease_analysis/incheon";
    }
}
