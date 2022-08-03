package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.AptLeaseResponseDto;
import aptdata.earlmazip.controller.dto.AptPriceResponseDto;
import aptdata.earlmazip.service.ApiCallStatService;
import aptdata.earlmazip.service.CodeInfoService;
import aptdata.earlmazip.service.LeaseService;
import aptdata.earlmazip.service.TradeService;
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
public class LeaseController {
    private final LeaseService leaseService;
    private final ApiCallStatService apiCallStatService;
    private final CodeInfoService codeInfoService;

    /**
     * 월별 전세가 통계
     * @param sigunguCode
     * @param uaType
     * @param model
     * @return
     */
    @GetMapping("/leaselist")
    public String getLeaseList_Sigungu(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                  @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                  Model model) {
        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (sigunguCode.length()==5) {
            log.info("/leaselist?sigunguCode=" + sigunguCode);
            title = codeInfoService.getCodeName(sigunguCode);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                trads = leaseService.getLeaseList_SigunguUAType(sigunguCode, uaType);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " - 최근 전세]");
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("list", trads);
        if (sigunguCode.substring(0, 2).equals("11")) {
            return "leaselist/seoul";
        } else if (sigunguCode.substring(0, 2).equals("41")) {
            return "leaselist/gyunggi";
        } else {
            return "leaselist/incheon";
        }
    }

    /**
     * 월별 월세가 통계
     * @param sigunguCode
     * @param uaType
     * @param model
     * @return
     */
    @GetMapping("/leaselist/monthly")
    public String getLeaseMonthlyList(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                      @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                      Model model) {
        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (sigunguCode.length() == 5) {
            log.info("/leaselist/monthly?sigunguCode" + sigunguCode);
            title = codeInfoService.getCodeName(sigunguCode);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/monthly?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                trads = leaseService.getLeaseMonthlyList_SigunguUAType(sigunguCode, uaType);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " - 최근 월세 ]");
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("list", trads);

        if (sigunguCode.substring(0, 2).equals("11")) {
            return "leaselist/monthly/seoul";
        } else if (sigunguCode.substring(0, 2).equals("41")) {
            return "leaselist/monthly/gyunggi";
        } else {
            return "leaselist/monthly/incheon";
        }
    }

    /**
     * 서울 전세 갱신현황
     * @param sigunguCode
     * @param model
     * @return
     */
    @GetMapping("/leaselist/renewal")
    public String getLeaseRenewalList_Seoul(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                            Model model) {
        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (!sigunguCode.equals("0")) {
            title = codeInfoService.getCodeName(sigunguCode);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/renewal?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                trads = leaseService.getLeaseRenewalList_SeoulSigungu(sigunguCode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " - 최근 갱신내역 ]");
        model.addAttribute("list", trads);

        return "leaselist/renewal/seoul";
    }

    @GetMapping("/leaselist/{sigungucode}/{gubn}/{ua}")
    public String getLeaseListBak(@PathVariable String sigungucode,
                               @PathVariable int gubn,
                               @PathVariable int ua, Model model) {
        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (sigungucode.length()==5) {
            log.info("/leaselist/seoul/" + sigungucode);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/" + title + "/" + gubn + "/" + ua, sigungucode);
            if (StringUtils.hasText(sigungucode)) {
                trads = leaseService.getLeaseList_Sigungu(sigungucode, gubn, ua);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " - 최근 전세]");
        model.addAttribute("sigungucode", sigungucode);
        model.addAttribute("gubn", gubn);
        model.addAttribute("ua", ua);
        model.addAttribute("list", trads);
        if (sigungucode.substring(0, 2).equals("11")) {
            return "leaselist/seoul";
        } else if (sigungucode.substring(0, 2).equals("41")) {
            return "leaselist/gyunggi";
        } else {
            return "leaselist/incheon";
        }
    }

    @GetMapping("/leaselist/monthly/{sigungucode}/{gubn}/{ua}") //추후 삭제
    public String getLeaseMonthlyList_Seoul(@PathVariable String sigungucode,
                                            @PathVariable int gubn,
                                            @PathVariable int ua, Model model) {
        String uaType = "UA01";
        if (ua == 84) {
            uaType = "UA07";
        } else {
            uaType = "UA03";
        }
        return getLeaseMonthlyList(sigungucode, uaType, model);
    }
    
    @GetMapping("/leaselist/monthly/seoul/{sigungucode}") //추후 삭제
    public String getLeaseMonthlyList_SeoulBak(@PathVariable String sigungucode, Model model) {
        return getLeaseMonthlyList(sigungucode, "UA01", model);
    }

    @GetMapping("/leaselist/seoul/{sigungucode}") // 추후 삭제
    public String getLeaseList_Seoul(@PathVariable String sigungucode, Model model) {
        return getLeaseList_Sigungu(sigungucode, "UA01", model);
    }

    @GetMapping("/leaselist/renewal/seoul/{sigungucode}") // 추후 삭제
    public String getLeaseRenewalList_SeoulBak(@PathVariable String sigungucode, Model model) {
        return getLeaseRenewalList_Seoul(sigungucode, model);
    }

    @GetMapping("/leaselist/renewal/gyunggi/{sigungucode}")
    public String getLeaseRenewalList_GyunggiSigungu(@PathVariable String sigungucode, Model model) {
        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (!sigungucode.equals("0")) {
            log.info("/leaselist/renewal/gyunggi/" + sigungucode);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/renewal/gyunggi/" + title, sigungucode);
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

    @GetMapping("/leaselist/incheon/{sigungucode}")
    public String getLeaseList_Incheon(@PathVariable String sigungucode, Model model) {
        return getLeaseList_Sigungu(sigungucode, "UA01", model);
    }

    @GetMapping("/leaselist/monthly/incheon/{sigungucode}")
    public String getLeaseMonthlyList_Incheon(@PathVariable String sigungucode, Model model) {
        return getLeaseMonthlyList(sigungucode, "UA01", model);
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
            apiCallStatService.writeApiCallStat("LEASE_LIST_NAME", "/leaselist/ByName/" + regncode + "/" + aptName + "/" + ua + "/" + term, regncode);
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
        model.addAttribute("termStr", Common.makeTermString(term));
        model.addAttribute("regncode", regncode);
        model.addAttribute("aptName", aptName);
        model.addAttribute("ua", ua);
        model.addAttribute("dong", dong);
        model.addAttribute("dates", dates);
        model.addAttribute("deposits", deposits);
        model.addAttribute("list", trads);

        return "leaselist/aptLeaseList_ByUA";
    }

    @GetMapping("/leaselist/monthly/ByName/{regncode}/{dong}/{aptName}/{ua}/{term}")
    public String getMonthlyListByName(@PathVariable String regncode,
                                     @PathVariable String dong,
                                     @PathVariable String aptName,
                                     @PathVariable int ua,
                                     @PathVariable int term,
                                     Model model) {
        List<AptLeaseResponseDto> trads;
        if (!regncode.equals("0")) {
            log.info("/leaselist/monthly/ByName/" + regncode + "/" + aptName + "/" + ua + "/" + term);
            apiCallStatService.writeApiCallStat("LEASE_LIST_NAME", "/leaselist/monthly/ByName/" + regncode + "/" + aptName + "/" + ua + "/" + term, regncode);
            if (StringUtils.hasText(regncode)) {
                trads = leaseService.getMonthlyList_ByName(regncode, dong, aptName, ua, term);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        List<String> dates = trads.stream().map(o->new String(o.getDealDate())).collect(Collectors.toList());
        List<String> monthlies = trads.stream().map(o->new String(o.getMonthlyRentStr())).collect(Collectors.toList());
        List<Integer> deposits = trads.stream().map(o->new Integer(o.getDeposit())).collect(Collectors.toList());
        Collections.reverse(dates);
        Collections.reverse(monthlies);
        Collections.reverse(deposits);

        String title = "-";
        if (trads.size() > 0) {
            title = trads.get(0).getLandDong() + " " + aptName;
        }

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("termStr", Common.makeTermString(term));
        model.addAttribute("regncode", regncode);
        model.addAttribute("aptName", aptName);
        model.addAttribute("ua", ua);
        model.addAttribute("dong", dong);
        model.addAttribute("dates", dates);
        model.addAttribute("monthlies", monthlies);
        model.addAttribute("deposits", deposits);
        model.addAttribute("list", trads);

        return "leaselist/aptMonthlyList_ByUA";
    }
}
