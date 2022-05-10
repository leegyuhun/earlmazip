package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.*;
import aptdata.earlmazip.service.ApiCallStatService;
import aptdata.earlmazip.service.CodeInfoService;
import aptdata.earlmazip.service.EcosDataService;
import aptdata.earlmazip.service.StatService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StatRankUaController {

    private final StatService statService;

    private final ApiCallStatService apiCallStatService;

    private final CodeInfoService codeInfoService;

    private final EcosDataService ecosDataService;

    /**
     * 서울시 월별 매매가 통계
     * @param
     * @param model
     * @return
     */
    @GetMapping("/stat_rank_ua/seoul/{rankgubn}/{sigungucode}/{ua}")
    public String getStatRankUaList_Seoul(@PathVariable int rankgubn,
                                          @PathVariable String sigungucode,
                                          @PathVariable int ua,Model model) {
        log.info("/stat_rank_ua/seoul/" + rankgubn + "/" + sigungucode + "/" + ua);
        apiCallStatService.writeApiCallStat("STAT_TRADE", "/stat_rank_ua/seoul/" + rankgubn + "/" + sigungucode + "/" + ua);
        List<RankUaSigunguResponseDto> list = statService.getStatRankUaList_Seoul(rankgubn, sigungucode, ua);

        int idx = 1;
        for (RankUaSigunguResponseDto item: list) {
            item.setRank(idx);
            item.setTradeUrl("tradelist/ByName/" + item.getSigunguCode() + "/" + item.getAptName() + "/"+ua+"/1");
            item.setTradeUrl2("tradelist/ByName/" + item.getSigunguCode() + "/" + item.getAptName() + "/"+ua+"/3");
            idx++;
        }
        String title = "-";
        if (list.size() > 0) {
            if (rankgubn == 0) {
                title = list.get(0).getSigunguName() + " 평균매매가 TOP 10";
            } else if (rankgubn == 1) {
                title = list.get(0).getSigunguName() + " 매매건수 TOP 10";
            }
        }

        model.addAttribute("list", list);
        model.addAttribute("title", "[ " + title + " ]");

        return "stat_rank_ua/seoul";
    }
}
