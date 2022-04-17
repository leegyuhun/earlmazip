package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.AptLeaseResponseDto;
import aptdata.earlmazip.controller.dto.AptPriceResponseDto;
import aptdata.earlmazip.service.ApiCallStatService;
import aptdata.earlmazip.service.CodeInfoService;
import aptdata.earlmazip.service.LeaseService;
import aptdata.earlmazip.service.TradeService;
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
public class LeaseController {
    private final LeaseService leaseService;
    private final ApiCallStatService apiCallStatService;
    private final CodeInfoService codeInfoService;

    @GetMapping("/leaselist/seoul/{sigungucode}")
    public String getLeaseList_Seoul(@PathVariable String sigungucode, Model model) {
        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (!sigungucode.equals("0")) {
            log.info("/leaselist/seoul/" + sigungucode);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("LEASE", "/leaselist/seoul/" + title);
            if (StringUtils.hasText(sigungucode)) {
                trads = leaseService.getLeaseList_SeoulSigungu(sigungucode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("list", trads);

        return "leaselist/seoul";
    }

    @GetMapping("/leaselist/gyunggi/{sidocode}")
    public String getLeaseList_Gyunggi(@PathVariable String sidocode, Model model) {
        List<AptLeaseResponseDto> trads;
        String title = "-";
        if (!sidocode.equals("0")) {
            log.info("/leaselist/gyunggi/" + sidocode);
            title = codeInfoService.getCodeName(sidocode);
            apiCallStatService.writeApiCallStat("LEASE", "/leaselist/gyunggi/" + title);
            if (StringUtils.hasText(sidocode)) {
                trads = leaseService.getLeaseList_GyunggiSido(sidocode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("list", trads);

        return "leaselist/gyunggi";
    }

    @GetMapping("/leaselist/incheon/{sigungucode}")
    public String getLeaseList_Incheon(@PathVariable String sigungucode, Model model) {
        List<AptLeaseResponseDto> trads;
        String title = "-";
        if (!sigungucode.equals("0")) {
            log.info("/leaselist/incheon/" + sigungucode);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("LEASE", "/leaselist/incheon/" + title);
            if (StringUtils.hasText(sigungucode)) {
                trads = leaseService.getLeaseList_IncheonSigungu(sigungucode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("list", trads);

        return "leaselist/incheon";
    }
}
