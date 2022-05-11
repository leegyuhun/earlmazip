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

    @GetMapping("/stat_rank_ua/{rankgubn}/{sigungucode}/{ua}")
    public String getStatRankUaList_SeoulBak(@PathVariable int rankgubn,
                                          @PathVariable String sigungucode,
                                          @PathVariable int ua,Model model) {
        String title = "-";
        List<RankUaSigunguResponseDto> list;
        if (!sigungucode.equals("0")) {
            log.info("/stat_rank_ua/" + rankgubn + "/" + sigungucode + "/" + ua);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_rank_ua/" + rankgubn + "/" + title + "/" + ua);
            list = statService.getStatRankUaList_Seoul(rankgubn, sigungucode, ua);
        } else {
            list = new ArrayList<>();
        }

        int idx = 1;
        for (RankUaSigunguResponseDto item: list) {
            item.setRank(idx);
            item.setTradeUrl("tradelist/ByName/" + item.getSigunguCode() + "/" + item.getAptName() + "/"+ua+"/1");
            item.setTradeUrl2("tradelist/ByName/" + item.getSigunguCode() + "/" + item.getAptName() + "/"+ua+"/3");
            idx++;
        }

        if (list.size() > 0) {
            if (rankgubn == 0) {
                title = title + " 평균매매가 TOP 10";
            } else if (rankgubn == 1) {
                title = title + " 매매건수 TOP 10";
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

    @GetMapping("/stat_rank_ua/seoul/{rankgubn}/{sigungucode}/{ua}")
    public String getStatRankUaList_Seoul(@PathVariable int rankgubn,
                                          @PathVariable String sigungucode,
                                          @PathVariable int ua,Model model) {
        String title = "-";
        List<RankUaSigunguResponseDto> list;
        if (!sigungucode.equals("0")) {
            log.info("/stat_rank_ua/seoul/" + rankgubn + "/" + sigungucode + "/" + ua);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_rank_ua/seoul/" + rankgubn + "/" + title + "/" + ua);
            list = statService.getStatRankUaList_Seoul(rankgubn, sigungucode, ua);
        } else {
            list = new ArrayList<>();
        }

        int idx = 1;
        for (RankUaSigunguResponseDto item: list) {
            item.setRank(idx);
            item.setTradeUrl("tradelist/ByName/" + item.getSigunguCode() + "/" + item.getAptName() + "/"+ua+"/1");
            item.setTradeUrl2("tradelist/ByName/" + item.getSigunguCode() + "/" + item.getAptName() + "/"+ua+"/3");
            idx++;
        }

        if (list.size() > 0) {
            if (rankgubn == 0) {
                title = title + " 평균매매가 TOP 10";
            } else if (rankgubn == 1) {
                title = title + " 매매건수 TOP 10";
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

    @GetMapping("/stat_rank_ua/gyunggi/{rankgubn}/{sigungucode}/{ua}")
    public String getStatRankUaList_Gyunggi(@PathVariable int rankgubn,
                                          @PathVariable String sigungucode,
                                          @PathVariable int ua,Model model) {
        String title = "-";
        List<RankUaSigunguResponseDto> list;
        if (!sigungucode.equals("0")) {
            log.info("/stat_rank_ua/gyunggi/" + rankgubn + "/" + sigungucode + "/" + ua);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_rank_ua/gyunggi/" + rankgubn + "/" + title + "/" + ua);
            list = statService.getStatRankUaList_Seoul(rankgubn, sigungucode, ua);
        } else {
            list = new ArrayList<>();
        }

        int idx = 1;
        for (RankUaSigunguResponseDto item: list) {
            item.setRank(idx);
            item.setTradeUrl("tradelist/ByName/" + item.getSigunguCode() + "/" + item.getAptName() + "/"+ua+"/1");
            item.setTradeUrl2("tradelist/ByName/" + item.getSigunguCode() + "/" + item.getAptName() + "/"+ua+"/3");
            idx++;
        }

        if (list.size() > 0) {
            if (rankgubn == 0) {
                title = title + " 평균매매가 TOP 10";
            } else if (rankgubn == 1) {
                title = title + " 매매건수 TOP 10";
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
        model.addAttribute("subtitle", subTitle);
        model.addAttribute("ua", ua);
        model.addAttribute("rankgubn", rankgubn);

        if (sigungucode.equals("41271") || sigungucode.equals("41273") || sigungucode.equals("41171")
            || sigungucode.equals("41173") || sigungucode.equals("41290") || sigungucode.equals("41210")
            || sigungucode.equals("41410") || sigungucode.equals("41190") || sigungucode.equals("41390")
            || sigungucode.equals("41430") || sigungucode.equals("0")) {
            return "stat_rank_ua/gyunggiCenterWest";
        } else if (sigungucode.equals("41111") || sigungucode.equals("41113") || sigungucode.equals("41115")
                || sigungucode.equals("41117") || sigungucode.equals("41550") || sigungucode.equals("41370")
                || sigungucode.equals("41220") || sigungucode.equals("41590") || sigungucode.equals("1")) {
            return "stat_rank_ua/gyunggiWestSouth";
        } else {
            return "stat_rank_ua/gyunggiWestSouth";
        }
    }
}
