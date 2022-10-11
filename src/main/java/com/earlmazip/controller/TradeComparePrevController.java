package com.earlmazip.controller;

import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.service.ApiCallStatService;
import com.earlmazip.service.CodeInfoService;
import com.earlmazip.service.TradeService;
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
public class TradeComparePrevController {
    private final TradeService tradeService;
    private final ApiCallStatService apiCallStatService;
    private final CodeInfoService codeInfoService;


    /**
     * @param sigunguCode
     * @param uaType
     * @param model
     * @return
     */
    @GetMapping("/tradelist/comparePrev")
    public String getTradeList(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                               @RequestParam(value = "type", defaultValue = "0") String type,
                               @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                               Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (sigunguCode.length() == 5) {
            title = codeInfoService.getCodeName(sigunguCode);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/comparePrev?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                TradeSearchCond cond = new TradeSearchCond();
                cond.setSigunguCode(sigunguCode);
                if (uaType.equals("UA01")) {
                    cond.setUaType("");
                } else {
                    cond.setUaType(uaType);
                }
                trads = tradeService.findTradeComparePrevList(cond, type);
//                trads = tradeService.getTradeComparePrevList_SigunguUAType(sigunguCode, type, uaType);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            trads = new ArrayList<>();
        }

        model.addAttribute("list", trads);
        model.addAttribute("sigungucode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("type", type);
        if (type.equals("0")) {
            model.addAttribute("title",  "[ "+ title + " 2022 상승거래 ]");
        } else {
            model.addAttribute("title",  "[ "+ title + " 2022 하락거래 ]");
        }
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        if (sigunguCode.substring(0, 2).equals("11")) {
            return "tradelist/comparePrev/seoul";
        } else if (sigunguCode.substring(0, 2).equals("41")) {
            return "tradelist/comparePrev/gyunggi";
        } else {
            return "tradelist/comparePrev/incheon";
        }
    }
}
