package com.earlmazip.controller;

import com.earlmazip.domain.ApiCallStat;
import com.earlmazip.domain.ApiCallStatDetail;
import com.earlmazip.domain.IpBlock;
import com.earlmazip.domain.IpCount;
import com.earlmazip.service.ApiCallStatService;
import com.earlmazip.service.IpBlockService;
import com.earlmazip.service.IpCountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApiIgnore
@Controller
@Slf4j
@RequiredArgsConstructor
public class IpBlockController {

    private final IpCountService ipCountService;

    private final IpBlockService ipBlockService;

    @GetMapping("/admin/ipCount")
    public String GetIPHistory(Model model) {
        List<IpCount> items = ipCountService.GetIpHistory();

        List<String> names = items.stream().map(o -> new String(o.getIpAddress())).collect(Collectors.toList());
        List<Integer> cnts = items.stream().map(o -> new Integer(o.getCnt())).collect(Collectors.toList());

        if (cnts.size() > 0) {
            IpCount sum = new IpCount();
            sum.setIpAddress("SUM");
            sum.setCnt(cnts.stream().mapToInt(Integer::intValue).sum());
            items.add(0, sum);
        }
        model.addAttribute("list", items);

        return "admin/ipHistory";
    }

    @GetMapping("/admin/ipBlock")
    public String GetIPBlock(@RequestParam(value = "ipAddress", defaultValue = "") String ipAddress,
                             Model model) {
//        model.addAttribute("ipAddress", ipAddress);
        IpBlock ipBlock = new IpBlock();
        ipBlock.setIpAddress(ipAddress);
        model.addAttribute("ipBlock", ipBlock);
        return "admin/ipBlock";
    }

    @PostMapping("/admin/ipBlock/new")
    public String AddIPBlock(@Valid IpBlock ipBlock, BindingResult result) {

        if (ipBlockService.AddBlockIP(ipBlock.getIpAddress())) {
            return "admin/ipHistory";
        } else {
            return "admin/ipBlock";
        }
    }

    @GetMapping("/admin/ipBlockHistory")
    public String GetIPBlockHistory(Model model) {
        List<IpBlock> list = ipBlockService.GetIPBlock();
        model.addAttribute("list", list);
        return "admin/ipBlockHistory";
    }
}
