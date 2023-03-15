package com.earlmazip.service.impl;

import com.earlmazip.repository.SiteInfoRepository;
import com.earlmazip.service.SiteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiteInfoServiceImpl implements SiteInfoService {

    SiteInfoRepository siteInfoRepository;

    @Autowired
    public SiteInfoServiceImpl(SiteInfoRepository siteInfoRepository) {
        this.siteInfoRepository = siteInfoRepository;
    }

    @Override
    public String findSiteInfo(String name) {
        return siteInfoRepository.findSiteInfo(name);
    }
}
