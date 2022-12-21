package com.earlmazip.controller;

import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.controller.dto.LandDongInfoDto;
import com.earlmazip.controller.dto.TradeSearchCond;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TradeController {
    private final SiteInfoService siteInfoService;
    private final TradeService tradeService;
    private final ApiCallStatService apiCallStatService;
    private final CodeInfoService codeInfoService;
    private final LandDongService landDongService;

    @RequestMapping("/tradelist/home")
    public String home_tradelist(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "월별 매매 통계");
        return "tradelist/home";
    }

    @RequestMapping("/tradelist/newHighest/home")
    public String home_tradelistNewHighest(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "월별 매매 통계");
        return "tradelist/newHighest/home";
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
                                     Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            title = codeInfoService.getCodeName(sigunguCode);
            log.info("/tradelist?" + sigunguCode);
            String url;
            if(StringUtils.hasText(landDong)){
                url = "/tradelist?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/tradelist?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            }
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
            trads = new ArrayList<>();
        }

        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);
        if (!landDong.equals("")) {
            title += " " + landDong;
        }
        model.addAttribute("dongList", dongList);
        model.addAttribute("landDong", landDong);
        model.addAttribute("list", trads);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("headerTitle", title + " 최근 매매");
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        String areaCode = sigunguCode.substring(0, 2);
        if (areaCode.equals("11")) {
            return "tradelist/seoul";
        } else if (areaCode.equals("41")) {
            return "tradelist/gyunggi";
        } else if(areaCode.equals("28")){
            return "tradelist/incheon";
        } else if (areaCode.equals("26")){
            return "tradelist/busan";
        } else if (areaCode.equals("27")) {
            return "tradelist/daegu";
        } else if (areaCode.equals("31")) {
            return "tradelist/ulsan";
        } else if (areaCode.equals("47")) {
            return "tradelist/gsNorth";
        } else if (areaCode.equals("48")) {
            return "tradelist/gsSouth";
        } else if (areaCode.equals("42")) {
            return "tradelist/gangwon";
        } else {
            return "tradelist/sejong";
        }
    }

    /**
     * 거래취소건 조회
     * @param sigunguCode
     * @param model
     * @return
     */
    @GetMapping("/tradelist/cancelDeal")
    public String getCancelDealList(@RequestParam(value = "sigunguCode", defaultValue = "0") String sigunguCode,
                                    Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (!sigunguCode.equals("0")) {
            log.info("/tradelist/cancelDeal?sigunguCode=" + sigunguCode);
            title = codeInfoService.getCodeName(sigunguCode);
            apiCallStatService.writeApiCallStatDetail("/tradelist/cancelDeal?sigunguCode=" + sigunguCode, sigunguCode, title);
            apiCallStatService.writeApiCallStat("TRADE_CANCEL", "/tradelist/cancelDeal?sigunguCode=" + title, sigunguCode);

            if (StringUtils.hasText(sigunguCode)) {
                trads = tradeService.getCancelDealList(sigunguCode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("headerTitle", title + " 취소거래");
        model.addAttribute("list", trads);

        return "tradelist/cancelDeal";
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
                                    Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (sigunguCode.length() == 5) {
            title = codeInfoService.getCodeName(sigunguCode);
            log.info("/tradelist/newHighest?sigunguCode" + sigunguCode);

            String url;
            if(StringUtils.hasText(landDong)){
                url = "/tradelist/newHighest?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&landDong=" + landDong;
            } else{
                url = "/tradelist/newHighest?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            }
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/newHighest?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                trads = tradeService.getNewHighestList(sigunguCode, uaType, landDong);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            trads = new ArrayList<>();
        }

        if (!landDong.equals("")) {
            title += " " + landDong;
        }
        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);
        model.addAttribute("dongList", dongList);
        model.addAttribute("list", trads);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("landDong", landDong);
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("headerTitle", title + " 2022 신고가내역");
        if (sigunguCode.substring(0, 2).equals("11")) {
            return "tradelist/newHighest/seoul";
        } else if (sigunguCode.substring(0, 2).equals("41")) {
            return "tradelist/newHighest/gyunggi";
        } else {
            return "tradelist/newHighest/incheon";
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
                                     Model model) {
        List<AptPriceResponseDto> trads;
        if (!sigunguCode.equals("0")) {
            String url = "/tradelist/ByName?sigunguCode=" + sigunguCode + "&aptName=" + aptName + "&ua=" + ua + "&landDong=" + landDong;
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
            trads = new ArrayList<>();
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
