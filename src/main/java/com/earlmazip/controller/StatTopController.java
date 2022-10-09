package com.earlmazip.controller;

import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.service.ApiCallStatService;
import com.earlmazip.service.CodeInfoService;
import com.earlmazip.service.EcosDataService;
import com.earlmazip.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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
            apiCallStatService.writeApiCallStat("STAT_TOP", "/stat_trade/top?sigunguCode=" +  title + "year="+year, sigunguCode);
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
            return "stat_trade/top/gyunggiTop";
        } else if (sigunguCode.substring(0, 2).equals("28")) {
            return "stat_trade/top/incheonTop";
        } else {
            return "stat_trade/top/seoulTop";
        }
    }
}