package com.earlmazip.controller;

import com.earlmazip.service.ApiCallStatService;
import com.earlmazip.service.SiteInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final SiteInfoService siteInfoService;
    private final ApiCallStatService apiCallStatService;

    @RequestMapping("/")
    public String home(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
//        apiCallStatService.writeApiCallStat("MENU", "Home");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "수도권 부동산 - 매매");
        return "home";
    }

    @RequestMapping("/home_lease")
    public String home_lease(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        apiCallStatService.writeApiCallStat("MENU", "Lease", "0");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "수도권 부동산 - 전세");
        return "home_lease";
    }

    @RequestMapping("/home_etc")
    public String home_etc(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        apiCallStatService.writeApiCallStat("MENU", "ETC", "0");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "수도권 부동산 - 기타");
        return "home_etc";
    }

    @RequestMapping("/home_theme")
    public String home_theme(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        apiCallStatService.writeApiCallStat("MENU", "THEME", "0");
        modal.addAttribute("udt", udt);
        modal.addAttribute("headerTitle", "수도권 부동산 - 테마");
        return "home_theme";
    }
}
