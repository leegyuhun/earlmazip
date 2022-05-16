package aptdata.earlmazip.service;

import aptdata.earlmazip.controller.dto.RankUaSigunguResponseDto;
import aptdata.earlmazip.controller.dto.RankYearResponseDto;
import aptdata.earlmazip.controller.dto.StatLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatResponseDto;
import aptdata.earlmazip.domain.RankYear;
import aptdata.earlmazip.domain.StatAreaYYMM;
import aptdata.earlmazip.domain.StatSidoYYMM;
import aptdata.earlmazip.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {

    private final StatRepository statRepository;

    public List<StatResponseDto> getStatTradeList_Area(String areaCode, String term) {
        return statRepository.getStatTradeList(areaCode, term);
    }

    public List<StatResponseDto> getStatTradeList_Seoul(String term) {
        return statRepository.getStatTradeList("11", term);
    }

    public List<StatResponseDto> getStatTradeList_BySigungu(String sigunguCode, String term) {
        return statRepository.getStatTradeList_BySigungu(sigunguCode, term);
    }

    public List<StatResponseDto> findStatSeoulListYear(String year) {
        return statRepository.findSeoulYear(year);
    }

    public List<StatResponseDto> getStatTradeByUseAreaList(String regnCode, String ua, String term) {
        return statRepository.getStatTradeByUseAreaList(regnCode, ua, term);
    }

    public List<RankYearResponseDto> getStatTradeTopSeoulByYear(String year, String sigungucode) {
        return statRepository.getStatTradeTopSeoulByYear(year, sigungucode);
    }

    public List<StatResponseDto> getStatTradeList_Gyunggi(String term) {
        return statRepository.getStatTradeList("41", term);
    }
    public List<StatResponseDto> getStatTradeList_Incheon(String term) {
        return statRepository.getStatTradeList("28", term);
    }

    public List<StatResponseDto> getStatTradeList_ByCity(String sidoCode, String term) {
        return statRepository.getStatTradeList_ByCity(sidoCode, term);
    }

    public List<RankYearResponseDto> findGyunggiTopList(String year, String sidocode) {
        return statRepository.findGyungGiTop(year, sidocode);
    }

    public List<RankYearResponseDto> findIncheonTopList(String year, String sigungucode) {
        return statRepository.findIncheonTop(year, sigungucode);
    }

    public List<StatResponseDto> getStatNewHighestAndTradeCount(String sidoCode) {
        return statRepository.getStatNewHighestAndTradeCount(sidoCode);
    }

    public List<StatResponseDto> getStatTheme(String themeCode, String term) {
        return statRepository.getStatTheme(themeCode, term);
    }

    public List<StatResponseDto> getStatBuildYearList(String regnCode, String buildYear, String term) {
        return statRepository.getStatBuildYearList(regnCode, buildYear, term);
    }

    public List<RankUaSigunguResponseDto> getStatRankUaList_Seoul(int rankGubn, String sigunguCode, int ua) {
        return statRepository.getStatRankUaList_Seoul(rankGubn, sigunguCode, ua);
    }
}
