package com.earlmazip.controller;

import com.earlmazip.controller.dto.AptPriceResponseDto;
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
public class TradeController {
    private final SiteInfoService siteInfoService;
    private final TradeService tradeService;
    private final ApiCallStatService apiCallStatService;
    private final CodeInfoService codeInfoService;
    private final LandDongService landDongService;
    private final RequestService requestService;
    private final IpCountService ipCountService;
    private final IpBlockService ipBlockService;
    private final IpInfoController ipInfoController;

    @RequestMapping("/tradelist/home")
    public String home_tradelist(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        System.out.println("/tradelist/home");
        apiCallStatService.writeApiCallStatDetail("/tradelist/home", "0", "0");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "최근매매내역");
        return "tradelist/home";
    }

    @RequestMapping("/tradelist/newHighest/home")
    public String home_tradelistNewHighest(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        apiCallStatService.writeApiCallStatDetail("/tradelist/newHighest/home", "0", "0");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "신고가내역");
        return "tradelist/newHighest/home";
    }

    @RequestMapping("/tradelist/cancelDeal/home")
    public String home_tradelistCancelDeal(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        apiCallStatService.writeApiCallStatDetail("/tradelist/cancelDeal/home", "0", "0");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "취소내역");
        return "tradelist/cancelDeal/home";
    }

    @RequestMapping("/tradelist/office/home")
    public String home_tradelistOffice(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        apiCallStatService.writeApiCallStatDetail("/tradelist/office/home", "0", "0");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "최근매매내역[오피스텔]");
        return "tradelist/office/home";
    }

    /** 최근 매매내역 200
     * @param sigunguCode
     * @param uaType
     * @param model
     * @return
     */
    @GetMapping("/tradelist")
    public String getTradeList(@RequestParam(value = "sigunguCode", defaultValue = "0") String sigunguCode,
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

        List<AptPriceResponseDto> trads;
        String title = "-";
        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/tradelist?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/tradelist?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            }
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist?sigunguCode=" + title, sigunguCode);

            if (StringUtils.hasText(sigunguCode)) {
                TradeSearchCond cond = new TradeSearchCond();
                cond.setSigunguCode(sigunguCode);
                if (uaType.equals("UA01")) {
                    cond.setUaType("");
                } else {
                    cond.setUaType(uaType);
                }
                cond.setLandDong(landDong);
                trads = tradeService.findTradeList(cond);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            apiCallStatService.writeApiCallStat("ERROR", "(error) /tradelist?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }
        String areaCode = sigunguCode.substring(0, 2);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);
        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);
        if (!landDong.equals("")) {
            title += " " + landDong;
        }
        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("dongList", dongList);
        model.addAttribute("landDong", landDong);
        model.addAttribute("list", trads);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("headerTitle", title + " 최근 매매");
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        if (areaCode.equals("11")) {
            return "tradelist/seoul";
        } else if (areaCode.equals("41")) {
            return "tradelist/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "tradelist/guSelect";
        } else {
            return "tradelist/regionSelect";
        }
    }

    @GetMapping("/tradelist/ByDealYearMon")
    public String getTradeListByDealYearMon(@RequestParam(value = "sigunguCode", defaultValue = "0") String sigunguCode,
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

        List<AptPriceResponseDto> trads;
        String title = "-";
        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/tradelist?sigunguCode=" + sigunguCode + "&dealYear=" + dealYear +"&dealMon" + dealMon + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/tradelist?sigunguCode=" + sigunguCode + "&dealYear=" + dealYear +"&dealMon" + dealMon +"&uaType=" + uaType;
            }
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist?sigunguCode=" + title, sigunguCode);

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
                trads = tradeService.findTradeList(cond);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            apiCallStatService.writeApiCallStat("ERROR", "(error) /tradelist/ByDealYearMon?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }
        String areaCode = sigunguCode.substring(0, 2);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);
        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);
        if (!landDong.equals("")) {
            title += " " + landDong;
        }
        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("dongList", dongList);
        model.addAttribute("landDong", landDong);
        model.addAttribute("list", trads);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("headerTitle", title + " 최근 매매");
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        if (areaCode.equals("11")) {
            return "tradelist/seoul";
        } else if (areaCode.equals("41")) {
            return "tradelist/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "tradelist/guSelect";
        } else {
            return "tradelist/regionSelect";
        }
    }

    @GetMapping("/tradelist/office")
    public String getTradeList_office(@RequestParam(value = "sigunguCode", defaultValue = "0") String sigunguCode,
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

        List<AptPriceResponseDto> trads;
        String title = "-";
        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/tradelist/office?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/tradelist/office?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            }
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/office?sigunguCode=" + title, sigunguCode);

            if (StringUtils.hasText(sigunguCode)) {
                TradeSearchCond cond = new TradeSearchCond();
                cond.setSigunguCode(sigunguCode);
                if (uaType.equals("UA01")) {
                    cond.setUaType("");
                } else {
                    cond.setUaType(uaType);
                }
                cond.setLandDong(landDong);
                trads = tradeService.findTradeList_Office(cond);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            apiCallStatService.writeApiCallStat("ERROR", "(error) /tradelist/office?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }
        String areaCode = sigunguCode.substring(0, 2);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);
        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);
        if (!landDong.equals("")) {
            title += " " + landDong;
        }
        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("dongList", dongList);
        model.addAttribute("landDong", landDong);
        model.addAttribute("list", trads);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("headerTitle", title + " 최근 오피스텔 매매");
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        if (areaCode.equals("11")) {
            return "tradelist/office/seoul";
        } else if (areaCode.equals("41")) {
            return "tradelist/office/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "tradelist/office/guSelect";
        } else {
            return "tradelist/office/regionSelect";
        }
    }

    @GetMapping("/tradelist/office/ByName")
    public String getTradeList_Office_ByName(@RequestParam(value="sigunguCode", defaultValue = "") String sigunguCode,
                                     @RequestParam(value="officeName", defaultValue = "") String officeName,
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

        List<AptPriceResponseDto> trads;
        if (!sigunguCode.equals("0")) {
            String url = "/tradelist/office/ByName?sigunguCode=" + sigunguCode + "&aptName=" + officeName + "&ua=" + ua + "&landDong=" + landDong;
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, codeInfoService.getCodeName(sigunguCode));
            apiCallStatService.writeApiCallStat("TRADE_LIST_NAME", "/tradelist/office/ByName?sigunguCode=" + sigunguCode + "&officeName=" + officeName, sigunguCode);

            if (StringUtils.hasText(sigunguCode)) {
                TradeSearchCond cond = new TradeSearchCond();
                cond.setAptName(officeName);
                cond.setSigunguCode(sigunguCode);
                if (ua > 0) {
                    cond.setUseAreaTrunc(ua);
                }
                cond.setLandDong(landDong);
                trads = tradeService.findOfficeTradeList(cond, term);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        List<String> dates = trads.stream().map(o->new String(o.getDealDate())).collect(Collectors.toList());
        List<Float> dealAmts = trads.stream().map(o->new Float((float)o.getDealAmt()/10000)).collect(Collectors.toList());
        Collections.reverse(dates);
        Collections.reverse(dealAmts);

        String title = "-";
        if (trads.size() > 0) {
            title = trads.get(0).getLandDong() + " " + officeName + "(" + trads.get(0).getBuildYear() + ")";
        }

        model.addAttribute("title",  title);
        model.addAttribute("headerTitle", title + " 매매내역");
        model.addAttribute("landDong", landDong);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("aptName", officeName);
        model.addAttribute("ua", ua);
        model.addAttribute("dates", dates);
        model.addAttribute("dealAmts", dealAmts);
        model.addAttribute("termStr", Common.makeTermString(term));
        model.addAttribute("list", trads);

        return "tradelist/office/ByName/officeTradeListByName";
    }

    /**
     * 거래취소건 조회
     * @param sigunguCode
     * @param model
     * @return
     */
    @GetMapping("/tradelist/cancelDeal")
    public String getCancelDealList(@RequestParam(value = "sigunguCode", defaultValue = "0") String sigunguCode,
                                    HttpServletRequest request,
                                    Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        List<AptPriceResponseDto> trads;
        String title = "-";
        if (!sigunguCode.equals("0")) {
            String url = "/tradelist/cancelDeal?sigunguCode=" + sigunguCode;
            log.info("[" + clientIP + "] " + url);
            title = codeInfoService.getCodeName(sigunguCode);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("TRADE_CANCEL", "/tradelist/cancelDeal?sigunguCode=" + title, sigunguCode);

            if (StringUtils.hasText(sigunguCode)) {
                trads = tradeService.getCancelDealList(sigunguCode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            apiCallStatService.writeApiCallStat("ERROR", "(error) /tradelist/cancelDeal?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }
        String areaCode = sigunguCode.substring(0, 2);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);
        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("headerTitle", title + " 취소거래");
        model.addAttribute("list", trads);
        if (areaCode.equals("11")) {
            return "tradelist/cancelDeal/seoul";
        } else if (areaCode.equals("41")) {
            return "tradelist/cancelDeal/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "tradelist/cancelDeal/guSelect";
        } else {
            return "tradelist/cancelDeal/regionSelect";
        }
    }

    /**
     * 신고가내역 조회
     * @param sigunguCode
     * @param uaType
     * @param model
     * @return
     */
    @GetMapping("/tradelist/newHighest")
    public String getNewHighestList(@RequestParam(value="sigunguCode", defaultValue = "11") String sigunguCode,
                                    @RequestParam(value="uaType", defaultValue = "UA01") String uaType,
                                    @RequestParam(value="landDong", defaultValue = "") String landDong,
                                    HttpServletRequest request,
                                    Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        List<AptPriceResponseDto> trads;
        String title = "-";
        if (sigunguCode.length() == 5) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/tradelist/newHighest?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/tradelist/newHighest?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            }
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/newHighest?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                trads = tradeService.getNewHighestList(sigunguCode, uaType, landDong);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            apiCallStatService.writeApiCallStat("ERROR", "(error) /tradelist/newHighest?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }

        if (!landDong.equals("")) {
            title += " " + landDong;
        }
        String areaCode = sigunguCode.substring(0, 2);

        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);
        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("dongList", dongList);
        model.addAttribute("list", trads);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("landDong", landDong);
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("headerTitle", title + " 신고가내역");
        if (areaCode.equals("11")) {
            return "tradelist/newHighest/seoul";
        } else if (areaCode.equals("41")) {
            return "tradelist/newHighest/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "tradelist/newHighest/guSelect";
        } else {
            return "tradelist/newHighest/regionSelect";
        }
    }

    /**
     * 아파트 실거래내역 조회
     * @param sigunguCode
     * @param aptName
     * @param ua
     * @param term
     * @param landDong
     * @param model
     * @return
     */
    @GetMapping("/tradelist/ByName")
    public String getTradeListByName(@RequestParam(value="sigunguCode", defaultValue = "") String sigunguCode,
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

        List<AptPriceResponseDto> trads;
        if (!sigunguCode.equals("0")) {
            String url = "/tradelist/ByName?sigunguCode=" + sigunguCode + "&aptName=" + aptName + "&ua=" + ua + "&landDong=" + landDong;
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, codeInfoService.getCodeName(sigunguCode));
            apiCallStatService.writeApiCallStat("TRADE_LIST_NAME", "/tradelist/ByName?sigunguCode=" + sigunguCode + "&aptName=" + aptName, sigunguCode);

            if (StringUtils.hasText(sigunguCode)) {
                TradeSearchCond cond = new TradeSearchCond();
                cond.setAptName(aptName);
                cond.setSigunguCode(sigunguCode);
                if (ua > 0) {
                    cond.setUseAreaTrunc(ua);
                }
                cond.setLandDong(landDong);
                trads = tradeService.findAptTradeList(cond, term);
//                trads = tradeService.getAptTradeList_ByName(sigunguCode, landDong, aptName, ua, term);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            apiCallStatService.writeApiCallStat("ERROR", "(error) /tradelist/ByName?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }

        List<String> dates = trads.stream().map(o->new String(o.getDealDate())).collect(Collectors.toList());
        List<Float> dealAmts = trads.stream().map(o->new Float((float)o.getDealAmt()/10000)).collect(Collectors.toList());
        Collections.reverse(dates);
        Collections.reverse(dealAmts);

        String title = "-";
        if (trads.size() > 0) {
            title = trads.get(0).getLandDong() + " " + aptName + "(" + trads.get(0).getBuildYear() + ")";
        }

        model.addAttribute("title",  title);
        model.addAttribute("headerTitle", title + " 매매내역");
        model.addAttribute("landDong", landDong);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("aptName", aptName);
        model.addAttribute("ua", ua);
        model.addAttribute("dates", dates);
        model.addAttribute("dealAmts", dealAmts);
        model.addAttribute("termStr", Common.makeTermString(term));
        model.addAttribute("list", trads);

        return "tradelist/ByName/aptTradeList_ByUA";
    }
}
