package aptdata.earlmazip.controller;

import aptdata.earlmazip.domain.ApiCallStat;
import aptdata.earlmazip.service.ApiCallStatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ApiCallStatController {

    private final ApiCallStatService apiCallStatService;

    @GetMapping("/admin/apicallstat")
    public String LoadTodayApiCallList(Model model) {
        List<ApiCallStat> apiCalls;

        apiCalls = apiCallStatService.LoadTodayApiCallList();

        List<String> names = apiCalls.stream().map(o->new String(o.getApiName())).collect(Collectors.toList());
        List<Integer> cnts = apiCalls.stream().map(o->new Integer(o.getCnt())).collect(Collectors.toList());

        model.addAttribute("list", apiCalls);
        model.addAttribute("names", names);
        model.addAttribute("cnts", cnts);

        return "admin/apicallstat";
    }
}
