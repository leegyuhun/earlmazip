package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.EcosDataResponseDto;
import aptdata.earlmazip.controller.dto.RankYearResponseDto;
import aptdata.earlmazip.controller.dto.StatLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatResponseDto;
import aptdata.earlmazip.domain.RankYear;
import aptdata.earlmazip.domain.StatAreaYYMM;
import aptdata.earlmazip.domain.StatSidoYYMM;
import aptdata.earlmazip.service.ApiCallStatService;
import aptdata.earlmazip.service.CodeInfoService;
import aptdata.earlmazip.service.EcosDataService;
import aptdata.earlmazip.service.StatService;
import aptdata.earlmazip.utils.Common;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
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
     * 서울시,인천시,경기도 월별 매매가 통계
     * @param term : 1인경우 현재년도 -1, 3인경우 현재년도 -3 부터 ~ 현재까지 조회
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/{areaCode}/{term}")
    public String getStatTradeList_Area(@PathVariable String areaCode,
                                        @PathVariable String term, Model model) {
        String title = "-";
        List<StatResponseDto> areas;
        if (areaCode != "") {
            title = codeInfoService.getCodeName(areaCode);
            log.info("/stat_trade/" + title + "/" + term);
            apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_trade/" + title + "/" + term, areaCode);
            areas = statService.getStatTradeList_Area(areaCode, term);
        } else {
            areas = new ArrayList<>();
        }

        List<String> dates = areas.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Float> avgprc = areas.stream().map(o->new Float((float)o.getAvgPrice()/10000)).collect(Collectors.toList());
        List<Integer> tradcnt = areas.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        // 한국은행 기준금리
        List<EcosDataResponseDto> rates = ecosDataService.getEcosData("722Y001", "0101000", "", term);
        List<String> interestRates = rates.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgprc);
        Collections.reverse(tradcnt);

        model.addAttribute("list", areas);
        model.addAttribute("title", title);
        model.addAttribute("dates", dates);
        model.addAttribute("avgprc", avgprc);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("areacode", areaCode);
        model.addAttribute("interestRates", interestRates);

        return "stat_trade/statArea";
    }

    /**
     * 서울시 월별 매매가 통계 (삭제해야됨)
     * @param term : 1인경우 현재년도 -1, 3인경우 현재년도 -3 부터 ~ 현재까지 조회
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/seoul/{term}")
    public String getStatTradeList_Seoul(@PathVariable String term, Model model) {
        log.info("/stat_trade/seoul/" + term);
        apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_trade/seoul/" + term, "11");
        return getStatTradeList_Area("11", term, model);
    }

    /**
     * 서울시 구별 월별 매매가 통계
     * @param term : 1인경우 현재년도 -1, 3인경우 현재년도 -3 부터 ~ 현재까지 조회
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/seoul/{sigungucode}/{term}")
    public String getStatTradeList_SeoulBySigungu(@PathVariable String sigungucode,
                                                  @PathVariable String term,
                                                  Model model) {
        List<StatResponseDto> areas;
        String title = "-";
        if (!sigungucode.equals("0")) {
            title = codeInfoService.getCodeName(sigungucode);
            log.info("/stat_trade/seoul/" + sigungucode + "/" + term);
            apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_trade/seoul/" + title + "/" + term, sigungucode);
            areas = statService.getStatTradeList_BySigungu(sigungucode, term);
        } else {
            areas = new ArrayList<>();
        }
        List<String> dates = areas.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Float> avgprc = areas.stream().map(o->new Float((float)o.getAvgPrice()/10000)).collect(Collectors.toList());
        List<Integer> tradcnt = areas.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        // 한국은행 기준금리
        List<EcosDataResponseDto> rates = ecosDataService.getEcosData("722Y001", "0101000", "", term);
        List<String> interestRates = rates.stream().map(o->new String(o.getDataValue())).collect(Collectors.toList());

        Collections.reverse(dates);
        Collections.reverse(avgprc);
        Collections.reverse(tradcnt);

        model.addAttribute("title",  title);
        model.addAttribute("termStr",  Common.makeTermString(term));
        model.addAttribute("list", areas);
        model.addAttribute("term", term);
        model.addAttribute("sigungucode", sigungucode);
        model.addAttribute("dates", dates);
        model.addAttribute("avgprc", avgprc);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("interestRates", interestRates);
        return "stat_trade/seoulBySigungu";
    }

    /**
     * 서울시 전용면적별 월별 매매 통계
     * @param sigungucode
     * @param ua
     * @param term
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/useareaType/{sigungucode}/{ua}/{term}")
    public String getStatUseareaType_BySigungu(@PathVariable String sigungucode,
                                                  @PathVariable String ua,
                                                  @PathVariable String term,
                                                  Model model) {
        List<StatResponseDto> areas;
        String title = "-";
        if (!sigungucode.equals("0")) {
            title = codeInfoService.getCodeName(sigungucode);
            log.info("/stat_trade/useareaType/" + sigungucode + "/" + ua + "/" + term);
            apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_trade/useareaType/" + title + "/" + ua + "/" + term, sigungucode);
            areas = statService.getStatTradeByUseAreaList(sigungucode,ua, term);
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
        model.addAttribute("sigungucode",  sigungucode);
        model.addAttribute("term",  term);
        model.addAttribute("termStr", Common.makeTermString(term));
        model.addAttribute("list", areas);
        model.addAttribute("uaStr", codeInfoService.getCodeName(ua));
        model.addAttribute("ua", ua);
        model.addAttribute("dates", dates);
        model.addAttribute("avgprc", avgprc);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("interestRates", interestRates);
        if (sigungucode.substring(0, 2).equals("11")) {
            return "stat_trade/useAreaType_Seoul";
        } else if (sigungucode.substring(0, 2).equals("41")) {
            return "stat_trade/useAreaType_Gyunggi";
        } else {
            return "stat_trade/useAreaType_Incheon";
        }
    }

    /**
     * 서울시 월별,전용면적별 매매가 통계
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

    /**
     * 서울시 구별 매매가 상위 100
     * @param
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/seoul/top/{year}/{sigungucode}")
    public String getStatTradeTopSeoulByYear(@PathVariable String year,
                                             @PathVariable String sigungucode,
                                             @RequestParam(value="ua", defaultValue = "UA01") String ua,
                                             Model model) {
        List<RankYearResponseDto> tops;
        String title = "-";
        if (!sigungucode.equals("0")) {
            title = codeInfoService.getCodeName(sigungucode);
            log.info("/stat_trade/seoul/top/" + year + "/" +  sigungucode);
            apiCallStatService.writeApiCallStat("STAT_TOP", "/stat_trade/seoul/top/" + year + "/" +  title + "?ua=" + ua, sigungucode);
            if (StringUtils.hasText(sigungucode)) {
                tops = statService.getStatTradeTopSeoulByYear(year, sigungucode, ua);
            } else {
                tops = new ArrayList<>();
            }
        } else {
            tops = new ArrayList<>();
        }

        model.addAttribute("title",  title);
        model.addAttribute("list", tops);
        model.addAttribute("ua", ua);
        model.addAttribute("uaStr", codeInfoService.getCodeName(ua));
        model.addAttribute("year", year);
        model.addAttribute("sigungucode", sigungucode);

        return "stat_trade/seoulTop";
    }

    /**
     * 경기도 월별 매매가 통계 (삭졔해야됨)
     * @param term : 1인경우 현재년도 -1, 3인경우 현재년도 -3 부터 ~ 현재까지 조회
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/gyunggi/{term}")
    public String getStatTradeGyunggi(@PathVariable String term, Model model) {
        log.info("/stat_trade/gyunggi/" + term);
        apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_trade/gyunggi/" + term, "41");
        return getStatTradeList_Area("41", term, model);
    }

    @GetMapping("/stat_trade/gyunggiByCity/{sidoCode}/{term}")
    public String getStatTradeList_ByCity(@PathVariable String sidoCode,
                                @PathVariable String term,Model model) {
        log.info("/stat_trade/gyunggiByCity/" + sidoCode + "/" + term);
        apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_trade/gyunggiByCity/" + sidoCode+ "/" + term, sidoCode);


        return getStatUseareaType_BySigungu(sidoCode, "UA01", term, model);
    }

    @GetMapping("/stat_trade/gyunggi/top/{year}/{sigungucode}")
    public String gyunggiTopList(@PathVariable String year,
                                 @PathVariable String sigungucode,
                                 @RequestParam(value="ua", defaultValue = "UA01") String ua,
                                 Model model) {
        List<RankYearResponseDto> tops;
        String title = "-";
        if (!sigungucode.equals("0")) {
            title = codeInfoService.getCodeName(sigungucode);
            log.info("/stat_trade/gyunggi/top/" + year + "/" + sigungucode);
            apiCallStatService.writeApiCallStat("STAT_TOP", "/stat_trade/gyunggi/top/" + year + "/" + title + "?ua=" + ua, sigungucode);
            if (StringUtils.hasText(sigungucode)) {
                tops = statService.findGyunggiTopList(year, sigungucode, ua);
            } else {
                tops = new ArrayList<>();
            }
        } else {
            tops = new ArrayList<>();
        }

        model.addAttribute("list", tops);
        model.addAttribute("year", year);
        model.addAttribute("ua", ua);
        model.addAttribute("uaStr", codeInfoService.getCodeName(ua));
        model.addAttribute("sigungucode", sigungucode);
        model.addAttribute("title",  title);
        return "stat_trade/gyunggiTop";
    }

    @GetMapping("/stat_trade/incheon/top/{year}/{sigungucode}")
    public String incheonTopList(@PathVariable String year,
                                 @PathVariable String sigungucode,
                                 @RequestParam(value="ua", defaultValue = "UA01") String ua,
                                 Model model) {
        List<RankYearResponseDto> tops;
        String title = "-";
        if (!sigungucode.equals("0")) {
            title = codeInfoService.getCodeName(sigungucode);
            log.info("/stat_trade/incheon/top/" + year + "/" + sigungucode);
            apiCallStatService.writeApiCallStat("STAT_TOP", "/stat_trade/incheon/top/" + year + "/" + title + "?ua=" + ua, sigungucode);
            if (StringUtils.hasText(sigungucode)) {
                tops = statService.findIncheonTopList(year, sigungucode, ua);
            } else {
                tops = new ArrayList<>();
            }
        } else {
            tops = new ArrayList<>();
        }
        model.addAttribute("list", tops);
        model.addAttribute("ua", ua);
        model.addAttribute("uaStr", codeInfoService.getCodeName(ua));
        model.addAttribute("year", year);
        model.addAttribute("title",  "[ "+ title + " ]");
        return "stat_trade/incheonTop";
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
     * 강남3구 15억이상 거래
     * @param term
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/theme01/{term}")
    public String getStatTheme(@PathVariable String term, Model model) {
        List<StatResponseDto> stats;
        log.info("/stat_trade/theme01/" + term);
        apiCallStatService.writeApiCallStat("STAT_THEME", "/stat_trade/theme01/" + term, "0");
        stats = statService.getStatTheme("001", term);

        List<String> dates = stats.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Float> avgPrices = stats.stream().map(o->new Float((float)o.getAvgPrice()/10000)).collect(Collectors.toList());
//        List<Integer> avgPrices = stats.stream().map(o->new Integer(o.getAvgPrice())).collect(Collectors.toList());
        List<Float> newHighests = stats.stream().map(o->new Float(o.getHighestRate())).collect(Collectors.toList());
        List<Integer> tradcnt = stats.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        String title = stats.get(0).getName();

        Collections.reverse(dates);
        Collections.reverse(avgPrices);
        Collections.reverse(newHighests);
        Collections.reverse(tradcnt);

        model.addAttribute("dates", dates);
        model.addAttribute("avgPrices", avgPrices);
        model.addAttribute("tradcnt", tradcnt);
        model.addAttribute("newHighests", newHighests);
        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("list", stats);

        return "stat_trade/statTheme";
    }

    /**
     * 서울 4구역 통계 (강남3구, 마용성, 노도강, 금관구)
     * @param term
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/theme02/{term}")
    public String getStatTheme02(@PathVariable String term, Model model) {
        List<StatResponseDto> stats002;
        List<StatResponseDto> stats003;
        List<StatResponseDto> stats004;
        List<StatResponseDto> stats005;
        log.info("/stat_trade/theme02/" + term);
        apiCallStatService.writeApiCallStat("STAT_THEME", "/stat_trade/theme02/" + term, "0");
        stats002 = statService.getStatTheme("002", term);
        stats003 = statService.getStatTheme("003", term);
        stats004 = statService.getStatTheme("004", term);
        stats005 = statService.getStatTheme("005", term);

        List<String> dates = stats002.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
//        List<Float> avgprc = areas.stream().map(o->new Float((float)o.getAvgPrice()/10000)).collect(Collectors.toList());
        List<Float> avgPrices002 = stats002.stream().map(o->new Float((float)o.getAvgPrice()/10000)).collect(Collectors.toList());
        List<Float> avgPrices003 = stats003.stream().map(o->new Float((float)o.getAvgPrice()/10000)).collect(Collectors.toList());
        List<Float> avgPrices004 = stats004.stream().map(o->new Float((float)o.getAvgPrice()/10000)).collect(Collectors.toList());
        List<Float> avgPrices005 = stats005.stream().map(o->new Float((float)o.getAvgPrice()/10000)).collect(Collectors.toList());

        List<Float> newHighests002 = stats002.stream().map(o->new Float(o.getHighestRate())).collect(Collectors.toList());
        List<Float> newHighests003 = stats003.stream().map(o->new Float(o.getHighestRate())).collect(Collectors.toList());
        List<Float> newHighests004 = stats004.stream().map(o->new Float(o.getHighestRate())).collect(Collectors.toList());
        List<Float> newHighests005 = stats005.stream().map(o->new Float(o.getHighestRate())).collect(Collectors.toList());

        List<Integer> tradcnt002 = stats002.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradcnt003 = stats003.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradcnt004 = stats004.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<Integer> tradcnt005 = stats005.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());
        List<theme02Dto> list = new ArrayList<>();
        for (int i = 0; i < dates.size() - 1; i++) {
            theme02Dto item = new theme02Dto(dates.get(i), avgPrices002.get(i), avgPrices003.get(i), avgPrices004.get(i), avgPrices005.get(i),
                    tradcnt002.get(i), tradcnt003.get(i), tradcnt004.get(i), tradcnt005.get(i));
            list.add(item);
        }
        Collections.reverse(dates);
        Collections.reverse(avgPrices002);
        Collections.reverse(avgPrices003);
        Collections.reverse(avgPrices004);
        Collections.reverse(avgPrices005);

        Collections.reverse(newHighests002);
        Collections.reverse(newHighests003);
        Collections.reverse(newHighests004);
        Collections.reverse(newHighests005);

        Collections.reverse(tradcnt002);
        Collections.reverse(tradcnt003);
        Collections.reverse(tradcnt004);
        Collections.reverse(tradcnt005);

        model.addAttribute("dates", dates);
        model.addAttribute("list", list);
        model.addAttribute("avgPrices002", avgPrices002);
        model.addAttribute("avgPrices003", avgPrices003);
        model.addAttribute("avgPrices004", avgPrices004);
        model.addAttribute("avgPrices005", avgPrices005);

        model.addAttribute("tradcnt002", tradcnt002);
        model.addAttribute("tradcnt003", tradcnt003);
        model.addAttribute("tradcnt004", tradcnt004);
        model.addAttribute("tradcnt005", tradcnt005);

        model.addAttribute("newHighests002", newHighests002);
        model.addAttribute("newHighests003", newHighests003);
        model.addAttribute("newHighests004", newHighests004);
        model.addAttribute("newHighests005", newHighests005);
        model.addAttribute("title",  "[ 서울 4구역 통계 ]");
//        model.addAttribute("list", stats);

        return "stat_trade/statTheme02";
    }

    @Data
    static class theme02Dto{
        private String date;
        private float item1;
        private float item2;
        private float item3;
        private float item4;

        private int cnt1;
        private int cnt2;
        private int cnt3;
        private int cnt4;

        public theme02Dto(String date, float item1, float item2, float item3, float item4,
                            int cnt1, int cnt2, int cnt3, int cnt4) {
            this.date = date;
            this.item1 = item1;
            this.item2 = item2;
            this.item3 = item3;
            this.item4 = item4;

            this.cnt1 = cnt1;
            this.cnt2 = cnt2;
            this.cnt3 = cnt3;
            this.cnt4 = cnt4;
        }
    }

    /**
     * 가계대출
     * @param areacode
     * @param term
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/theme03/{areacode}/{term}")
    public String getStatTheme03(@PathVariable String areacode,
                                 @PathVariable String term, Model model) {
        log.info("/stat_trade/theme03/" + areacode + "/" + term);
        apiCallStatService.writeApiCallStat("STAT_THEME", "/stat_trade/stheme03/" + areacode + "/" + term, "0");
        List<StatResponseDto> areas;
        String title = "-";
        title = codeInfoService.getCodeName(areacode);
        if (areacode.equals("11")) {
            areas = statService.getStatTradeList_Seoul(term);
        } else if (areacode.equals("41")) {
            areas = statService.getStatTradeList_Gyunggi(term);
        } else if (areacode.equals("28")) {
            areas = statService.getStatTradeList_Incheon(term);
        } else {
            areas = new ArrayList<>();
        }

        List<String> dates = areas.stream().map(o->new String(o.getDealYYMM())).collect(Collectors.toList());
        List<Float> avgprc = areas.stream().map(o->new Float((float)o.getAvgPrice()/10000)).collect(Collectors.toList());

        // 주담대
        List<EcosDataResponseDto> houseDebt;
        if (areacode.equals("11")) {
            houseDebt = ecosDataService.getEcosData("008Y003", "11100A0", "A", term);
        } else if (areacode.equals("41")) {
            houseDebt = ecosDataService.getEcosData("008Y003", "11100A0", "L", term);
        } else if (areacode.equals("28")) {
            houseDebt = ecosDataService.getEcosData("008Y003", "11100A0", "D", term);
        } else {
            houseDebt = new ArrayList<>();
        }
        List<Float> houseDebts = houseDebt.stream().map(o->new Float(o.getValue())).collect(Collectors.toList());

        // 기타대출
        List<EcosDataResponseDto> etcDebt;
        if (areacode.equals("11")) {
            etcDebt = ecosDataService.getEcosData("008Y003", "11100B0", "A", term);
        } else if (areacode.equals("41")) {
            etcDebt = ecosDataService.getEcosData("008Y003", "11100B0", "L", term);
        } else if (areacode.equals("28")) {
            etcDebt = ecosDataService.getEcosData("008Y003", "11100B0", "D", term);
        } else {
            etcDebt = new ArrayList<>();
        }
        List<Float> etcDebts = etcDebt.stream().map(o->new Float(o.getValue())).collect(Collectors.toList());
        String subtitle = "-";
        if (houseDebt.size() > 0){
            subtitle = houseDebt.get(0).getStatName();
        }

        Collections.reverse(dates);
        Collections.reverse(avgprc);
        Collections.reverse(houseDebt);

        model.addAttribute("list", houseDebt);
        model.addAttribute("dates", dates);
        model.addAttribute("avgprc", avgprc);
        model.addAttribute("title", "[ " + title + " ]");
        model.addAttribute("subtitle", "[ " + subtitle + " ]");
        model.addAttribute("etcDebts", etcDebts);
        model.addAttribute("houseDebts", houseDebts);

//        model.addAttribute("interestRates", interestRates);
        return "stat_trade/statTheme03";
    }

    @GetMapping("/stat_trade/gyunggiBySigungu/{sigungucode}/{term}")
    public String getStatTradeList_GyunggiBySigungu(@PathVariable String sigungucode,
                                                  @PathVariable String term,
                                                  Model model) {
        List<StatResponseDto> areas;
        String title = "-";
        title = codeInfoService.getCodeName(sigungucode);
        log.info("/stat_trade/gyunggiBySigungu/" + sigungucode + "/" + term);
        apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_trade/gyunggiBySigungu/" + title + "/" + term, sigungucode);

        return getStatUseareaType_BySigungu(sigungucode, "UA01", term, model);
    }

    /**
     * 인천 월별 매매가 통계 (삭졔해야됨)
     * @param term
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/incheon/{term}")
    public String getStatTradeList_Incheon(@PathVariable String term, Model model) {
        log.info("/stat_trade/incheon/" + term);
        apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_trade/incheon/" + term, "28");
        return getStatTradeList_Area("28", term, model);
    }

    /**
     * 삭제해야됨
     * @param sigungucode
     * @param term
     * @param model
     * @return
     */
    @GetMapping("/stat_trade/incheonBySigungu/{sigungucode}/{term}")
    public String getStatTradeList_IncheonBySigungu(@PathVariable String sigungucode,
                                                    @PathVariable String term,
                                                    Model model) {
        String title = "-";
        title = codeInfoService.getCodeName(sigungucode);
        log.info("/stat_trade/incheonBySigungu/" + sigungucode + "/" + term);
        apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_trade/incheonBySigungu/" + title + "/" + term, sigungucode);
        return getStatUseareaType_BySigungu(sigungucode, "UA01", term, model);
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
