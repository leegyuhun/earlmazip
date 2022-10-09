package com.earlmazip.service;

import com.earlmazip.repository.SiteInfoRepository;
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
