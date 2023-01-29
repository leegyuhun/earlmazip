package com.earlmazip.controller;

import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.controller.dto.LandDongInfoDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.domain.SigunguCode;
import com.earlmazip.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiIgnore
@Controller
@Slf4j
@RequiredArgsConstructor
public class TradeComparePrevController {
    private final SiteInfoService siteInfoService;
    private final TradeService tradeService;
    private final ApiCallStatService apiCallStatService;
    private final CodeInfoService codeInfoService;
    private final LandDongService landDongService;
    private final RequestService requestService;
    private final IpCountService ipCountService;
    private final IpBlockService ipBlockService;

    @RequestMapping("/tradelist/comparePrev/home")
    public String home_tradelistcomparePrev(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "월별 매매 통계");
        return "tradelist/comparePrev/home";
    }

    /**
     * @param sigunguCode
     * @param uaType
     * @param model
     * @return
     */
    @GetMapping("/tradelist/comparePrev")
    public String getTradeListComparePrev(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                               @RequestParam(value = "type", defaultValue = "0") String type,
                               @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                               @RequestParam(value = "landDong", defaultValue = "") String landDong,
                               HttpServletRequest request,
                               Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        String url = "/tradelist/comparePrev?sigunguCode=" + sigunguCode + "&type=" + type + "&uaType=" + uaType;
        if (type.length() > 1){
            apiCallStatService.writeApiCallStat("ERROR", "(error)" + url, sigunguCode);
            return "error";
        }
        if (uaType.length()>4){
            apiCallStatService.writeApiCallStat("ERROR", "(error)" + url, sigunguCode);
            return "error";
        }
        List<AptPriceResponseDto> trads;
        String title = "-";
        if (sigunguCode.length() == 5) {
            title = codeInfoService.getCodeName(sigunguCode);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/comparePrev?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                TradeSearchCond cond = new TradeSearchCond();
                cond.setSigunguCode(sigunguCode);
                if (uaType.equals("UA01")) {
                    cond.setUaType("");
                } else {
                    cond.setUaType(uaType);
                }
                if (StringUtils.hasText(landDong)){
                    cond.setLandDong(landDong);
                }
                trads = tradeService.getTradeListComparePrev(cond, type);
//                trads = tradeService.getTradeComparePrevList_SigunguUAType(sigunguCode, type, uaType);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            trads = new ArrayList<>();
        }
        String areaCode = sigunguCode.substring(0, 2);

        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);
        List<LandDongInfoDto> dongList = landDongService.getLandDongList_BySigunguCode(sigunguCode);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date());

        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("list", trads);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("dongList", dongList);
        model.addAttribute("landDong", landDong);
        model.addAttribute("uaType", uaType);
        model.addAttribute("type", type);
        if (!landDong.equals("")) {
            title += " " + landDong;
        }
        if (type.equals("0")) {
            model.addAttribute("title",  "[ "+ title + " 상승거래 ]");
        } else {
            model.addAttribute("title",  "[ "+ title + " 하락거래 ]");
        }
        model.addAttribute("uaStr", codeInfoService.getCodeName(uaType));
        if (areaCode.equals("11")) {
            return "tradelist/comparePrev/seoul";
        } else if (areaCode.equals("41")) {
            return "tradelist/comparePrev/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "tradelist/comparePrev/guSelect";
        } else {
            return "tradelist/comparePrev/regionSelect";
        }
    }
}
