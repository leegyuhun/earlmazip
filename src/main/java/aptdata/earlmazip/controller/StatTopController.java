package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.AptPriceResponseDto;
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
public class StatTopController {

    private final StatService statService;

    private final ApiCallStatService apiCallStatService;

    private final CodeInfoService codeInfoService;

    private final EcosDataService ecosDataService;

    @GetMapping("/stat_trade/top")
    public String GetStatTradeTopByYear(@RequestParam(value = "year", defaultValue = "2022") String year,
                                        @RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                        @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                        Model model) {
        List<AptPriceResponseDto> tops;
        String title = "-";
        if (!sigunguCode.equals("0")) {
            title = codeInfoService.getCodeName(sigunguCode);
            log.info("/stat_trade/top?year=" + year + "&sigunguCode=" +  sigunguCode);
            apiCallStatService.writeApiCallStat("STAT_TOP", "/stat_trade/top?sigunguCode=" +  title + "year="+year+"&uaType=" + uaType, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                tops = statService.getStatTradeTopByYear(year, sigunguCode, uaType);
            } else {
                tops = new ArrayList<>();
            }
        } else {
            tops = new ArrayList<>();
        }

        model.addAttribute("title",  title);
        model.addAttribute("list", tops);
        model.addAttribute("uaType", uaType);
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("year", year);
        model.addAttribute("sigunguCode", sigunguCode);

        if (sigunguCode.substring(0, 2).equals("41")) {
            return "stat_trade/gyunggiTop";
        } else if (sigunguCode.substring(0, 2).equals("28")) {
            return "stat_trade/incheonTop";
        } else {
            return "stat_trade/seoulTop";
        }
    }

    //삭제해야됨
    @GetMapping("/stat_trade/seoul/top/{year}/{sigungucode}")
    public String getStatTradeTopSeoulByYear(@PathVariable String year,
                                             @PathVariable String sigungucode,
                                             @RequestParam(value="ua", defaultValue = "UA01") String ua,
                                             Model model) {
        return GetStatTradeTopByYear(year, sigungucode, ua, model);
    }
    //삭제해야됨
    @GetMapping("/stat_trade/gyunggi/top/{year}/{sigungucode}")
    public String gyunggiTopList(@PathVariable String year,
                                 @PathVariable String sigungucode,
                                 @RequestParam(value="ua", defaultValue = "UA01") String ua,
                                 Model model) {
        return GetStatTradeTopByYear(year, sigungucode, ua, model);
    }
    //삭제해야됨
    @GetMapping("/stat_trade/incheon/top/{year}/{sigungucode}")
    public String incheonTopList(@PathVariable String year,
                                 @PathVariable String sigungucode,
                                 @RequestParam(value="ua", defaultValue = "UA01") String ua,
                                 Model model) {
        return GetStatTradeTopByYear(year, sigungucode, ua, model);
    }
}