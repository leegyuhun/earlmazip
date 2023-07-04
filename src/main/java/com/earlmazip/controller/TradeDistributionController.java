package com.earlmazip.controller;

import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiIgnore
@Controller
@Slf4j
@RequiredArgsConstructor
public class TradeDistributionController {
    private final TradeService tradeService;
    private final ApiCallStatService apiCallStatService;
    private final CodeInfoService codeInfoService;
    private final RequestService requestService;
    private final IpCountService ipCountService;
    private final IpBlockService ipBlockService;
    private final IpInfoController ipInfoController;


    /**
     * @param sigunguCode
     * @param model
     * @return
     */
    @GetMapping("/tradelist/distribution")
    public String getTradeListDistribution(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                           @RequestParam(value = "dealYear", defaultValue = "2023") String dealYear,
                                           HttpServletRequest request,
                               Model model) throws IOException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        if (!ipInfoController.isIPCountryKOR(clientIP)) {
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        List<AptPriceResponseDto> trads;
        String title = "-";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        if (sigunguCode.length() == 5) {
            title = codeInfoService.getCodeName(sigunguCode);
            String url = "/tradelist/distribution?sigunguCode=" + sigunguCode + "&dealYear=" + dealYear;
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/distribution?sigunguCode=" + title, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                trads = tradeService.getTradeDistribution_BySigungu(dealYear, sigunguCode);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            trads = new ArrayList<>();
        }

        model.addAttribute("list", trads);
        model.addAttribute("sigunguCode", sigunguCode);
        model.addAttribute("dealYear", dealYear);
        model.addAttribute("title",  "[ " + title + " "+dealYear + " 분양권 전매거래 ]");

        return "tradelist/distribution/aptDistributionList";
    }

    @GetMapping("/tradelist/distribution/ByName")
    public String getTradeListDistribution_ByName(@RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                                  @RequestParam(value = "landDong", defaultValue = "") String landDong,
                                                  @RequestParam(value = "aptName", defaultValue = "") String aptName,
                                                  HttpServletRequest request,
                               Model model) throws UnknownHostException {
        String clientIP = requestService.getClientIPAddress(request);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        List<AptPriceResponseDto> trads;
        String title = "-";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        if (sigunguCode.length() == 5) {
            String url = "/tradelist/distribution?sigunguCode=" + sigunguCode + "&aptName=" + aptName + "&landDong=" + landDong;
            log.info("[" + clientIP + "] " + url);
//            title = codeInfoService.getCodeName(sigunguCode);
            log.info("/tradelist?" + sigunguCode);
            apiCallStatService.writeApiCallStat("TRADE_LIST", "/tradelist/distribution/ByName?sigunguCode=" + title + "&aptName=" + aptName, sigunguCode);
            if (StringUtils.hasText(sigunguCode)) {
                trads = tradeService.getTradeDistribution_ByName(date.substring(0,4), sigunguCode, landDong, aptName);
            } else {
                trads = new ArrayList<>();
            }
        } else{
            trads = new ArrayList<>();
        }

        title = aptName;
        model.addAttribute("list", trads);
        model.addAttribute("sigungucode", sigunguCode);
        model.addAttribute("title",  "[ "+ title + " " + date.substring(0,4) + " 분양권 전매거래 ]");

        return "tradelist/distribution/aptDistributionList_ByName";
    }
}
