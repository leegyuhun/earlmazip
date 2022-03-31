package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.AptLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatLeaseAnalysisDto;
import aptdata.earlmazip.service.ApiCallStatService;
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
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LeaseAnalysisController {
    private final LeaseAnalysisService leaseAnalysisService;
    private final ApiCallStatService apiCallStatService;

    @GetMapping("/lease_analysis/seoul/{gubncode}")
    public String getLeaseList_Seoul(@PathVariable String gubncode, Model model) {
        log.info("/lease_analysis/seoul/" + gubncode);
        apiCallStatService.writeApiCallStat("LEASE_ANAL", "SEOUL");
        List<StatLeaseAnalysisDto> anals;
        if (StringUtils.hasText(gubncode)) {
            anals = leaseAnalysisService.getLeaseAnalysisList(gubncode);
        } else {
            anals = new ArrayList<>();
        }
        model.addAttribute("list", anals);

        return "lease_analysis/seoul";
    }

    @GetMapping("/lease_analysis/gyunggi/{gubncode}")
    public String getLeaseList_Gyunggi(@PathVariable String gubncode, Model model) {
        log.info("/lease_analysis/gyunggi/" + gubncode);
        apiCallStatService.writeApiCallStat("LEASE_ANAL", "GYUNGGI");
        List<StatLeaseAnalysisDto> anals;
        if (StringUtils.hasText(gubncode)) {
            anals = leaseAnalysisService.getLeaseAnalysisList(gubncode);
        } else {
            anals = new ArrayList<>();
        }
        model.addAttribute("list", anals);

        return "lease_analysis/gyunggi";
    }
}
