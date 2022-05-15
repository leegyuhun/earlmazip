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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/seoul/" + title);
            if (StringUtils.hasText(sigungucode)) {
                trads = leaseService.getLeaseList_SeoulSigungu(sigungucode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " - 최근 전세]");
        model.addAttribute("list", trads);

        return "leaselist/seoul";
    }

    @GetMapping("/leaselist/renewal/seoul/{sigungucode}")
    public String getLeaseRenewalList_Seoul(@PathVariable String sigungucode, Model model) {
        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (!sigungucode.equals("0")) {
            log.info("/leaselist/renewal/seoul/" + sigungucode);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/renewal/seoul/" + title);
            if (StringUtils.hasText(sigungucode)) {
                trads = leaseService.getLeaseRenewalList_SeoulSigungu(sigungucode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " - 최근 갱신내역 ]");
        model.addAttribute("list", trads);

        return "leaselist/seoulRenewal";
    }

    @GetMapping("/leaselist/monthly/seoul/{sigungucode}")
    public String getLeaseMonthlyList_Seoul(@PathVariable String sigungucode, Model model) {
        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (!sigungucode.equals("0")) {
            log.info("/leaselist/monthly/seoul/" + sigungucode);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/monthly/seoul/" + title);
            if (StringUtils.hasText(sigungucode)) {
                trads = leaseService.getLeaseMonthlyList_SeoulSigungu(sigungucode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " - 최근 월세 ]");
        model.addAttribute("list", trads);

        return "leaselist/seoulMonthly";
    }

    @GetMapping("/leaselist/gyunggi/{sidocode}")
    public String getLeaseList_Gyunggi(@PathVariable String sidocode, Model model) {
        List<AptLeaseResponseDto> trads;
        String title = "-";
        if (!sidocode.equals("0")) {
            log.info("/leaselist/gyunggi/" + sidocode);
            title = codeInfoService.getCodeName(sidocode);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/gyunggi/" + title);
            if (StringUtils.hasText(sidocode)) {
                trads = leaseService.getLeaseList_GyunggiSido(sidocode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " - 최근 전세 ]");
        model.addAttribute("list", trads);

        return "leaselist/gyunggi";
    }

    @GetMapping("/leaselist/renewal/gyunggi/{sigungucode}")
    public String getLeaseRenewalList_GyunggiSigungu(@PathVariable String sigungucode, Model model) {
        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (!sigungucode.equals("0")) {
            log.info("/leaselist/renewal/gyunggi/" + sigungucode);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/renewal/gyunggi/" + title);
            if (StringUtils.hasText(sigungucode)) {
                trads = leaseService.getLeaseRenewalList_GyunggiSigungu(sigungucode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " - 최근 갱신내역 ]");
        model.addAttribute("list", trads);

        return "leaselist/gyunggiRenewal";
    }

    @GetMapping("/leaselist/monthly/gyunggi/{sidocode}")
    public String getLeaseMonthlyList_Gyunggi(@PathVariable String sidocode, Model model) {
        List<AptLeaseResponseDto> trads;
        String title = "-";
        if (!sidocode.equals("0")) {
            log.info("/leaselist/monthly/gyunggi/" + sidocode);
            title = codeInfoService.getCodeName(sidocode);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/monthly/gyunggi/" + title);
            if (StringUtils.hasText(sidocode)) {
                trads = leaseService.getLeaseMonthlyList_GyunggiSido(sidocode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " - 최근 월세 ]");
        model.addAttribute("list", trads);

        return "leaselist/gyunggiMonthly";
    }

    @GetMapping("/leaselist/incheon/{sigungucode}")
    public String getLeaseList_Incheon(@PathVariable String sigungucode, Model model) {
        List<AptLeaseResponseDto> trads;
        String title = "-";
        if (!sigungucode.equals("0")) {
            log.info("/leaselist/incheon/" + sigungucode);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/incheon/" + title);
            if (StringUtils.hasText(sigungucode)) {
                trads = leaseService.getLeaseList_IncheonSigungu(sigungucode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " - 최근 전세 ]");
        model.addAttribute("list", trads);

        return "leaselist/incheon";
    }

    @GetMapping("/leaselist/monthly/incheon/{sigungucode}")
    public String getLeaseMonthlyList_Incheon(@PathVariable String sigungucode, Model model) {
        List<AptLeaseResponseDto> trads;
        String title = "-";
        if (!sigungucode.equals("0")) {
            log.info("/leaselist/monthly/incheon/" + sigungucode);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/monthly/incheon/" + title);
            if (StringUtils.hasText(sigungucode)) {
                trads = leaseService.getLeaseMonthlyList_IncheonSigungu(sigungucode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " - 최근 월세 ]");
        model.addAttribute("list", trads);

        return "leaselist/incheonMonthly";
    }

    @GetMapping("/leaselist/ByName/{regncode}/{dong}/{aptName}/{ua}/{term}")
    public String getLeaseListByName(@PathVariable String regncode,
                                     @PathVariable String dong,
                                     @PathVariable String aptName,
                                     @PathVariable int ua,
                                     @PathVariable int term,
                                     Model model) {
        List<AptLeaseResponseDto> trads;
        if (!regncode.equals("0")) {
            log.info("/leaselist/ByName/" + regncode + "/" + aptName + "/" + ua + "/" + term);
            apiCallStatService.writeApiCallStat("LEASE_LIST_NAME", "/leaselist/ByName/" + regncode + "/" + aptName + "/" + ua + "/" + term);
            if (StringUtils.hasText(regncode)) {
                trads = leaseService.getLeaseList_ByName(regncode, dong, aptName, ua, term);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        List<String> dates = trads.stream().map(o->new String(o.getDealDate())).collect(Collectors.toList());
        List<Integer> deposits = trads.stream().map(o->new Integer(o.getDeposit())).collect(Collectors.toList());
        Collections.reverse(dates);
        Collections.reverse(deposits);

        String title = "-";
        if (trads.size() > 0) {
            title = trads.get(0).getLandDong() + " " + aptName;
        }

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("dates", dates);
        model.addAttribute("deposits", deposits);
        model.addAttribute("list", trads);

        return "leaselist/aptLeaseList_ByUA";
    }
}
