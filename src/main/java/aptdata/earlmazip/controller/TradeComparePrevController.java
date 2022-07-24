package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.AptPriceResponseDto;
import aptdata.earlmazip.service.ApiCallStatService;
import aptdata.earlmazip.service.CodeInfoService;
import aptdata.earlmazip.service.TradeService;
import aptdata.earlmazip.utils.Common;
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
            log.info("/tradelist?" + sigunguCode);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/comparePrev?sigunguCode=" + title + "&type=" + type +"&uaType=" + uaType, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                trads = tradeService.getTradeComparePrevList_SigunguUAType(sigunguCode, type, uaType);
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
            model.addAttribute("title",  "[ "+ title + " 상승거래 ]");
        } else {
            model.addAttribute("title",  "[ "+ title + " 하락거래 ]");
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
