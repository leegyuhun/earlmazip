package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.AptPriceResponseDto;
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

    @GetMapping("/tradelist/seoul/{sigungucode}")
    public String getTradeList_Seoul(@PathVariable String sigungucode, Model model) {
        log.info("/tradelist/seoul/" + sigungucode);
        List<AptPriceResponseDto> trads;
        if (StringUtils.hasText(sigungucode)) {
            trads = tradeService.getTradeList_SeoulSigungu(sigungucode);
        } else {
            trads = new ArrayList<>();
        }
        model.addAttribute("list", trads);

        return "tradelist/seoul";
    }

    @GetMapping("/tradelist/gyunggi/{sidocode}")
    public String getTradeList_Gyunggi(@PathVariable String sidocode, Model model) {
        log.info("/tradelist/gyunggi/" + sidocode);
        List<AptPriceResponseDto> trads;
        if (StringUtils.hasText(sidocode)) {
            trads = tradeService.getTradeList_GyunggiSido(sidocode);
        } else {
            trads = new ArrayList<>();
        }
        model.addAttribute("list", trads);

        return "tradelist/gyunggi";
    }

    @GetMapping("/tradelist/incheon/{sigungucode}")
    public String getTradeList_Incheon(@PathVariable String sigungucode, Model model) {
        log.info("/tradelist/incheon/" + sigungucode);
        List<AptPriceResponseDto> trads;
        if (StringUtils.hasText(sigungucode)) {
            trads = tradeService.getTradeList_Incheon(sigungucode);
        } else {
            trads = new ArrayList<>();
        }
        model.addAttribute("list", trads);

        return "tradelist/incheon";
    }
}
