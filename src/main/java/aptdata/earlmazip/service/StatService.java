package aptdata.earlmazip.service;

import aptdata.earlmazip.domain.stat_area_yymm;
import aptdata.earlmazip.domain.stat_sido_yymm;
import aptdata.earlmazip.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {

    private final StatRepository statRepository;

    public List<stat_area_yymm> findStatSeoulList() {
        return statRepository.findSeoul();
    }

    public List<stat_area_yymm> findStatSeoulListYear(String year) {
        return statRepository.findSeoulYear(year);
    }

    public List<stat_area_yymm> findStatSeoulListUA(String ua) {
        return statRepository.findSeoulUA(ua);
    }

    public List<stat_area_yymm> findStatGyunggiList() {
        return statRepository.findGyungGi();
    }

    public List<stat_area_yymm> findStatGyunggiListYear(String year) {
        return statRepository.findGyungGiYear(year);
    }

    public List<stat_area_yymm> findStatGyunggiListUA(String ua) {
        return statRepository.findGyungGiUA(ua);
    }

    public List<stat_sido_yymm> findStatGyunggiSiList(String sidoCode) {
        return statRepository.findGyungGiSi(sidoCode);
    }
}
