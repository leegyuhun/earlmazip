package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.AptLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatLeaseAnalysisDto;
import aptdata.earlmazip.service.ApiCallStatService;
import aptdata.earlmazip.service.CodeInfoService;
import aptdata.earlmazip.service.LeaseAnalysisService;
import aptdata.earlmazip.service.LeaseService;
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
public class LeaseAnalysisController {
    private final LeaseAnalysisService leaseAnalysisService;
    private final ApiCallStatService apiCallStatService;
    private final CodeInfoService codeInfoService;

    @GetMapping("/lease_analysis/seoul/{gubncode}")
    public String getLeaseList_Seoul(@PathVariable String gubncode, Model model) {
        List<StatLeaseAnalysisDto> anals;
        if (!gubncode.equals("0")) {
            log.info("/lease_analysis/seoul/" + gubncode);
            apiCallStatService.writeApiCallStat("LEASE_ANAL", "/lease_analysis/seoul/" + gubncode);
            if (StringUtils.hasText(gubncode)) {
                anals = leaseAnalysisService.getLeaseAnalysisList(gubncode);
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

        String title = codeInfoService.getCodeName(gubncode);

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("list", anals);
        model.addAttribute("dates", dates);
        model.addAttribute("rates", rates);

        return "lease_analysis/seoul";
    }

    @GetMapping("/lease_analysis/gyunggi/{gubncode}")
    public String getLeaseList_Gyunggi(@PathVariable String gubncode, Model model) {
        List<StatLeaseAnalysisDto> anals;
        if (!gubncode.equals("0")) {
            log.info("/lease_analysis/gyunggi/" + gubncode);
            apiCallStatService.writeApiCallStat("LEASE_ANAL", "/lease_analysis/gyunggi/" + gubncode);
            if (StringUtils.hasText(gubncode)) {
                anals = leaseAnalysisService.getLeaseAnalysisList(gubncode);
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
        String title = codeInfoService.getCodeName(gubncode);

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("list", anals);
        model.addAttribute("dates", dates);
        model.addAttribute("rates", rates);

        return "lease_analysis/gyunggi";
    }

    @GetMapping("/lease_analysis/incheon/{gubncode}")
    public String getLeaseanalysis_Incheon(@PathVariable String gubncode, Model model) {
        List<StatLeaseAnalysisDto> anals;
        if (!gubncode.equals("0")) {
            log.info("/lease_analysis/incheon/" + gubncode);
            apiCallStatService.writeApiCallStat("LEASE_ANAL", "/lease_analysis/incheon/" + gubncode);
            if (StringUtils.hasText(gubncode)) {
                anals = leaseAnalysisService.getLeaseAnalysisList(gubncode);
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
        String title = codeInfoService.getCodeName(gubncode);

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("list", anals);
        model.addAttribute("dates", dates);
        model.addAttribute("rates", rates);

        return "lease_analysis/incheon";
    }
}
