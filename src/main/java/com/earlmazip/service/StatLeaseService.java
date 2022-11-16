package com.earlmazip.service;

import com.earlmazip.controller.dto.RankLeaseResponseDto;
import com.earlmazip.controller.dto.StatLeaseResponseDto;
import com.earlmazip.repository.StatLeaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatLeaseService {

    private final StatLeaseRepository statRepository;

    public List<StatLeaseResponseDto> getStatLeaseList(String sigunguCode, String uaType, String term) {
        return statRepository.getStatLeaseList(sigunguCode, uaType, term).stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<RankLeaseResponseDto> getTopLeaseSigungu(String sigunguCode, String uaType, int leaseType) {
        return statRepository.getTopLeaseSigungu(sigunguCode, uaType, leaseType);

    }
}
