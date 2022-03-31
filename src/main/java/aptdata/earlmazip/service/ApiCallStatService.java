package aptdata.earlmazip.service;

import aptdata.earlmazip.repository.ApiCallStatRepository;
import aptdata.earlmazip.repository.SiteInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApiCallStatService {

    private final ApiCallStatRepository apiCallStatRepository;

    @Transactional
    public void writeApiCallStat(String gubn, String name) {
        apiCallStatRepository.WriteApiCallStat(gubn, name);
    }

}
