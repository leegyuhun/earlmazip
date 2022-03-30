package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.AptLeaseResponseDto;
import aptdata.earlmazip.controller.dto.AptPriceResponseDto;
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

    @GetMapping("/leaselist/seoul/{sigungucode}")
    public String getLeaseList_Seoul(@PathVariable String sigungucode, Model model) {
        log.info("/leaselist/seoul/" + sigungucode);
        List<AptLeaseResponseDto> trads;
        if (StringUtils.hasText(sigungucode)) {
            trads = leaseService.getLeaseList_SeoulSigungu(sigungucode);
        } else {
            trads = new ArrayList<>();
        }
        model.addAttribute("list", trads);

        return "leaselist/seoul";
    }

    @GetMapping("/leaselist/gyunggi/{sidocode}")
    public String getLeaseList_Gyunggi(@PathVariable String sidocode, Model model) {
        log.info("/leaselist/gyunggi/" + sidocode);
        List<AptLeaseResponseDto> trads;
        if (StringUtils.hasText(sidocode)) {
            trads = leaseService.getLeaseList_GyunggiSido(sidocode);
        } else {
            trads = new ArrayList<>();
        }
        model.addAttribute("list", trads);

        return "leaselist/gyunggi";
    }

    @GetMapping("/leaselist/incheon/{sigungucode}")
    public String getLeaseList_Incheon(@PathVariable String sigungucode, Model model) {
        log.info("/leaselist/incheon/" + sigungucode);
        List<AptLeaseResponseDto> trads;
        if (StringUtils.hasText(sigungucode)) {
            trads = leaseService.getLeaseList_IncheonSigungu(sigungucode);
        } else {
            trads = new ArrayList<>();
        }
        model.addAttribute("list", trads);

        return "leaselist/incheon";
    }
}
