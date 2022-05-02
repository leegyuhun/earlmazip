package aptdata.earlmazip.controller;

import aptdata.earlmazip.service.ApiCallStatService;
import aptdata.earlmazip.service.SiteInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final SiteInfoService siteInfoService;
    private final ApiCallStatService apiCallStatService;

    @RequestMapping("/")
    public String home(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        apiCallStatService.writeApiCallStat("MENU", "Home");
        modal.addAttribute("udt", udt);
        return "home";
    }

    @RequestMapping("/home_lease")
    public String home_lease(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        apiCallStatService.writeApiCallStat("MENU", "Lease");
        modal.addAttribute("udt", udt);
        return "home_lease";
    }

    @RequestMapping("/home_etc")
    public String home_etc(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        apiCallStatService.writeApiCallStat("MENU", "ETC");
        modal.addAttribute("udt", udt);
        return "home_etc";
    }

    @RequestMapping("/home_theme")
    public String home_theme(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        apiCallStatService.writeApiCallStat("MENU", "THEME");
        modal.addAttribute("udt", udt);
        return "home_theme";
    }
}
