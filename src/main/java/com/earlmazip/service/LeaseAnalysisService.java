package com.earlmazip.service;

import com.earlmazip.controller.dto.StatLeaseAnalysisDto;
import com.earlmazip.repository.LeaseAnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaseAnalysisService {

    private final LeaseAnalysisRepository leaseAnalysisRepository;

    public List<StatLeaseAnalysisDto> getLeaseAnalysisList(String gubnCode, int term) {
        return leaseAnalysisRepository.getLeaseAnalysisList(gubnCode, term).stream().map(StatLeaseAnalysisDto::new).collect(Collectors.toList());
    }
}
