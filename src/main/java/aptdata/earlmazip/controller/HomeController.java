package aptdata.earlmazip.controller;

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

    @RequestMapping("/")
    public String home(Model modal) {
        String udt = siteInfoService.findSiteInfo("TRADELIST_UDT");
        modal.addAttribute("udt", udt);
        return "home";

    }
}
