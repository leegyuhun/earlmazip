package aptdata.earlmazip.service;

import aptdata.earlmazip.domain.ApiCallStat;
import aptdata.earlmazip.repository.ApiCallStatRepository;
import aptdata.earlmazip.repository.SiteInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiCallStatService {

    private final ApiCallStatRepository apiCallStatRepository;

    @Transactional
    public void writeApiCallStat(String gubn, String name) {
        apiCallStatRepository.WriteApiCallStat(gubn, name);
    }

    public List<ApiCallStat> LoadTodayApiCallList() {
        return apiCallStatRepository.LoadTodayApiCallList();
    }

}
