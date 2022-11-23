package com.earlmazip.controller;

import com.earlmazip.controller.dto.*;
import com.earlmazip.service.ApiCallStatService;
import com.earlmazip.service.CodeInfoService;
import com.earlmazip.service.EcosDataService;
import com.earlmazip.service.StatService;
import com.earlmazip.utils.Common;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;

    private final ApiCallStatService apiCallStatService;

    private final CodeInfoService codeInfoService;

    private final EcosDataService ecosDataService;

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
                                               Model model) {
        List<StatResponseDto> areas;
        String title = "-";
        if (!sigunguCode.equals("0")) {
            title = codeInfoService.getCodeName(sigunguCode);
            System.out.println("/stat_trade/useareaType?" + sigunguCode + "&" + uaType + "&" + term);
            log.info("/stat_trade/useareaType?" + sigunguCode + "&" + uaType + "&" + term);
            apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_trade/useareaType?sigunguCode=" + title, sigunguCode);
            areas = statService.getStatTradeByUseAreaList(sigunguCode,uaType, term);
        } else {
            areas = new ArrayList<>();
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

        model.addAttribute("title",  title);
        model.addAttribute("headerTitle", title + " 월별 통계");
        model.addAttribute("sigunguCode",  sigunguCode);
        model.addAttribute("term",  term);
        model.addAttribute("termStr", Common.makeTermString(term));
        model.addAttribute("list", areas);
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("uaType", uaType);
        model.addAttribute("dates", dates);
        model.addAttribute("avgprc", avgprc);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("interestRates", interestRates);
        if (sigunguCode.substring(0, 2).equals("11")) {
            return "stat_trade/useAreaType/seoul";
        } else if (sigunguCode.substring(0, 2).equals("41")) {
            return "stat_trade/useAreaType/gyunggi";
        } else {
            return "stat_trade/useAreaType/incheon";
        }

    }

    /**
     * 서울시 월별,전용면적별 매매가 통계 (테마)
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/ByUsearea/{regnCode}/{term}")
    public String getStatTradeByUseAreaList_Seoul(@PathVariable String regnCode, @PathVariable String term, Model model) {
        log.info("/stat_trade/ByUsearea/" + regnCode + "/" + term);
        List<StatResponseDto> listUA02;
        List<StatResponseDto> listUA03;
        List<StatResponseDto> listUA04;
        List<StatResponseDto> listUA05;
        List<StatResponseDto> listUA06;
        List<StatResponseDto> listUA07;
        String title = "-";
        if (!regnCode.equals("0")) {
            title = codeInfoService.getCodeName(regnCode);
            apiCallStatService.writeApiCallStat("STAT_UA", "/stat_trade/ByUsearea/" + title + "/" + term, regnCode);
            listUA02 = statService.getStatTradeByUseAreaList(regnCode,"UA02", term);
            listUA03 = statService.getStatTradeByUseAreaList(regnCode,"UA03", term);
            listUA04 = statService.getStatTradeByUseAreaList(regnCode,"UA04", term);
            listUA05 = statService.getStatTradeByUseAreaList(regnCode,"UA05", term);
            listUA06 = statService.getStatTradeByUseAreaList(regnCode,"UA06", term);
            listUA07 = statService.getStatTradeByUseAreaList(regnCode,"UA07", term);
        }
        else{
            listUA02 = new ArrayList<>();
            listUA03 = new ArrayList<>();
            listUA04 = new ArrayList<>();
            listUA05 = new ArrayList<>();
            listUA06 = new ArrayList<>();
            listUA07 = new ArrayList<>();
        }
        List<String> dates = listUA02.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Integer> avgPricesUA02 = listUA02.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgPricesUA03 = listUA03.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgPricesUA04 = listUA04.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgPricesUA05 = listUA05.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgPricesUA06 = listUA06.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgPricesUA07 = listUA07.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());

        List<Integer> tradCntUA02 = listUA02.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCntUA03 = listUA03.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCntUA04 = listUA04.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCntUA05 = listUA05.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCntUA06 = listUA06.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCntUA07 = listUA07.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgPricesUA02);
        Collections.reverse(avgPricesUA03);
        Collections.reverse(avgPricesUA04);
        Collections.reverse(avgPricesUA05);
        Collections.reverse(avgPricesUA06);
        Collections.reverse(avgPricesUA07);

        Collections.reverse(tradCntUA02);
        Collections.reverse(tradCntUA03);
        Collections.reverse(tradCntUA04);
        Collections.reverse(tradCntUA05);
        Collections.reverse(tradCntUA06);
        Collections.reverse(tradCntUA07);

        int maxAmt = 0;
        if (avgPricesUA06.size() > 0) {
            maxAmt = Collections.max(avgPricesUA06);
        }

        int maxCnt = 0;
        if (tradCntUA02.size() > 0) {
            maxCnt = Collections.max(tradCntUA02);
            if (Collections.max(tradCntUA03) > maxCnt) {
                maxCnt = Collections.max(tradCntUA03);
            }
            if (Collections.max(tradCntUA04) > maxCnt) {
                maxCnt = Collections.max(tradCntUA04);
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
        }

        model.addAttribute("title",  "[ "+  title + " ]");

        model.addAttribute("dates", dates);
        model.addAttribute("maxAmt", maxAmt);
        model.addAttribute("maxCnt", maxCnt);
        model.addAttribute("avgPricesUA02", avgPricesUA02);
        model.addAttribute("avgPricesUA03", avgPricesUA03);
        model.addAttribute("avgPricesUA04", avgPricesUA04);
        model.addAttribute("avgPricesUA05", avgPricesUA05);
        model.addAttribute("avgPricesUA06", avgPricesUA06);
        model.addAttribute("avgPricesUA07", avgPricesUA07);

        model.addAttribute("tradCntUA02", tradCntUA02);
        model.addAttribute("tradCntUA03", tradCntUA03);
        model.addAttribute("tradCntUA04", tradCntUA04);
        model.addAttribute("tradCntUA05", tradCntUA05);
        model.addAttribute("tradCntUA06", tradCntUA06);
        model.addAttribute("tradCntUA07", tradCntUA07);

        return "stat_trade/statByUsearea";
    }


    @GetMapping("/stat_trade/newHighestAndTradeCntByCity/{sidocode}")
    public String getStatNewHighestAndTradeCount(@PathVariable String sidocode, Model model) {
        List<StatResponseDto> stats;
        String title = "-";
        if (!sidocode.equals("0")) {
            title = codeInfoService.getCodeName(sidocode);
            log.info("/stat_trade/newHighestAndTradeCntByCity/" + sidocode);
            apiCallStatService.writeApiCallStat("STAT_THEME", "/stat_trade/newHighestAndTradeCntByCity/" + title, sidocode);
            if (StringUtils.hasText(sidocode)) {
                stats = statService.getStatNewHighestAndTradeCount(sidocode);
            } else {
                stats = new ArrayList<>();
            }
        } else {
            stats = new ArrayList<>();
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
        model.addAttribute("avgPrices", avgPrices);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("newHighests", newHighests);
        model.addAttribute("title",  "[ "+ title + " ]");

        return "stat_trade/newHighestAndTradeCntByCity";
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
                                    Model model) {
        List<StatResponseDto> stats;
        List<StatResponseDto> stats0;
        List<StatResponseDto> stats1;
        String title = "-";
        String title2 = "-";
        if (!sigunguCode.equals("0")) {
            title = codeInfoService.getCodeName(sigunguCode);
            log.info("/stat_trade/ByDealType?" + sigunguCode + "&uaType" + uaType);
            apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_trade/ByDealType?" + title, sigunguCode);
            if (sigunguCode.length() == 5) {
                stats = statService.getStatTradeByUseAreaList(sigunguCode, uaType, "0");
            } else {
                stats = statService.getStatTradeByUseAreaList(sigunguCode, uaType, "0");
            }
            stats0 = statService.getStatByDealType(sigunguCode, uaType, 0); //중개거래
            stats1 = statService.getStatByDealType(sigunguCode, uaType, 1); //직거래
        } else {
            stats = new ArrayList<>();
            stats0 = new ArrayList<>();
            stats1 = new ArrayList<>();
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

        model.addAttribute("title",  title);
        model.addAttribute("title2",  title2);
        model.addAttribute("headerTitle", title + " 직/중개 통계");
        model.addAttribute("sigungucode",  sigunguCode);
        model.addAttribute("list", list);
        model.addAttribute("uaType", uaType);
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
//        model.addAttribute("ua", ua);
        model.addAttribute("dates", dates);
        model.addAttribute("avgprc", avgprc);
        model.addAttribute("avgprc0", avgprc0);
        model.addAttribute("avgprc1", avgprc1);
        if (sigunguCode.substring(0, 2).equals("11")) {
            return "stat_trade/dealType/seoul";
        } else if (sigunguCode.substring(0, 2).equals("41")) {
            return "stat_trade/dealType/gyunggi";
        } else {
            return "stat_trade/dealType/incheon";
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

    @GetMapping("/stat_trade/ByBuildYear/{regncode}/{term}")
    public String getStatBuildYearList(@PathVariable String regncode,
                                                    @PathVariable String term,
                                                    Model model) {
        List<StatResponseDto> stat1970;
        List<StatResponseDto> stat1980;
        List<StatResponseDto> stat1990;
        List<StatResponseDto> stat2000;
        List<StatResponseDto> stat2010;
        List<StatResponseDto> stat2020;
        if (!regncode.equals("0")) {
            log.info("/stat_trade/ByBuildYear/" + regncode + "/" + term);
            apiCallStatService.writeApiCallStat("STAT", "/stat_trade/ByBuildYear/" + regncode + "/" + term, regncode);
            stat1970 = statService.getStatBuildYearList(regncode, "1970", term);
            stat1980 = statService.getStatBuildYearList(regncode, "1980", term);
            stat1990 = statService.getStatBuildYearList(regncode, "1990", term);
            stat2000 = statService.getStatBuildYearList(regncode, "2000", term);
            stat2010 = statService.getStatBuildYearList(regncode, "2010", term);
            stat2020 = statService.getStatBuildYearList(regncode, "2020", term);

        } else {
            stat1970 = new ArrayList<>();
            stat1980 = new ArrayList<>();
            stat1990 = new ArrayList<>();
            stat2000 = new ArrayList<>();
            stat2010 = new ArrayList<>();
            stat2020 = new ArrayList<>();
        }
        List<String> dates = stat1990.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Integer> avgprc1970 = stat1970.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgprc1980 = stat1980.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgprc1990 = stat1990.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgprc2000 = stat2000.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgprc2010 = stat2010.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Integer> avgprc2020 = stat2020.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());

        List<Integer> tradCnt1970 = stat1970.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCnt1980 = stat1980.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCnt1990 = stat1990.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCnt2000 = stat2000.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCnt2010 = stat2010.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradCnt2020 = stat2020.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgprc1970);
        Collections.reverse(avgprc1980);
        Collections.reverse(avgprc1990);
        Collections.reverse(avgprc2000);
        Collections.reverse(avgprc2010);
        Collections.reverse(avgprc2020);

        Collections.reverse(tradCnt1970);
        Collections.reverse(tradCnt1980);
        Collections.reverse(tradCnt1990);
        Collections.reverse(tradCnt2000);
        Collections.reverse(tradCnt2010);
        Collections.reverse(tradCnt2020);

        String title = codeInfoService.getCodeName(regncode);

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("dates", dates);
        model.addAttribute("avgprc1970", avgprc1970);
        model.addAttribute("avgprc1980", avgprc1980);
        model.addAttribute("avgprc1990", avgprc1990);
        model.addAttribute("avgprc2000", avgprc2000);
        model.addAttribute("avgprc2010", avgprc2010);
        model.addAttribute("avgprc2020", avgprc2020);

        model.addAttribute("tradCnt1970", tradCnt1970);
        model.addAttribute("tradCnt1980", tradCnt1980);
        model.addAttribute("tradCnt1990", tradCnt1990);
        model.addAttribute("tradCnt2000", tradCnt2000);
        model.addAttribute("tradCnt2010", tradCnt2010);
        model.addAttribute("tradCnt2020", tradCnt2020);

        return "stat_trade/statByBuildYear";
    }
}