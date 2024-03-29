package com.earlmazip.controller;

import com.earlmazip.controller.dto.AptResponseDto;
import com.earlmazip.repository.AptSearch;
import com.earlmazip.service.ApiCallStatService;
import com.earlmazip.service.AptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@ApiIgnore
@Controller
@Slf4j
@RequiredArgsConstructor
public class AptController {

    private final AptService aptService;
    private final ApiCallStatService apiCallStatService;

    @GetMapping("/apts")
    public String list(@ModelAttribute("aptSearch") AptSearch aptSearch, Model model) {
        log.info("aptSearh: " + aptSearch.getAptName());
        List<AptResponseDto> apts;
        if(aptSearch.getAptName() == null || aptSearch.getAptName().equals("")){
            apts = new ArrayList<>();
        } else{
            apiCallStatService.writeApiCallStat("APT_SEARCH", aptSearch.getAptName(), "0");
            apts = aptService.findAllByName(aptSearch);
        }
        for (AptResponseDto apt: apts) {
            apt.setTradeUrl("tradelist/ByName/" + apt.getSigunguCode() + "/" + apt.getAptName() + "/0/1");
        }
        model.addAttribute("headerTitle", "아파트 정보 검색");
        model.addAttribute("apts", apts);
        return "apts/aptList";
    }
}