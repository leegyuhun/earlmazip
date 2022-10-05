package aptdata.earlmazip.controller;

import aptdata.earlmazip.domain.ApiCallStat;
import aptdata.earlmazip.repository.ApiStatisticsRepository;
import aptdata.earlmazip.service.ApiCallStatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ApiCallStatController {

    private final ApiCallStatService apiCallStatService;

    @GetMapping("/admin/apicallstat/{gubn}")
    public String LoadTodayApiCallList(@PathVariable String gubn, Model model) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        List<ApiCallStat> apiCalls;
        if (gubn.equals("TOTAL")) {
            apiCalls = apiCallStatService.findAllToday(date);
        } else {
            apiCalls = apiCallStatService.findGubnToday(date, gubn);
        }

        List<String> names = apiCalls.stream().map(o->new String(o.getApiName())).collect(Collectors.toList());
        List<Integer> cnts = apiCalls.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

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
}
