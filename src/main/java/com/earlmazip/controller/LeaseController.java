package com.earlmazip.controller;

import com.earlmazip.controller.dto.AptLeaseResponseDto;
import com.earlmazip.controller.dto.LandDongInfoDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.domain.SigunguCode;
import com.earlmazip.service.*;
import com.earlmazip.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApiIgnore
@Controller
@Slf4j
@RequiredArgsConstructor
public class LeaseController {
    private final LeaseService leaseService;
    private final ApiCallStatService apiCallStatService;
    private final CodeInfoService codeInfoService;
    private final LandDongService landDongService;
    private final RequestService requestService;
    private final IpCountService ipCountService;
    private final IpBlockService ipBlockService;
    private final SiteInfoService siteInfoService;
    private final IpInfoController ipInfoController;


    @RequestMapping("leaselist/home")
    public String home_leaselist(Model model) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        System.out.println("/leaselist/home");
        apiCallStatService.writeApiCallStatDetail("/leaselist/home", "0", "0");
        model.addAttribute("udt", udt);
        model.addAttribute("headerTitle", "최근전세내역");
        return "leaselist/home";
    }

    @RequestMapping("leaselist/monthly/home")
    public String home_leaselistMonthly(Model model) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        System.out.println("/leaselist/monthly/home");
        apiCallStatService.writeApiCallStatDetail("/leaselist/monthly/home", "0", "0");
        model.addAttribute("udt", udt);
        model.addAttribute("headerTitle", "최근월세내역");
        return "leaselist/monthly/home";
    }

    @RequestMapping("leaselist/renewal/home")
    public String home_leaselistRenewal(Model model) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        System.out.println("/leaselist/renewal/home");
        apiCallStatService.writeApiCallStatDetail("/leaselist/renewal/home", "0", "0");
        model.addAttribute("udt", udt);
        model.addAttribute("headerTitle", "최근갱신내역");
        return "leaselist/renewal/home";
    }

    @RequestMapping("leaselist/office/home")
    public String home_leaselistOffice(Model model) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        System.out.println("/leaselist/office/home");
        apiCallStatService.writeApiCallStatDetail("/leaselist/office/home", "0", "0");
        model.addAttribute("udt", udt);
        model.addAttribute("headerTitle", "최근월세내역");
        return "leaselist/office/home";
    }

    @RequestMapping("leaselist/office/monthly/home")
    public String home_leaselistOfficeMonthly(Model model) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        System.out.println("/leaselist/office/monthly/home");
        apiCallStatService.writeApiCallStatDetail("/leaselist/office/monthly/home", "0", "0");
        model.addAttribute("udt", udt);
        model.addAttribute("headerTitle", "최근월세내역");
        return "leaselist/office/monthly/home";
    }

    /**
     * 최근전세내역
     * @param sigunguCode
     * @param uaType
     * @param model
     * @return
     */
    @GetMapping("/leaselist")
    public String getLeaseList_Sigungu(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                  @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                  @RequestParam(value = "landDong", defaultValue = "") String landDong,
                                  HttpServletRequest request,
                                  Model model) throws IOException {
        String clientIP = requestService.getClientIPAddress(request);
        ipInfoController.MergeIpInformation(clientIP);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (sigunguCode.length() == 5) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/leaselist?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/leaselist?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            }
            log.info("[" + clientIP + "] " + url);
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
        } else if (sigunguCode.length() == 2) {
            trads = new ArrayList<>();
        } else {
            apiCallStatService.writeApiCallStat("ERROR", "(error) /leaselist?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }
        String areaCode = sigunguCode.substring(0, 2);
        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);

        model.addAttribute("dongList", dongList);
        model.addAttribute("landDong", landDong);
        if (landDong.equals("")) {
            model.addAttribute("title", "[ " + title + " - 최근 전세]");
        } else {
            model.addAttribute("title",  "[ "+ title + " " + landDong + " - 최근 전세]");
        }

        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("list", trads);
        if (areaCode.equals("11")) {
            return "leaselist/seoul";
        } else if (areaCode.equals("41")) {
            return "leaselist/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "leaselist/guSelect";
        } else {
            return "leaselist/regionSelect";
        }
    }

    @GetMapping("/leaselist/ByDealYearMon")
    public String getLeaseList_SigunguByDealYearMon(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                                    @RequestParam(value = "dealYear", defaultValue = "") String dealYear,
                                                    @RequestParam(value = "dealMon", defaultValue = "") String dealMon,
                                                    @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                                    @RequestParam(value = "landDong", defaultValue = "") String landDong,
                                                    HttpServletRequest request,
                                                    Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/leaselist/ByDealYearMon?sigunguCode=" + sigunguCode + "&dealYear=" + dealYear +"&dealMon" + dealMon + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/leaselist/ByDealYearMon?sigunguCode=" + sigunguCode + "&dealYear=" + dealYear +"&dealMon" + dealMon + "&uaType=" + uaType;
            }
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/ByDealYearMon?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                TradeSearchCond cond = new TradeSearchCond();
                cond.setSigunguCode(sigunguCode);
                cond.setDealYear(dealYear);
                cond.setDealMon(dealMon);
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
            apiCallStatService.writeApiCallStat("ERROR", "(error) /leaselist/ByDealYearMon?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }
        String areaCode = sigunguCode.substring(0, 2);
        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);

        model.addAttribute("dongList", dongList);
        model.addAttribute("landDong", landDong);
        if (landDong.equals("")) {
            model.addAttribute("title", "[ " + title + " - 최근 전세]");
        } else {
            model.addAttribute("title",  "[ "+ title + " " + landDong + " - 최근 전세]");
        }

        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("list", trads);
        if (areaCode.equals("11")) {
            return "leaselist/seoul";
        } else if (areaCode.equals("41")) {
            return "leaselist/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "leaselist/guSelect";
        } else {
            return "leaselist/regionSelect";
        }
    }

    @GetMapping("/leaselist/office")
    public String getLeaseList_Office(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                  @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                  @RequestParam(value = "landDong", defaultValue = "") String landDong,
                                  HttpServletRequest request,
                                  Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        if (sigunguCode.length() < 2) {
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (sigunguCode.length() == 5) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/leaselist/office?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/leaselist/office?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            }
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/office?sigunguCode=" + title, sigunguCode);
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
                trads = leaseService.findLeaseList_Office(cond);
            } else {
                trads = new ArrayList<>();
            }
        } else if (sigunguCode.length() == 2) {
            trads = new ArrayList<>();
        } else {
            apiCallStatService.writeApiCallStat("ERROR", "(error) /leaselist/office?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }
        String areaCode = sigunguCode.substring(0, 2);
        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);

        model.addAttribute("dongList", dongList);
        model.addAttribute("landDong", landDong);
        if (landDong.equals("")) {
            model.addAttribute("title", "[ " + title + " - 최근 전세]");
        } else {
            model.addAttribute("title",  "[ "+ title + " " + landDong + " - 최근 전세]");
        }

        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("list", trads);
        if (areaCode.equals("11")) {
            return "leaselist/office/seoul";
        } else if (areaCode.equals("41")) {
            return "leaselist/office/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "leaselist/office/guSelect";
        } else {
            return "leaselist/office/regionSelect";
        }
    }

    /**
     * 최근 월세 내역
     * @param sigunguCode
     * @param uaType
     * @param model
     * @return
     */
    @GetMapping("/leaselist/monthly")
    public String getLeaseMonthlyList(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                      @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                      @RequestParam(value = "landDong", defaultValue = "") String landDong,
                                      HttpServletRequest request,
                                      Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (sigunguCode.length() == 5) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/leaselist/monthly?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/leaselist/monthly?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            }
            log.info("[" + clientIP + "] " + url);
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
        } else if (sigunguCode.length() == 2) {
            trads = new ArrayList<>();
        } else {
            apiCallStatService.writeApiCallStat("ERROR", "(error) /leaselist/monthly?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }

        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);
        String areaCode = sigunguCode.substring(0, 2);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);

        model.addAttribute("sigunguList", sigunguList);
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

        if (areaCode.equals("11")) {
            return "leaselist/monthly/seoul";
        } else if (areaCode.equals("41")) {
            return "leaselist/monthly/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "leaselist/monthly/guSelect";
        } else {
            return "leaselist/monthly/regionSelect";
        }
    }

    @GetMapping("/leaselist/office/monthly")
    public String getLeaseMonthlyList_Office(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                      @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                      @RequestParam(value = "landDong", defaultValue = "") String landDong,
                                      HttpServletRequest request,
                                      Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        if (sigunguCode.length() < 2) {
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/leaselist/office/monthly?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/leaselist/office/monthly?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            }
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/office/monthly?sigunguCode=" + title, sigunguCode);
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
                trads = leaseService.findLeaseList_Office(cond);
//                trads = leaseService.getLeaseMonthlyList_SigunguUAType(sigunguCode, uaType);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            apiCallStatService.writeApiCallStat("ERROR", "(error) /leaselist/office/monthly?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }

        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);
        String areaCode = sigunguCode.substring(0, 2);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);

        model.addAttribute("sigunguList", sigunguList);
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

        if (areaCode.equals("11")) {
            return "leaselist/office/monthly/seoul";
        } else if (areaCode.equals("41")) {
            return "leaselist/office/monthly/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "leaselist/office/monthly/guSelect";
        } else {
            return "leaselist/office/monthly/regionSelect";
        }
    }

    /**
     * 최근 갱신내역
     * @param sigunguCode
     * @param model
     * @return
     */
    @GetMapping("/leaselist/renewal")
    public String getLeaseRenewalList_Seoul(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                            @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                            @RequestParam(value = "landDong", defaultValue = "") String landDong,
                                            HttpServletRequest request,
                                            Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        String title = "-";
        List<AptLeaseResponseDto> trads;
        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/leaselist/renewal?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/leaselist/renewal?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            }
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("LEASE_LIST", "/leaselist/renewal?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                trads = leaseService.findLeaseRenewalList(sigunguCode, landDong, uaType);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            apiCallStatService.writeApiCallStat("ERROR", "(error) /leaselist/renewal?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }
        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);
        String areaCode = sigunguCode.substring(0, 2);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);

        model.addAttribute("sigunguList", sigunguList);
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
        if (areaCode.equals("11")) {
            return "leaselist/renewal/seoul";
        } else if (areaCode.equals("41")) {
            return "leaselist/renewal/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "leaselist/renewal/guSelect";
        } else {
            return "leaselist/renewal/regionSelect";
        }
    }

    @GetMapping("/leaselist/ByName")
    public String getLeaseListByName(@RequestParam(value="sigunguCode", defaultValue = "") String sigunguCode,
                                     @RequestParam(value="aptName", defaultValue = "") String aptName,
                                     @RequestParam(value="ua", defaultValue = "0") int ua,
                                     @RequestParam(value="term", defaultValue = "1") int term,
                                     @RequestParam(value="landDong", defaultValue = "") String landDong,
                                     HttpServletRequest request,
                                     Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        List<AptLeaseResponseDto> trads;
        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            String url = "/leaselist/ByName?sigunguCode=" + sigunguCode + "&aptName=" + aptName + "&ua=" + ua + "&landDong=" + landDong;
            log.info("[" + clientIP + "] " + url);
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
            apiCallStatService.writeApiCallStat("ERROR", "(error) /leaselist/ByName?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
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
                                       HttpServletRequest request,
                                     Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        List<AptLeaseResponseDto> trads;
        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            String url = "/leaselist/monthly/ByName?sigunguCode=" + sigunguCode + "&aptName=" + aptName;
            log.info("[" + clientIP + "] " + url);
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
            apiCallStatService.writeApiCallStat("ERROR", "(error) /leaselist/monthly/ByName?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
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
