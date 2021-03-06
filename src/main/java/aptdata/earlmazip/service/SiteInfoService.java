package aptdata.earlmazip.service;

import aptdata.earlmazip.repository.SiteInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteInfoService {

    private final SiteInfoRepository siteInfoRepository;

    public String findSiteInfo(String name) {
        return siteInfoRepository.findSiteInfo(name);
    }

}
