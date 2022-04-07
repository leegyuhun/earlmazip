package aptdata.earlmazip.controller;

import aptdata.earlmazip.controller.dto.AptResponseDto;
import aptdata.earlmazip.domain.AptInfo;
import aptdata.earlmazip.repository.AptSearch;
import aptdata.earlmazip.service.ApiCallStatService;
import aptdata.earlmazip.service.AptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AptController {

    private final AptService aptService;
    private final ApiCallStatService apiCallStatService;

    @GetMapping("/apts")
    public String list(@ModelAttribute("aptSearch") AptSearch aptSearch, Model model) {
        log.info("aptSearh: " + aptSearch.getAptName());
        apiCallStatService.writeApiCallStat("APT_SEARCH", aptSearch.getAptName());
        List<AptResponseDto> apts;
        if(aptSearch.getAptName() == null || aptSearch.getAptName().equals("")){
            apts = new ArrayList<>();
        } else{
            apts = aptService.findAllByName(aptSearch);
        }
        model.addAttribute("apts", apts);
        return "apts/aptList";
    }
}