package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.AptPriceResponseDto;
import aptdata.earlmazip.service.ApiCallStatService;
import aptdata.earlmazip.service.CodeInfoService;
import aptdata.earlmazip.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TradeController {
    private final TradeService tradeService;
    private final ApiCallStatService apiCallStatService;
    private final CodeInfoService codeInfoService;

    @GetMapping("/tradelist/seoul/{sigungucode}")
    public String getTradeList_Seoul(@PathVariable String sigungucode, Model model) {
        List<AptPriceResponseDto> trads;
        if (!sigungucode.equals("0")) {
            log.info("/tradelist/seoul/" + sigungucode);
            apiCallStatService.writeApiCallStat("TRADE", "/tradelist/seoul/" + sigungucode);
            if (StringUtils.hasText(sigungucode)) {
                trads = tradeService.getTradeList_SeoulSigungu(sigungucode);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            trads = new ArrayList<>();
        }
        String title = codeInfoService.getCodeName(sigungucode);

        model.addAttribute("list", trads);
        model.addAttribute("title",  "[ "+ title + " ]");

        return "tradelist/seoul";
    }

    @GetMapping("/tradelist/gyunggi/{sidocode}")
    public String getTradeList_Gyunggi(@PathVariable String sidocode, Model model) {
        List<AptPriceResponseDto> trads;
        if (!sidocode.equals("0")) {
            log.info("/tradelist/gyunggi/" + sidocode);
            apiCallStatService.writeApiCallStat("TRADE", "/tradelist/gyunggi/" + sidocode);
            if (StringUtils.hasText(sidocode)) {
                trads = tradeService.getTradeList_GyunggiSido(sidocode);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            trads = new ArrayList<>();
        }
        String title = codeInfoService.getCodeName(sidocode);

        model.addAttribute("list", trads);
        model.addAttribute("title",  "[ "+ title + " ]");

        return "tradelist/gyunggi";
    }

    @GetMapping("/tradelist/incheon/{sigungucode}")
    public String getTradeList_Incheon(@PathVariable String sigungucode, Model model) {
        List<AptPriceResponseDto> trads;
        if (!sigungucode.equals("0")) {
            log.info("/tradelist/incheon/" + sigungucode);
            apiCallStatService.writeApiCallStat("TRADE", "/tradelist/incheon/" + sigungucode);

            if (StringUtils.hasText(sigungucode)) {
                trads = tradeService.getTradeList_Incheon(sigungucode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }
        String title = codeInfoService.getCodeName(sigungucode);

        model.addAttribute("list", trads);
        model.addAttribute("title",  "[ "+ title + " ]");

        return "tradelist/incheon";
    }
}
