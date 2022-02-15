package aptdata.earlmazip.service;

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

    public List<StatResponseDto> findStatSeoulList() {
        return statRepository.findSeoul();
    }

    public List<StatResponseDto> findStatSeoulListYear(String year) {
        return statRepository.findSeoulYear(year);
    }

    public List<StatResponseDto> findStatSeoulListUA(String ua) {
        return statRepository.findSeoulUA(ua);
    }

    public List<RankYear> findSeoulTopList(String year, String sigungucode) {
        return statRepository.findSeoulTop(year, sigungucode);
    }

    public List<StatResponseDto> findStatGyunggiList() {
        return statRepository.findGyungGi();
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

    public List<RankYear> findGyunggiTopList(String year, String sidocode) {
        return statRepository.findGyungGiTop(year, sidocode);
    }
}
