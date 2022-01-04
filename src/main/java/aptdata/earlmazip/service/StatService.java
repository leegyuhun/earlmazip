package aptdata.earlmazip.service;

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

    public List<StatAreaYYMM> findStatSeoulList() {
        return statRepository.findSeoul();
    }

    public List<StatAreaYYMM> findStatSeoulListYear(String year) {
        return statRepository.findSeoulYear(year);
    }

    public List<StatAreaYYMM> findStatSeoulListUA(String ua) {
        return statRepository.findSeoulUA(ua);
    }

    public List<RankYear> findSeoulTopList(String sigungucode) {
        return statRepository.findSeoulTop(sigungucode);
    }

    public List<StatAreaYYMM> findStatGyunggiList() {
        return statRepository.findGyungGi();
    }

    public List<StatAreaYYMM> findStatGyunggiListYear(String year) {
        return statRepository.findGyungGiYear(year);
    }

    public List<StatAreaYYMM> findStatGyunggiListUA(String ua) {
        return statRepository.findGyungGiUA(ua);
    }

    public List<StatSidoYYMM> findStatGyunggiSiList(String sidoCode) {
        return statRepository.findGyungGiSi(sidoCode);
    }

    public List<RankYear> findGyunggiTopList(String sidocode) {
        return statRepository.findGyungGiTop(sidocode);
    }
}
