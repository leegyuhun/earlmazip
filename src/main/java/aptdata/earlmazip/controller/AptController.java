package aptdata.earlmazip.controller;

import aptdata.earlmazip.domain.aptInfo;
import aptdata.earlmazip.repository.AptSearch;
import aptdata.earlmazip.service.AptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AptController {

    private final AptService aptService;

    @GetMapping("/apts")
    public String list(@ModelAttribute("aptSearch") AptSearch aptSearch, Model model) {
        log.info("aptSearh: " + aptSearch.getAptName());
        List<aptInfo> apts = aptService.findAllByName(aptSearch);
        model.addAttribute("apts", apts);
        return "apts/aptList";
    }
}
