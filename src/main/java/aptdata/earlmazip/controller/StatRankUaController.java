package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.RankUaSigunguResponseDto;
import aptdata.earlmazip.service.ApiCallStatService;
import aptdata.earlmazip.service.CodeInfoService;
import aptdata.earlmazip.service.EcosDataService;
import aptdata.earlmazip.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StatRankUaController {

    private final StatService statService;

    private final ApiCallStatService apiCallStatService;

    private final CodeInfoService codeInfoService;

    private final EcosDataService ecosDataService;

    /**
     * 서울지역 전용면적별 TOP 랭크 조회
     * rankgubn(0: 평균매매가, 1: 거래건수)
     * ua(현재 59, 84만)
     * @param rankgubn
     * @param sigungucode
     * @param ua
     * @param model
     * @return
     */
    @GetMapping("/stat_rank_ua/seoul/{rankgubn}/{sigungucode}/{ua}")
    public String getStatRankUaList_Seoul(@PathVariable int rankgubn,
                                          @PathVariable String sigungucode,
                                          @PathVariable int ua,Model model) {
        String title = "-";
        List<RankUaSigunguResponseDto> list;
        if (!sigungucode.equals("0")) {
            log.info("/stat_rank_ua/seoul/" + rankgubn + "/" + sigungucode + "/" + ua);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("STAT_RANK_UA", "/stat_rank_ua/seoul/" + rankgubn + "/" + title + "/" + ua, sigungucode);
            list = statService.getStatRankUaList_Seoul(rankgubn, sigungucode, ua);
        } else {
            list = new ArrayList<>();
        }

//        int idx = 1;
//        for (RankUaSigunguResponseDto item: list) {
//            item.setRank(idx);
//            item.setTradeUrl("tradelist/ByName?regncode=" + item.getSigunguCode() + "&aptname=" + item.getAptName() + "&ua="+ua+"&term=1&landDong=" + item.getLandDong());
//            idx++;
//        }

        if (list.size() > 0) {
            if (rankgubn == 0) {
                title = title + " 전용" + ua + " 평균매매가 TOP 20";
            } else if (rankgubn == 1) {
                title = title + " 전용" + ua + " 매매건수 TOP 20";
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int nowInt = Integer.parseInt(date.substring(0,4));
        int prevInt = nowInt-1;

        String subTitle = "( " + prevInt + " - " + nowInt + " )";

        model.addAttribute("list", list);
        model.addAttribute("title", "[ " + title + " ]");
        model.addAttribute("headerTitle", title);
        model.addAttribute("subtitle", subTitle);
        model.addAttribute("ua", ua);
        model.addAttribute("rankgubn", rankgubn);

        if (sigungucode.equals("11680") || sigungucode.equals("11740") || sigungucode.equals("11500") || sigungucode.equals("11620") || sigungucode.equals("11530") ||
                sigungucode.equals("11545") || sigungucode.equals("11590") || sigungucode.equals("11650") || sigungucode.equals("11710") || sigungucode.equals("11470") ||
                sigungucode.equals("11560") || sigungucode.equals("0")) {
            return "stat_rank_ua/seoulSouth";
        } else {
            return "stat_rank_ua/seoulNorth";
        }
    }

    /**
     * 경기지역 전용면적별 TOP 랭크 조회
     * rankgubn(0: 평균매매가, 1: 거래건수)
     * ua(현재 59, 84만)
     * @param rankgubn
     * @param sigungucode
     * @param ua
     * @param model
     * @return
     */
    @GetMapping("/stat_rank_ua/gyunggi/{rankgubn}/{sigungucode}/{ua}")
    public String getStatRankUaList_Gyunggi(@PathVariable int rankgubn,
                                          @PathVariable String sigungucode,
                                          @PathVariable int ua,Model model) {
        String title = "-";
        List<RankUaSigunguResponseDto> list;
        if (!sigungucode.equals("0")) {
            log.info("/stat_rank_ua/gyunggi/" + rankgubn + "/" + sigungucode + "/" + ua);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("STAT_RANK_UA", "/stat_rank_ua/gyunggi/" + rankgubn + "/" + title + "/" + ua, sigungucode);
            list = statService.getStatRankUaList_Seoul(rankgubn, sigungucode, ua);
        } else {
            list = new ArrayList<>();
        }

        int idx = 1;
        for (RankUaSigunguResponseDto item: list) {
            item.setRank(idx);
            item.setTradeUrl("tradelist/ByName?sigunguCode" + item.getSigunguCode() + "&aptName=" + item.getAptName() + "&ua="+ua+"&term=1&landDong=" + item.getLandDong());
            item.setTradeUrl2("tradelist/ByName?sigunguCode" + item.getSigunguCode() + "&aptName=" + item.getAptName() + "&ua="+ua+"&term=3&landDong=" + item.getLandDong());
            idx++;
        }

        if (list.size() > 0) {
            if (rankgubn == 0) {
                title = title + " 전용" + ua + " 평균매매가 TOP 20";
            } else if (rankgubn == 1) {
                title = title + " 전용" + ua + " 매매건수 TOP 20";
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int nowInt = Integer.parseInt(date.substring(0,4));
        int prevInt = nowInt-1;

        String subTitle = "( " + prevInt + " - " + nowInt + " )";

        model.addAttribute("list", list);
        model.addAttribute("title", "[ " + title + " ]");
        model.addAttribute("headerTitle", title);
        model.addAttribute("subtitle", subTitle);
        model.addAttribute("ua", ua);
        model.addAttribute("rankgubn", rankgubn);

        return "stat_rank_ua/gyunggi";
    }

    @GetMapping("/stat_rank_ua/incheon/{rankgubn}/{sigungucode}/{ua}")
    public String getStatRankUaList_Incheon(@PathVariable int rankgubn,
                                            @PathVariable String sigungucode,
                                            @PathVariable int ua,Model model) {
        String title = "-";
        List<RankUaSigunguResponseDto> list;
        if (!sigungucode.equals("0")) {
            log.info("/stat_rank_ua/incheon/" + rankgubn + "/" + sigungucode + "/" + ua);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("STAT_RANK_UA", "/stat_rank_ua/incheon/" + rankgubn + "/" + title + "/" + ua, sigungucode);
            list = statService.getStatRankUaList_Seoul(rankgubn, sigungucode, ua);
        } else {
            list = new ArrayList<>();
        }

        int idx = 1;
        for (RankUaSigunguResponseDto item: list) {
            item.setRank(idx);
            item.setTradeUrl("tradelist/ByName?sigunguCode" + item.getSigunguCode() + "&aptName=" + item.getAptName() + "&ua="+ua+"&term=1&landDong=" + item.getLandDong());
            item.setTradeUrl2("tradelist/ByName?sigunguCode" + item.getSigunguCode() + "&aptName=" + item.getAptName() + "&ua="+ua+"&term=3&landDong=" + item.getLandDong());
            idx++;
        }

        if (list.size() > 0) {
            if (rankgubn == 0) {
                title = title + " 전용" + ua + " 평균매매가 TOP 20";
            } else if (rankgubn == 1) {
                title = title + " 전용" + ua + " 매매건수 TOP 20";
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int nowInt = Integer.parseInt(date.substring(0,4));
        int prevInt = nowInt-1;

        String subTitle = "( " + prevInt + " - " + nowInt + " )";

        model.addAttribute("list", list);
        model.addAttribute("title", "[ " + title + " ]");
        model.addAttribute("headerTitle", title);
        model.addAttribute("subtitle", subTitle);
        model.addAttribute("ua", ua);
        model.addAttribute("rankgubn", rankgubn);

        return "stat_rank_ua/incheon";
    }

    @GetMapping("/stat_rank_uatype")
    public String getStatRankUatype(@RequestParam(value = "rankGubn", defaultValue = "0") int rankGubn,
                                    @RequestParam(value = "dealYear", defaultValue = "2021") int dealYear,
                                    @RequestParam(value = "sigunguCode", defaultValue = "0") String sigunguCode,
                                    @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                    Model model) {
        String title = "-";
        List<RankUaSigunguResponseDto> list;
        if (!sigunguCode.equals("0")) {
            log.info("/stat_rank_uatype?rankGubn=" + rankGubn + "&dealYear=" + dealYear + "&sigunguCode=" + sigunguCode + "&uaType=" + uaType);
            title = codeInfoService.getCodeName(sigunguCode);
            apiCallStatService.writeApiCallStat("STAT_RANK_UA", "/stat_rank_uatype?rankGubn=" + rankGubn + "&dealYear=" + dealYear + "&sigunguCode=" + title, sigunguCode);
            list = statService.getStatRankUaTypeList(rankGubn, dealYear, sigunguCode, uaType);
        } else {
            list = new ArrayList<>();
        }

        if (list.size() > 0) {
            if (rankGubn == 0) {
                title = dealYear + " " + title + " 평균매매가 TOP 20";
            } else if (rankGubn == 1) {
                title = dealYear + " " + title + " 매매건수 TOP 20";
            }
        }

        String subTitle = "* " + codeInfoService.getCodeName(uaType);

        model.addAttribute("list", list);
        model.addAttribute("title", "[ " + title + " ]");
        model.addAttribute("headerTitle", title);
        model.addAttribute("subtitle", subTitle);
        model.addAttribute("uaType", uaType);
        model.addAttribute("rankGubn", rankGubn);
        model.addAttribute("dealYear", dealYear);
        model.addAttribute("sigunguCode", sigunguCode);

        if (sigunguCode.substring(0, 2).equals("11")) {
            return "stat_rank_uatype/seoul";
        } else if (sigunguCode.substring(0, 2).equals("41")) {
            return "stat_rank_uatype/gyunggi";
        } else {
            return "stat_rank_uatype/incheon";
        }
    }
}
