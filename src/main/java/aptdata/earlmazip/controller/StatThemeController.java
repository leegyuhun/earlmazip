package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.EcosDataResponseDto;
import aptdata.earlmazip.controller.dto.StatResponseDto;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StatThemeController {

    private final StatService statService;

    private final ApiCallStatService apiCallStatService;

    private final CodeInfoService codeInfoService;

    private final EcosDataService ecosDataService;

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
}