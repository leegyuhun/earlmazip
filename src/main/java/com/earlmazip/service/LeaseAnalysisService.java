package com.earlmazip.service;

import com.earlmazip.controller.dto.StatLeaseAnalysisDto;
import com.earlmazip.repository.LeaseAnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaseAnalysisService {

    private final LeaseAnalysisRepository leaseAnalysisRepository;

    public List<StatLeaseAnalysisDto> getLeaseAnalysisList(String gubnCode)
    {
        return leaseAnalysisRepository.getLeaseAnalysisList(gubnCode);
    }
}
