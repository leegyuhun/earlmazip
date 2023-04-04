package com.earlmazip.service.impl;

import com.earlmazip.controller.dto.RankLeaseResponseDto;
import com.earlmazip.controller.dto.StatLeaseResponseDto;
import com.earlmazip.repository.StatLeaseRepository;
import com.earlmazip.service.StatLeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatLeaseServiceImpl implements StatLeaseService {
//    StatLeaseRepository statLeaseRepository;
//    @Autowired
//    public StatLeaseServiceImpl(StatLeaseRepository statLeaseRepository) {
//        this.statLeaseRepository = statLeaseRepository;
//    }
    private final StatLeaseRepository statLeaseRepository;

    public StatLeaseServiceImpl(StatLeaseRepository statLeaseRepository) {
        this.statLeaseRepository = statLeaseRepository;
    }

    @Override
    public List<StatLeaseResponseDto> getStatLeaseList(String sigunguCode, String uaType, String term) {
        return statLeaseRepository.getStatLeaseList(sigunguCode, uaType, term).stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<RankLeaseResponseDto> getTopLeaseSigungu(String sigunguCode, String uaType, int leaseType, int dealYear) {
        return statLeaseRepository.getTopLeaseSigungu(sigunguCode, uaType, leaseType, dealYear).stream().map(RankLeaseResponseDto::new).collect(Collectors.toList());
    }
}
