package com.earlmazip.controller;

import com.earlmazip.domain.ApiCallStat;
import com.earlmazip.domain.ApiCallStatDetail;
import com.earlmazip.domain.IpCount;
import com.earlmazip.service.ApiCallStatService;
import com.earlmazip.service.IpCountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ApiCallStatController {

    private final ApiCallStatService apiCallStatService;

    private final IpCountService ipCountService;

    @GetMapping("/admin/apicallstatdetail")
    public String LoadTodayApiCallDetail(@RequestParam(value = "gubn", defaultValue = "") String gubn,
                                         Model model) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        List<ApiCallStatDetail> apiCalls;
        if (gubn.equals("sigunguCode")) {
            apiCalls = apiCallStatService.findTodaySigungu(date);
        } else {
            apiCalls = apiCallStatService.findAllTodayDetail(date);
        }

        List<String> names = apiCalls.stream().map(o -> new String(o.getApiName())).collect(Collectors.toList());
        List<Integer> cnts = apiCalls.stream().map(o -> new Integer(o.getCnt())).collect(Collectors.toList());

        if (cnts.size() > 0) {

            ApiCallStatDetail sum = new ApiCallStatDetail();
            sum.setApiName("SUM");
            sum.setCallDate(date);
            sum.setCnt(cnts.stream().mapToInt(Integer::intValue).sum());
            apiCalls.add(0, sum);
        }

        model.addAttribute("list", apiCalls);
        model.addAttribute("names", names);
        model.addAttribute("cnts", cnts);

        return "admin/apicallstat";
    }

    @GetMapping("/admin/apicallstat/{gubn}")
    public String LoadTodayApiCallList(@PathVariable String gubn, Model model) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        List<ApiCallStat> apiCalls;
        if (gubn.equals("TOTAL")) {
            apiCalls = apiCallStatService.findAllToday(date);
        } else if (gubn.equals("error")) {
            apiCalls = apiCallStatService.findTodayError(date);
        } else {
            apiCalls = apiCallStatService.findGubnToday(date, gubn);
        }

        List<String> names = apiCalls.stream().map(o -> new String(o.getApiName())).collect(Collectors.toList());
        List<Integer> cnts = apiCalls.stream().map(o -> new Integer(o.getCnt())).collect(Collectors.toList());

        if (cnts.size() > 0) {

            ApiCallStat sum = new ApiCallStat();
            sum.setApiName("SUM");
            sum.setCallDate(date);
            sum.setCnt(cnts.stream().mapToInt(Integer::intValue).sum());
            apiCalls.add(0, sum);
        }

        model.addAttribute("list", apiCalls);
        model.addAttribute("names", names);
        model.addAttribute("cnts", cnts);

        return "admin/apicallstat";
    }

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
}
