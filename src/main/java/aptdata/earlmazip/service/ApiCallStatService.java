package aptdata.earlmazip.service;

import aptdata.earlmazip.domain.ApiCallStat;
import aptdata.earlmazip.repository.ApiCallStatRepository;
import aptdata.earlmazip.repository.ApiStatisticsRepository;
import aptdata.earlmazip.repository.SiteInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiCallStatService {

    private final ApiCallStatRepository apiCallStatRepository;

    private final ApiStatisticsRepository apiStatisticsRepository;

    @Transactional
    public void writeApiCallStat(String gubn, String name, String code) {
        if(code.equals("")) return;
        apiCallStatRepository.WriteApiCallStat(gubn, name, code);
    }

    public List<ApiCallStat> LoadTodayApiCallList(String gubn) {
        return apiCallStatRepository.LoadTodayApiCallList(gubn);
    }

    public List<ApiCallStat> findAllToday(String date) {
        return apiStatisticsRepository.findAllToday(date);
    }

    public List<ApiCallStat> findGubnToday(String date, String gubn) {
        return apiStatisticsRepository.findGubnToday(date, gubn);
    }

}
