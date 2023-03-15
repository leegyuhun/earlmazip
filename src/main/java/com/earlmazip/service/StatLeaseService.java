package com.earlmazip.service;

import com.earlmazip.controller.dto.RankLeaseResponseDto;
import com.earlmazip.controller.dto.StatLeaseResponseDto;
import com.earlmazip.repository.StatLeaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public interface StatLeaseService {

    List<StatLeaseResponseDto> getStatLeaseList(String sigunguCode, String uaType, String term);

    List<RankLeaseResponseDto> getTopLeaseSigungu(String sigunguCode, String uaType, int leaseType, int dealYear);
}
