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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TradeController {
    private final TradeService tradeService;
    private final ApiCallStatService apiCallStatService;
    private final CodeInfoService codeInfoService;


    /**
     * gubn 0: = ua, 1: < ua, 2: > ua / ua = 0 전체면적
     * @param sigungucode
     * @param gubn
     * @param ua
     * @param model
     * @return
     */
    @GetMapping("/tradelist/{sigungucode}/{gubn}/{ua}")
    public String getTradeList_Seoul(@PathVariable String sigungucode,
                                     @PathVariable int gubn,
                                     @PathVariable int ua,
                                     Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (sigungucode.length() == 5) {
            title = codeInfoService.getCodeName(sigungucode);
            log.info("/tradelist/seoul/" + sigungucode);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/" + title + "/" + gubn + "/" + ua);
            if (StringUtils.hasText(sigungucode)) {
                trads = tradeService.getTradeList_Sigungu(sigungucode, gubn, ua);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            trads = new ArrayList<>();
        }

        model.addAttribute("list", trads);
        model.addAttribute("sigungucode", sigungucode);
        model.addAttribute("gubn", gubn);
        model.addAttribute("ua", ua);
        model.addAttribute("title",  "[ "+ title + " ]");
        if (sigungucode.substring(0, 2).equals("11")) {
            return "tradelist/seoul";
        } else if (sigungucode.substring(0, 2).equals("41")) {
            return "tradelist/gyunggi";
        } else {
            return "tradelist/incheon";
        }

    }

    @GetMapping("/tradelist/seoul/{sigungucode}") // 추후 삭제
    public String getTradeList_SeoulBak(@PathVariable String sigungucode, Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (!sigungucode.equals("0")) {
            title = codeInfoService.getCodeName(sigungucode);
            log.info("/tradelist/seoul/" + sigungucode);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/seoul/" + title);
            if (StringUtils.hasText(sigungucode)) {
                trads = tradeService.getTradeList_Sigungu(sigungucode,0,0);
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

    @GetMapping("/tradelist/gyunggi/{sidocode}/{gubn}/{ua}")
    public String getTradeList_Gyunggi(@PathVariable String sidocode,
                                       @PathVariable int gubn,
                                       @PathVariable int ua,
                                       Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (!sidocode.equals("0")) {
            title = codeInfoService.getCodeName(sidocode);
            log.info("/tradelist/gyunggi/" + sidocode);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/gyunggi/" + "/" + gubn + "/" + ua);
            if (StringUtils.hasText(sidocode)) {
                trads = tradeService.getTradeList_GyunggiSido(sidocode, gubn, ua);
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
    
    @GetMapping("/tradelist/gyunggi/{sidocode}") //추후삭제
    public String getTradeList_GyunggiBak(@PathVariable String sidocode, Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (!sidocode.equals("0")) {
            title = codeInfoService.getCodeName(sidocode);
            log.info("/tradelist/gyunggi/" + sidocode);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/gyunggi/" + title);
            if (StringUtils.hasText(sidocode)) {
                trads = tradeService.getTradeList_GyunggiSido(sidocode, 0, 0);
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
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/incheon/" + title);

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
            apiCallStatService.writeApiCallStat("TRADE_CANCEL", "/tradelist/cancelDeal/" + title);

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
            apiCallStatService.writeApiCallStat("TRADE_LIST_NAME", "/tradelist/ByName/" + regncode + "/" + aptName + "/" + ua + "/" + term);

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
            title = trads.get(0).getLandDong() + " " + aptName + "(" + trads.get(0).getBuildYear() + ")";
        }

        model.addAttribute("title",  title);
        model.addAttribute("regncode", regncode);
        model.addAttribute("aptName", aptName);
        model.addAttribute("ua", ua);
        model.addAttribute("dates", dates);
        model.addAttribute("dealAmts", dealAmts);
        model.addAttribute("termStr", Common.makeTermString(term));
        model.addAttribute("list", trads);

        return "tradelist/aptTradeList_ByUA";
    }
}
