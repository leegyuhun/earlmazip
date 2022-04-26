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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        String title = "-";
        if (!sigungucode.equals("0")) {
            title = codeInfoService.getCodeName(sigungucode);
            log.info("/tradelist/seoul/" + sigungucode);
            apiCallStatService.writeApiCallStat("TRADE", "/tradelist/seoul/" + title);
            if (StringUtils.hasText(sigungucode)) {
                trads = tradeService.getTradeList_SeoulSigungu(sigungucode);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            trads = new ArrayList<>();
        }

        model.addAttribute("list", trads);
        model.addAttribute("title",  "[ "+ title + " ]");

        return "tradelist/seoul";
    }

    @GetMapping("/tradelist/gyunggi/{sidocode}")
    public String getTradeList_Gyunggi(@PathVariable String sidocode, Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (!sidocode.equals("0")) {
            title = codeInfoService.getCodeName(sidocode);
            log.info("/tradelist/gyunggi/" + sidocode);
            apiCallStatService.writeApiCallStat("TRADE", "/tradelist/gyunggi/" + title);
            if (StringUtils.hasText(sidocode)) {
                trads = tradeService.getTradeList_GyunggiSido(sidocode);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            trads = new ArrayList<>();
        }

        model.addAttribute("list", trads);
        model.addAttribute("title",  "[ "+ title + " ]");

        return "tradelist/gyunggi";
    }

    @GetMapping("/tradelist/incheon/{sigungucode}")
    public String getTradeList_Incheon(@PathVariable String sigungucode, Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (!sigungucode.equals("0")) {
            log.info("/tradelist/incheon/" + sigungucode);
            title = codeInfoService.getCodeName(sigungucode);
            apiCallStatService.writeApiCallStat("TRADE", "/tradelist/incheon/" + title);

            if (StringUtils.hasText(sigungucode)) {
                trads = tradeService.getTradeList_Incheon(sigungucode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("list", trads);

        return "tradelist/incheon";
    }

    @GetMapping("/tradelist/cancelDeal/{regncode}")
    public String getCancelDealList(@PathVariable String regncode, Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (!regncode.equals("0")) {
            log.info("/tradelist/cancelDeal/" + regncode);
            title = codeInfoService.getCodeName(regncode);
            apiCallStatService.writeApiCallStat("TRADE", "/tradelist/cancelDeal/" + title);

            if (StringUtils.hasText(regncode)) {
                trads = tradeService.getCancelDealList(regncode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("list", trads);

        return "tradelist/cancelDeal";
    }

    @GetMapping("/tradelist/ByName/{regncode}/{aptName}/{ua}/{term}")
    public String getTradeListByName(@PathVariable String regncode,
                                    @PathVariable String aptName,
                                    @PathVariable int ua,
                                    @PathVariable int term,
                                    Model model) {
        List<AptPriceResponseDto> trads;
        if (!regncode.equals("0")) {
            log.info("/tradelist/ByName/" + regncode + "/" + aptName + "/" + ua + "/" + term);
            apiCallStatService.writeApiCallStat("TRADE", "/tradelist/ByName/" + regncode + "/" + aptName + "/" + ua + "/" + term);

            if (StringUtils.hasText(regncode)) {
                trads = tradeService.getAptTradeList_ByName(regncode, aptName, ua, term);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        List<String> dates = trads.stream().map(o->new String(o.getDealDate())).collect(Collectors.toList());
        List<Integer> dealAmts = trads.stream().map(o->new Integer(o.getDealAmt())).collect(Collectors.toList());
        Collections.reverse(dates);
        Collections.reverse(dealAmts);

        String title = "-";
        if (trads.size() > 0) {
            title = trads.get(0).getLandDong() + " " + aptName;
        }

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("dates", dates);
        model.addAttribute("dealAmts", dealAmts);
        model.addAttribute("list", trads);

        return "tradelist/aptTradeList_ByUA";
    }
}
