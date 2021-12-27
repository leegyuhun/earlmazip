package aptdata.earlmazip.service;

import aptdata.earlmazip.domain.stat_area_yymm;
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

    public List<stat_area_yymm> findStatSeoulListUA02() {
        return statRepository.findSeoulUA2();
    }

    public List<stat_area_yymm> findStatSeoulListUA03() {
        return statRepository.findSeoulUA3();
    }

    public List<stat_area_yymm> findStatSeoulListUA04() {
        return statRepository.findSeoulUA4();
    }

    public List<stat_area_yymm> findStatSeoulListUA05() {
        return statRepository.findSeoulUA5();
    }

    public List<stat_area_yymm> findStatSeoulListUA06() {
        return statRepository.findSeoulUA6();
    }

    public List<stat_area_yymm> findStatGyunggiList() {
        return statRepository.findGyungGi();
    }

    public List<stat_area_yymm> findStatGyunggiListYear(String year) {
        return statRepository.findGyungGiYear(year);
    }
}
