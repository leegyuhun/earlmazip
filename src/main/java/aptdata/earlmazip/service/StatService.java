package aptdata.earlmazip.service;

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

    public List<StatResponseDto> findStatSeoulList(String term) {
        return statRepository.findSeoul(term);
    }

    public List<StatResponseDto> findStatSeoulListYear(String year) {
        return statRepository.findSeoulYear(year);
    }

    public List<StatResponseDto> findStatSeoulListUA(String ua) {
        return statRepository.findSeoulUA(ua);
    }

    public List<RankYearResponseDto> findSeoulTopList(String year, String sigungucode) {
        return statRepository.findSeoulTop(year, sigungucode);
    }

    public List<StatResponseDto> findStatGyunggiList(String term) {
        return statRepository.findGyungGi(term);
    }

    public List<StatResponseDto> findStatGyunggiListYear(String year) {
        return statRepository.findGyungGiYear(year);
    }

    public List<StatResponseDto> findStatGyunggiListUA(String ua) {
        return statRepository.findGyungGiUA(ua);
    }

    public List<StatResponseDto> findStatGyunggiSiList(String sidoCode) {
        return statRepository.findGyungGiSi(sidoCode);
    }

    public List<RankYearResponseDto> findGyunggiTopList(String year, String sidocode) {
        return statRepository.findGyungGiTop(year, sidocode);
    }

    public List<RankYearResponseDto> findIncheonTopList(String year, String sigungucode) {
        return statRepository.findIncheonTop(year, sigungucode);
    }

    public List<StatLeaseResponseDto> statLeaseSido(String sidoCode) {
        return statRepository.statLeaseSido(sidoCode);
    }

    public List<StatLeaseResponseDto> statLeaseMonthlySido(String sidoCode) {
        return statRepository.statLeaseMonthlySido(sidoCode);
    }

    public List<StatResponseDto> getStatNewHighestAndTradeCount(String sidoCode) {
        return statRepository.getStatNewHighestAndTradeCount(sidoCode);
    }
}
