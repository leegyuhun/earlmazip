package com.earlmazip.controller;

import com.earlmazip.controller.dto.*;
import com.earlmazip.domain.SigunguCode;
import com.earlmazip.service.*;
import com.earlmazip.utils.Common;
import lombok.Data;
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
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

@ApiIgnore
@Controller
@Slf4j
@RequiredArgsConstructor
public class StatController {

    private final SiteInfoService siteInfoService;

    private final StatService statService;

    private final ApiCallStatService apiCallStatService;

    private final CodeInfoService codeInfoService;

    private final EcosDataService ecosDataService;

    private final RequestService requestService;

    private final IpCountService ipCountService;

    private final IpBlockService ipBlockService;

    @RequestMapping("/stat_trade/useareaType/home")
    public String home_statMonthly(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "월별 매매 통계");
        return "stat_trade/useAreaType/home";
    }

    @RequestMapping("/stat_trade/top/home")
    public String home_statTop(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "매매가 TOP 100");
        return "stat_trade/top/home";
    }

    @RequestMapping("/stat_trade/ByDealType/home")
    public String home_statDealType(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "거래유형별 통계");
        return "stat_trade/dealType/home";
    }
    @GetMapping("/stat_trade/office/top/home")
    public String home_statTopOffice(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "오피스텔 TOP 100");
        return "stat_trade/office/top/home";
    }
    /**
     * 서울/경기/인천 구별,평형대별,월별 매매 통계
     * @param sigunguCode
     * @param uaType
     * @param term
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/useareaType")
    public String getStatUseareaType_BySigungu(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                               @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                               @RequestParam(value = "term", defaultValue = "0") String term,
                                               HttpServletRequest request,
                                               Model model) throws UnknownHostException {
        List<StatResponseDto> areas;
        String title = "-";
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);
        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url = "/stat_trade/useareaType?sigunguCode=" + sigunguCode + "&uaType=" + uaType + "&term=" + term;
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_trade/useareaType?sigunguCode=" + title, sigunguCode);
            areas = statService.getStatTradeByUseAreaList(sigunguCode,uaType, term);
        } else {
            apiCallStatService.writeApiCallStat("ERROR", "(error) /stat_trade/useareaType?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }
        List<String> dates = areas.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
//        List<Integer> avgprc = areas.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Float> avgprc = areas.stream().map(o->new Float((float)o.getAvgPrice()/10000)).collect(Collectors.toList());
        List<Integer> tradcnt = areas.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        // 한국은행 기준금리
        List<EcosDataResponseDto> rates = ecosDataService.getEcosData("722Y001", "0101000", "", term);
        List<String> interestRates = rates.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgprc);
        Collections.reverse(tradcnt);
        String areaCode = sigunguCode.substring(0, 2);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);
        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("title",  title);
        model.addAttribute("headerTitle", title + " 월별 통계");
        model.addAttribute("sigunguCode",  sigunguCode);
        model.addAttribute("term",  term);
        model.addAttribute("termStr", Common.makeTermString(term));
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("list", areas);
        model.addAttribute("uaType", uaType);
        model.addAttribute("dates", dates);
        model.addAttribute("avgprc", avgprc);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("interestRates", interestRates);
        if (areaCode.equals("11")) {
            return "stat_trade/useAreaType/seoul";
        } else if (areaCode.equals("41")) {
            return "stat_trade/useAreaType/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "stat_trade/useAreaType/guSelect";
        } else {
            return "stat_trade/useAreaType/regionSelect";
        }
    }

    /**
     * 서울시 월별,전용면적별 매매가 통계 (테마)
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/ByUsearea")
    public String getStatTradeByUseAreaList_Seoul(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                                  @RequestParam(value = "term", defaultValue = "3") String term,
                                                  Model model) {
        log.info("/stat_trade/ByUsearea?sigunguCode=" + sigunguCode + "&term=" + term);
        List<StatResponseDto> listUA02;
        List<StatResponseDto> listUA05;
        List<StatResponseDto> listUA06;
        List<StatResponseDto> listUA07;
        List<StatResponseDto> listUA08;
        String title = "-";
        if (!sigunguCode.equals("0")) {
            title = codeInfoService.getCodeName(sigunguCode);
            apiCallStatService.writeApiCallStat("STAT_UA", "/stat_trade/ByUsearea/" + title + "/" + term, sigunguCode);
            listUA02 = statService.getStatTradeByUseAreaList(sigunguCode,"UA02", term);
            listUA05 = statService.getStatTradeByUseAreaList(sigunguCode,"UA05", term);
            listUA06 = statService.getStatTradeByUseAreaList(sigunguCode,"UA06", term);
            listUA07 = statService.getStatTradeByUseAreaList(sigunguCode,"UA07", term);
            listUA08 = statService.getStatTradeByUseAreaList(sigunguCode,"UA08", term);
        }
        else{
            listUA02 = new ArrayList<>();
            listUA05 = new ArrayList<>();
            listUA06 = new ArrayList<>();
            listUA07 = new ArrayList<>();
            listUA08 = new ArrayList<>();
        }
        List<String> dates = listUA02.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Integer> avgPricesUA02 = listUA02.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgPricesUA05 = listUA05.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgPricesUA06 = listUA06.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgPricesUA07 = listUA07.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgPricesUA08 = listUA08.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());

        List<Integer> tradCntUA02 = listUA02.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCntUA05 = listUA05.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCntUA06 = listUA06.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCntUA07 = listUA07.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCntUA08 = listUA08.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgPricesUA02);
        Collections.reverse(avgPricesUA05);
        Collections.reverse(avgPricesUA06);
        Collections.reverse(avgPricesUA07);
        Collections.reverse(avgPricesUA08);

        Collections.reverse(tradCntUA02);
        Collections.reverse(tradCntUA05);
        Collections.reverse(tradCntUA06);
        Collections.reverse(tradCntUA07);
        Collections.reverse(tradCntUA08);

        int maxAmt = 0;
        if (avgPricesUA06.size() > 0) {
            maxAmt = Collections.max(avgPricesUA06);
        }

        int maxCnt = 0;
        if (tradCntUA02.size() > 0) {
            maxCnt = Collections.max(tradCntUA02);
            if (Collections.max(tradCntUA06) > maxCnt) {
                maxCnt = Collections.max(tradCntUA06);
            }
            if (Collections.max(tradCntUA05) > maxCnt) {
                maxCnt = Collections.max(tradCntUA05);
            }
            if (Collections.max(tradCntUA06) > maxCnt) {
                maxCnt = Collections.max(tradCntUA06);
            }
            if (Collections.max(tradCntUA07) > maxCnt) {
                maxCnt = Collections.max(tradCntUA07);
            }
            if (Collections.max(tradCntUA08) > maxCnt) {
                maxCnt = Collections.max(tradCntUA08);
            }
        }

        model.addAttribute("title", title);
        model.addAttribute("termStr", Common.makeTermString(term));
        model.addAttribute("term", term);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("dates", dates);
        model.addAttribute("maxAmt", maxAmt);
        model.addAttribute("maxCnt", maxCnt);
        model.addAttribute("avgPricesUA02", avgPricesUA02);
        model.addAttribute("avgPricesUA05", avgPricesUA05);
        model.addAttribute("avgPricesUA06", avgPricesUA06);
        model.addAttribute("avgPricesUA07", avgPricesUA07);
        model.addAttribute("avgPricesUA08", avgPricesUA08);

        model.addAttribute("tradCntUA02", tradCntUA02);
        model.addAttribute("tradCntUA05", tradCntUA05);
        model.addAttribute("tradCntUA06", tradCntUA06);
        model.addAttribute("tradCntUA07", tradCntUA07);
        model.addAttribute("tradCntUA08", tradCntUA08);

        return "stat_trade/statByUsearea";
    }


    @GetMapping("/stat_trade/newHighestAmt")
    public String getStatNewHighestAndTradeCount(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                                 @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                                 @RequestParam(value = "term", defaultValue = "0") int term,
                                                 HttpServletRequest request,
                                                 Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        List<StatResponseDto> stats;
        String title = "-";
        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url = "/stat_trade/newHighest?sigunguCode=" + sigunguCode + "&uaTYpe=" + uaType;
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("STAT_THEME", "/stat_trade/newHighest?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                stats = statService.getStatNewHighestAndTradeCount(sigunguCode, uaType, term);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            apiCallStatService.writeApiCallStat("ERROR", "(error) /stat_trade/newHighestAmt?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }
        List<String> dates = stats.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Integer> avgPrices = stats.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Float> newHighests = stats.stream().map(o->new Float(o.getHighestRate())).collect(Collectors.toList());
        List<Integer> tradcnt = stats.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgPrices);
        Collections.reverse(newHighests);
        Collections.reverse(tradcnt);

        model.addAttribute("dates", dates);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("term", term);
        model.addAttribute("termStr", Common.makeTermString(term));
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("avgPrices", avgPrices);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("newHighests", newHighests);
        model.addAttribute("list",  stats);
        model.addAttribute("title",  title);

        if(sigunguCode.substring(0,2).equals("11")){
            return "stat_trade/newHighestAmt/seoul";
        } else if (sigunguCode.substring(0, 2).equals("41")) {
            return "stat_trade/newHighestAmt/gyunggi";
        } else {
            return "stat_trade/newHighestAmt/incheon";
        }
    }

    /**
     * 거래유형별(직/중개거래) 월별통계
     * @param sigunguCode
     * @param uaType
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/ByDealType")
    public String getStatByDealType(@RequestParam(value = "sigunguCode", defaultValue = "0") String sigunguCode,
                                    @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                    HttpServletRequest request,
                                    Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        List<StatResponseDto> stats;
        List<StatResponseDto> stats0;
        List<StatResponseDto> stats1;
        String title = "-";
        String title2 = "-";
        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url = "/stat_trade/ByDealType?sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_trade/ByDealType?" + title, sigunguCode);
            if (sigunguCode.length() == 5) {
                stats = statService.getStatTradeByUseAreaList(sigunguCode, uaType, "1");
            } else {
                stats = statService.getStatTradeByUseAreaList(sigunguCode, uaType, "1");
            }
            stats0 = statService.getStatByDealType(sigunguCode, uaType, 0); //중개거래
            stats1 = statService.getStatByDealType(sigunguCode, uaType, 1); //직거래
        } else {
            apiCallStatService.writeApiCallStat("ERROR", "(error) /stat_trade/ByDealType?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }

        List<String> dates = stats.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());

        List<DealTypeDto> list = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            String tmp = dates.get(i);
            StatResponseDto statItem = stats.stream().filter(o -> o.getDealYYMM().equals(tmp)).findFirst().orElse(null);
            StatResponseDto statItem0 = stats0.stream().filter(o -> o.getDealYYMM().equals(tmp)).findFirst().orElse(null);
            StatResponseDto statItem1 = stats1.stream().filter(o -> o.getDealYYMM().equals(tmp)).findFirst().orElse(null);
            if (statItem0 == null) {
                statItem0 = new StatResponseDto();
            }
            if (statItem1 == null) {
                statItem1 = new StatResponseDto();
            }
            DealTypeDto item = new DealTypeDto(tmp, statItem, statItem0, statItem1);
            list.add(item);
        }

        List<Float> avgprc = list.stream().map(o->new Float(o.getAvgPrice())).collect(Collectors.toList());
        List<Float> avgprc0 = list.stream().map(o->new Float(o.getAvgPrice0())).collect(Collectors.toList());
        List<Float> avgprc1 = list.stream().map(o->new Float(o.getAvgPrice1())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgprc);
        Collections.reverse(avgprc0);
        Collections.reverse(avgprc1);

        String areaCode = sigunguCode.substring(0, 2);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);

        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("title",  title);
        model.addAttribute("title2",  title2);
        model.addAttribute("headerTitle", title + " 직/중개 통계");
        model.addAttribute("sigunguCode",  sigunguCode);
        model.addAttribute("list", list);
        model.addAttribute("uaType", uaType);
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
//        model.addAttribute("ua", ua);
        model.addAttribute("dates", dates);
        model.addAttribute("avgprc", avgprc);
        model.addAttribute("avgprc0", avgprc0);
        model.addAttribute("avgprc1", avgprc1);

        if (areaCode.equals("11")) {
            return "stat_trade/dealType/seoul";
        } else if (areaCode.equals("41")) {
            return "stat_trade/dealType/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "stat_trade/dealType/guSelect";
        } else {
            return "stat_trade/dealType/regionSelect";
        }
    }

    @Data
    static class DealTypeDto{
        private String date;
        private float avgPrice;
        private float avgPrice0;
        private float avgPrice1;
        private String avgPriceStr;
        private String avgPriceStr0;
        private String avgPriceStr1;
        private int cnt;
        private int cnt0;
        private int cnt1;
        private float avgUseArea;
        private float avgUseArea0;
        private float avgUseArea1;
        private float highestRate;
        private float highestRate0;
        private float highestRate1;
        public DealTypeDto(String date, StatResponseDto item, StatResponseDto item0, StatResponseDto item1) {
            this.date = date;
            this.avgPrice = (float)item.getAvgPrice() / 10000;
            this.avgPriceStr = item.getAvgPriceStr();
            this.cnt = item.getCnt();
            this.highestRate = item.getHighestRate();
            this.avgUseArea = item.getAvgUseArea();

            this.avgPrice0 = (float)item0.getAvgPrice() / 10000;
            this.avgPriceStr0 = item0.getAvgPriceStr();
            this.cnt0 = item0.getCnt();
            this.highestRate0 = item0.getHighestRate();
            this.avgUseArea0 = item0.getAvgUseArea();

            this.avgPrice1 = (float)item1.getAvgPrice() / 10000;
            this.avgPriceStr1 = item1.getAvgPriceStr();
            this.cnt1 = item1.getCnt();
            this.highestRate1 = item1.getHighestRate();
            this.avgUseArea1 = item1.getAvgUseArea();
        }
    }

    @GetMapping("/stat_trade/top")
    public String GetStatTradeTopByYear(@RequestParam(value = "year", defaultValue = "2023") String year,
                                        @RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                        @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                        HttpServletRequest request,
                                        Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        List<AptPriceResponseDto> tops;
        String title = "-";

        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url = "/stat_trade/top?year=" + year + "&sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("STAT_TOP", "/stat_trade/top?sigunguCode=" +  title + "year="+year, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                tops = statService.getStatTradeTopByYear(year, sigunguCode, uaType);
            } else {
                tops = new ArrayList<>();
            }
        } else {
            apiCallStatService.writeApiCallStat("ERROR", "(error) /stat_trade/top?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }
        String areaCode = sigunguCode.substring(0, 2);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);
        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("title",  title);
        model.addAttribute("list", tops);
        model.addAttribute("uaType", uaType);
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("year", year);
        model.addAttribute("sigunguCode", sigunguCode);
        if (areaCode.equals("11")) {
            return "stat_trade/top/seoul";
        } else if (areaCode.equals("41")) {
            return "stat_trade/top/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "stat_trade/top/guSelect";
        } else {
            return "stat_trade/top/regionSelect";
        }
    }

    @GetMapping("/stat_trade/office/top")
    public String GetStatTradeOfficeTopByYear(@RequestParam(value = "year", defaultValue = "2023") String year,
                                        @RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                        @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                        HttpServletRequest request,
                                        Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        List<AptPriceResponseDto> tops;
        String title = "-";

        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url = "/stat_trade/office/top?year=" + year + "&sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("STAT_TOP", "/stat_trade/office/top?sigunguCode=" +  title + "year="+year, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                tops = statService.getStatTradeOfficeTopByYear(year, sigunguCode, uaType);
            } else {
                tops = new ArrayList<>();
            }
        } else {
            apiCallStatService.writeApiCallStat("ERROR", "(error) /stat_trade/office/top?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }
        String areaCode = sigunguCode.substring(0, 2);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);
        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("title",  title);
        model.addAttribute("list", tops);
        model.addAttribute("uaType", uaType);
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("year", year);
        model.addAttribute("sigunguCode", sigunguCode);
        if (areaCode.equals("11")) {
            return "stat_trade/office/top/seoul";
        } else if (areaCode.equals("41")) {
            return "stat_trade/office/top/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "stat_trade/office/top/guSelect";
        } else {
            return "stat_trade/office/top/regionSelect";
        }
    }
}