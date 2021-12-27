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

    public List<stat_area_yymm> findStatGyunggiList() {
        return statRepository.findGyungGi();
    }
}
