package com.earlmazip.controller;

import com.earlmazip.controller.dto.RankUaSigunguResponseDto;
import com.earlmazip.domain.SigunguCode;
import com.earlmazip.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@ApiIgnore
@Controller
@Slf4j
@RequiredArgsConstructor
public class StatLeaseRankUaTypeController {

    private final StatService statService;
    private final ApiCallStatService apiCallStatService;
    private final CodeInfoService codeInfoService;
    private final SiteInfoService siteInfoService;
    private final RequestService requestService;
    private final IpCountService ipCountService;
    private final IpBlockService ipBlockService;
    private final IpInfoController ipInfoController;

    @RequestMapping("/stat_rank_uatype/lease/home")
    public String home_statRankUaType(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        apiCallStatService.writeApiCallStatDetail("/stat_rank_uatype/lease/home", "0", "0");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "평균전세가 TOP 20");
        return "stat_rank_uatype/lease/home";
    }

    @GetMapping("/stat_rank_uatype/lease")
    public String getStatRankUatype(@RequestParam(value = "rankGubn", defaultValue = "0") int rankGubn,
                                    @RequestParam(value = "dealYear", defaultValue = "2023") int dealYear,
                                    @RequestParam(value = "sigunguCode", defaultValue = "11") String sigunguCode,
                                    @RequestParam(value = "uaType", defaultValue = "UA01") String uaType,
                                    HttpServletRequest request,
                                    Model model) throws IOException {
        String clientIP = requestService.getClientIPAddress(request);
//        ipInfoController.MergeIpInformation(clientIP);
        System.out.println("clientIP = " + clientIP);
        if (!ipBlockService.IsBlockIP(clientIP)){
            return "error";
        }
        if (!ipInfoController.isIPCountryKOR(clientIP)) {
            return "error";
        }
        ipCountService.ipCounting(clientIP);

        String title = "-";
        List<RankUaSigunguResponseDto> list;
        if (sigunguCode.length() == 5 || sigunguCode.length() == 2) {
            title = codeInfoService.getCodeName(sigunguCode);

            String url = "/stat_rank_uatype/lease?rankGubn" + rankGubn + "&dealYear=" + dealYear + "&sigunguCode=" + sigunguCode + "&uaType=" + uaType;
            log.info("[" + clientIP + "] " + url);
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("STAT_RANK_UA", "/stat_rank_uatype/lease?rankGubn=" + rankGubn + "&dealYear=" + dealYear + "&sigunguCode=" + title, sigunguCode);
            list = statService.getStatRankLeaseUaTypeList(rankGubn, dealYear, sigunguCode, uaType);
        } else {
            apiCallStatService.writeApiCallStat("ERROR", "(error) /stat_rank_uatype/lease?sigunguCode=" + sigunguCode, sigunguCode);
            return "error";
        }

        if (list.size() > 0) {
            if (rankGubn == 0) {
                title = dealYear + " " + title + " 평균전세가 TOP 20";
            } else if (rankGubn == 1) {
                title = dealYear + " " + title + " 전세건수 TOP 20";
            }
        }
        String areaCode = sigunguCode.substring(0, 2);
        String subTitle = "* " + codeInfoService.getCodeName(uaType);
        List<SigunguCode> sigunguList = codeInfoService.getSigunguList(areaCode);

        model.addAttribute("sigunguList", sigunguList);
        model.addAttribute("list", list);
        model.addAttribute("title", "[ " + title + " ]");
        model.addAttribute("headerTitle", title);
        model.addAttribute("subtitle", subTitle);
        model.addAttribute("uaType", uaType);
        model.addAttribute("rankGubn", rankGubn);
        model.addAttribute("dealYear", dealYear);
        model.addAttribute("sigunguCode", sigunguCode);
        if (areaCode.equals("11")) {
            return "stat_rank_uatype/lease/seoul";
        } else if (areaCode.equals("41")) {
            return "stat_rank_uatype/lease/gyunggi";
        } else if (areaCode.equals("28") || areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("29") || areaCode.equals("30") || areaCode.equals("31")) {
            return "stat_rank_uatype/lease/guSelect";
        } else {
            return "stat_rank_uatype/lease/regionSelect";
        }
    }
}
