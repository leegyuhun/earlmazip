package com.earlmazip.service.impl;

import com.earlmazip.controller.dto.StatLeaseAnalysisDto;
import com.earlmazip.repository.LeaseAnalysisRepository;
import com.earlmazip.service.LeaseAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaseAnalysisServiceImpl implements LeaseAnalysisService {

//    LeaseAnalysisRepository leaseAnalysisRepository;
//    @Autowired
//    public LeaseAnalysisServiceImpl(LeaseAnalysisRepository leaseAnalysisRepository) {
//        this.leaseAnalysisRepository = leaseAnalysisRepository;
//    }

    private final LeaseAnalysisRepository leaseAnalysisRepository;

    public LeaseAnalysisServiceImpl(LeaseAnalysisRepository leaseAnalysisRepository) {
        this.leaseAnalysisRepository = leaseAnalysisRepository;
    }

    @Override
    public List<StatLeaseAnalysisDto> getLeaseAnalysisList(String gubnCode, int term) {
        return leaseAnalysisRepository.getLeaseAnalysisList(gubnCode, term).stream().map(StatLeaseAnalysisDto::new).collect(Collectors.toList());
    }
}
