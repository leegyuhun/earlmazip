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

    /** 최근 매매내역 100
     * @param sigunguCode
     * @param uaType
     * @param model
     * @return
     */
    @GetMapping("/tradelist")
    public String getTradeList(@RequestParam(value = "sigunguCode", defaultValue = "0") String sigunguCode,
                                     @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                     Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (sigunguCode.length() == 5) {
            title = codeInfoService.getCodeName(sigunguCode);
            log.info("/tradelist?" + sigunguCode);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                trads = tradeService.getTradeList_SigunguUAType(sigunguCode, uaType);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            trads = new ArrayList<>();
        }

        model.addAttribute("list", trads);
        model.addAttribute("sigungucode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("headerTitle", title + " 최근 매매");
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        if (sigunguCode.substring(0, 2).equals("11")) {
            return "tradelist/seoul";
        } else if (sigunguCode.substring(0, 2).equals("41")) {
            return "tradelist/gyunggi";
        } else {
            return "tradelist/incheon";
        }
    }

    /**
     * 거래취소건 조회
     * @param sigunguCode
     * @param model
     * @return
     */
    @GetMapping("/tradelist/cancelDeal")
    public String getCancelDealList(@RequestParam(value = "sigunguCode", defaultValue = "0") String sigunguCode,
                                    Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (!sigunguCode.equals("0")) {
            log.info("/tradelist/cancelDeal?sigunguCode=" + sigunguCode);
            title = codeInfoService.getCodeName(sigunguCode);
            apiCallStatService.writeApiCallStat("TRADE_CANCEL", "/tradelist/cancelDeal?sigunguCode=" + title, sigunguCode);

            if (StringUtils.hasText(sigunguCode)) {
                trads = tradeService.getCancelDealList(sigunguCode);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("headerTitle", title + " 취소거래");
        model.addAttribute("list", trads);

        return "tradelist/cancelDeal";
    }

    /**
     * 신고가내역 조회
     * @param sigunguCode
     * @param uaType
     * @param model
     * @return
     */
    @GetMapping("/tradelist/newHighest")
    public String getNewHighestList(@RequestParam(value="sigunguCode", defaultValue = "11") String sigunguCode,
                                    @RequestParam(value="uaType", defaultValue = "UA01") String uaType,
                                    Model model) {
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (sigunguCode.length() == 5) {
            title = codeInfoService.getCodeName(sigunguCode);
            log.info("/tradelist/newHighest?sigunguCode" + sigunguCode);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/newHighest?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                trads = tradeService.getNewHighestList(sigunguCode, uaType);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            trads = new ArrayList<>();
        }

        model.addAttribute("list", trads);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("uaType", uaType);
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        model.addAttribute("title",  "[ "+ title + " ]");
        model.addAttribute("headerTitle", title + " 2022 신고가내역");
        if (sigunguCode.substring(0, 2).equals("11")) {
            return "tradelist/newHighest/seoul";
        } else if (sigunguCode.substring(0, 2).equals("41")) {
            return "tradelist/newHighest/gyunggi";
        } else {
            return "tradelist/newHighest/incheon";
        }
    }

    /**
     * 아파트 실거래내역 조회
     * @param sigunguCode
     * @param aptName
     * @param ua
     * @param term
     * @param landDong
     * @param model
     * @return
     */
    @GetMapping("/tradelist/ByName")
    public String getTradeListByName(@RequestParam(value="sigunguCode", defaultValue = "") String sigunguCode,
                                     @RequestParam(value="aptName", defaultValue = "") String aptName,
                                     @RequestParam(value="ua", defaultValue = "0") int ua,
                                     @RequestParam(value="term", defaultValue = "1") int term,
                                     @RequestParam(value="landDong", defaultValue = "") String landDong,
                                     Model model) {
        List<AptPriceResponseDto> trads;
        if (!sigunguCode.equals("0")) {
            apiCallStatService.writeApiCallStat("TRADE_LIST_NAME", "/tradelist/ByName?sigunguCode=" + sigunguCode + "&aptName=" + aptName, sigunguCode);

            if (StringUtils.hasText(sigunguCode)) {
                trads = tradeService.getAptTradeList_ByName(sigunguCode, landDong, aptName, ua, term);
            } else {
                trads = new ArrayList<>();
            }
        } else {
            trads = new ArrayList<>();
        }

        List<String> dates = trads.stream().map(o->new String(o.getDealDate())).collect(Collectors.toList());
        List<Float> dealAmts = trads.stream().map(o->new Float((float)o.getDealAmt()/10000)).collect(Collectors.toList());
        Collections.reverse(dates);
        Collections.reverse(dealAmts);

        String title = "-";
        if (trads.size() > 0) {
            title = trads.get(0).getLandDong() + " " + aptName + "(" + trads.get(0).getBuildYear() + ")";
        }

        model.addAttribute("title",  title);
        model.addAttribute("headerTitle", title + " 매매내역");
        model.addAttribute("landDong", landDong);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("aptName", aptName);
        model.addAttribute("ua", ua);
        model.addAttribute("dates", dates);
        model.addAttribute("dealAmts", dealAmts);
        model.addAttribute("termStr", Common.makeTermString(term));
        model.addAttribute("list", trads);

        return "tradelist/ByName/aptTradeList_ByUA";
    }

    @GetMapping("/newHighestList/{sigungucode}/{ua}") //추후 삭제
    public String getNewHighestListBak(@PathVariable String sigungucode,
                                     @PathVariable int ua,
                                     Model model) {
        String title = "-";
        String uaType = "UA01";
        if (ua == 84) {
            uaType = "UA07";
        } else if (ua==59) {
            uaType = "UA03";
        }
        return getNewHighestList(sigungucode, uaType, model);
    }

    @GetMapping("/tradelist/{sigungucode}/{gubn}/{ua}") //추후 삭제
    public String getTradeList_Seoul(@PathVariable String sigungucode,
                                     @PathVariable int gubn,
                                     @PathVariable int ua,
                                     Model model) {
        String uaType = "UA01";
        if (ua == 84) {
            uaType = "UA07";
        } else {
            uaType = "UA08";
        }
        return getTradeList(sigungucode, uaType, model);
    }

    @GetMapping("/tradelist/seoul/{sigungucode}") // 추후 삭제
    public String getTradeList_SeoulBak(@PathVariable String sigungucode, Model model) {
        return getTradeList(sigungucode, "UA01", model);
    }

    @GetMapping("/tradelist/gyunggi/{sidocode}") //추후삭제
    public String getTradeList_GyunggiBak(@PathVariable String sidocode, Model model) {
        return getTradeList(sidocode, "UA01", model);
    }

    @GetMapping("/tradelist/incheon/{sigungucode}") //추후 삭제
    public String getTradeList_Incheon(@PathVariable String sigungucode, Model model) {
        return getTradeList(sigungucode, "UA01", model);
    }

    @GetMapping("/tradelist/cancelDeal/{regncode}") //추후 삭제
    public String getCancelDealListBak(@PathVariable String regncode, Model model) {
        return getCancelDealList(regncode, model);
    }

    @GetMapping("/tradelist/gyunggi/{sidocode}/{gubn}/{ua}") //추후 삭제
    public String getTradeList_Gyunggi(@PathVariable String sidocode,
                                       @PathVariable int gubn,
                                       @PathVariable int ua,
                                       Model model) {
        String uaType = "UA01";
        if (ua == 84) {
            uaType = "UA07";
        } else {
            uaType = "UA08";
        }
        return getTradeList(sidocode, uaType, model);
    }
}
