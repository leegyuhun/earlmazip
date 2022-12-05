package com.earlmazip.controller;

import com.earlmazip.controller.dto.AptLeaseResponseDto;
import com.earlmazip.controller.dto.LandDongInfoDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.service.ApiCallStatService;
import com.earlmazip.service.CodeInfoService;
import com.earlmazip.service.LandDongService;
import com.earlmazip.service.LeaseService;
import com.earlmazip.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    private final LandDongService landDongService;

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
                                  @RequestParam(value = "landDong", defaultValue = "") String landDong,
                                  Model model) {
        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (sigunguCode.length()==5) {
            log.info("/leaselist?sigunguCode=" + sigunguCode);
            title = codeInfoService.getCodeName(sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/leaselist?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/leaselist?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            }
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                TradeSearchCond cond = new TradeSearchCond();
                cond.setSigunguCode(sigunguCode);
                if (uaType.equals("UA01")) {
                    cond.setUaType("");
                } else {
                    cond.setUaType(uaType);
                }
                cond.setLandDong(landDong);
                cond.setLeaseType("0");
                trads = leaseService.findLeaseList(cond);
//                trads = leaseService.getLeaseList_SigunguUAType(sigunguCode, uaType);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);

        model.addAttribute("dongList", dongList);
        model.addAttribute("landDong", landDong);
        if (landDong.equals("")) {
            model.addAttribute("title", "[ " + title + " - 최근 전세]");
        } else {
            model.addAttribute("title",  "[ "+ title + " " + landDong + " - 최근 전세]");
        }

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
                                      @RequestParam(value = "landDong", defaultValue = "") String landDong,
                                      Model model) {
        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (sigunguCode.length() == 5) {
            log.info("/leaselist/monthly?sigunguCode" + sigunguCode);
            title = codeInfoService.getCodeName(sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/leaselist/monthly?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/leaselist/monthly?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            }
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/monthly?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                TradeSearchCond cond = new TradeSearchCond();
                cond.setSigunguCode(sigunguCode);
                if (uaType.equals("UA01")) {
                    cond.setUaType("");
                } else {
                    cond.setUaType(uaType);
                }
                cond.setLandDong(landDong);
                cond.setLeaseType("1");
                trads = leaseService.findLeaseList(cond);
//                trads = leaseService.getLeaseMonthlyList_SigunguUAType(sigunguCode, uaType);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);

        model.addAttribute("dongList", dongList);
        model.addAttribute("landDong", landDong);
        if (landDong.equals("")) {
            model.addAttribute("title", "[ " + title + " - 최근 월세 ]");
        } else {
            model.addAttribute("title",  "[ "+ title + " " + landDong + " - 최근 월세 ]");
        }

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
                                            @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                            @RequestParam(value = "landDong", defaultValue = "") String landDong,
                                            Model model) {
        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (!sigunguCode.equals("0")) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/leaselist/renewal?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/leaselist/renewal?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            }
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/renewal?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                trads = leaseService.findLeaseRenewalList(sigunguCode, landDong, uaType);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }
        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);

        model.addAttribute("dongList", dongList);
        model.addAttribute("landDong", landDong);
        model.addAttribute("uaType", uaType);
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("sigunguCode", sigunguCode);
        if (landDong.equals("")) {
            model.addAttribute("title",  "[ "+ title + " - 최근 갱신내역 ]");
        } else {
            model.addAttribute("title",  "[ "+ title + " " + landDong + " - 최근 갱신내역 ]");
        }
        model.addAttribute("list", trads);

        return "leaselist/renewal/seoul";
    }

    @GetMapping("/leaselist/ByName")
    public String getLeaseListByName(@RequestParam(value="sigunguCode", defaultValue = "") String sigunguCode,
                                     @RequestParam(value="aptName", defaultValue = "") String aptName,
                                     @RequestParam(value="ua", defaultValue = "0") int ua,
                                     @RequestParam(value="term", defaultValue = "1") int term,
                                     @RequestParam(value="landDong", defaultValue = "") String landDong,
                                     Model model){
        List<AptLeaseResponseDto> trads;
        if (!sigunguCode.equals("0")) {
            String url = "/leaselist/ByName?sigunguCode=" + sigunguCode + "&aptName=" + aptName + "&ua=" + ua + "&landDong=" + landDong;
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, codeInfoService.getCodeName(sigunguCode));
            apiCallStatService.writeApiCallStat("LEASE_LIST_NAME", "/leaselist/ByName?sigunguCode=" + sigunguCode + "&aptName=" + aptName, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                TradeSearchCond cond = new TradeSearchCond();
                cond.setAptName(aptName);
                cond.setSigunguCode(sigunguCode);
                if (ua > 0) {
                    cond.setUseAreaTrunc(ua);
                }
                cond.setLandDong(landDong);
                trads = leaseService.findAptLeaseList(cond, "0", term);
//                trads = leaseService.getLeaseList_ByName(sigunguCode, landDong, aptName, ua, term);
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
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("aptName", aptName);
        model.addAttribute("ua", ua);
        model.addAttribute("landDong", landDong);
        model.addAttribute("dates", dates);
        model.addAttribute("deposits", deposits);
        model.addAttribute("list", trads);

        return "leaselist/aptLeaseList_ByUA";
    }
    @GetMapping("/leaselist/monthly/ByName")
    public String getMonthlyListByName(@RequestParam(value="sigunguCode", defaultValue = "") String sigunguCode,
                                     @RequestParam(value="aptName", defaultValue = "") String aptName,
                                     @RequestParam(value="ua", defaultValue = "0") int ua,
                                     @RequestParam(value="term", defaultValue = "1") int term,
                                     @RequestParam(value="landDong", defaultValue = "") String landDong,
                                     Model model){
        List<AptLeaseResponseDto> trads;
        if (!sigunguCode.equals("0")) {
            apiCallStatService.writeApiCallStat("LEASE_LIST_NAME", "/leaselist/monthly/ByName?sigunguCode=" + sigunguCode + "&aptName=" + aptName, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                TradeSearchCond cond = new TradeSearchCond();
                cond.setAptName(aptName);
                cond.setSigunguCode(sigunguCode);
                if (ua > 0) {
                    cond.setUseAreaTrunc(ua);
                }
                cond.setLandDong(landDong);
                trads = leaseService.findAptLeaseList(cond, "1", term);
//                trads = leaseService.getMonthlyList_ByName(sigunguCode, landDong, aptName, ua, term);
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
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("aptName", aptName);
        model.addAttribute("ua", ua);
        model.addAttribute("landDong", landDong);
        model.addAttribute("dates", dates);
        model.addAttribute("monthlies", monthlies);
        model.addAttribute("deposits", deposits);
        model.addAttribute("list", trads);

        return "leaselist/aptMonthlyList_ByUA";
    }
}
