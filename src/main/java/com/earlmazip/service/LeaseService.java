package com.earlmazip.service;

import com.earlmazip.controller.dto.AptLeaseResponseDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.repository.LeaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaseService {

    private final LeaseRepository leaseRepository;

    public List<AptLeaseResponseDto> findLeaseList(TradeSearchCond cond) {
        return leaseRepository.findLeaseList(cond).stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<AptLeaseResponseDto> findLeaseRenewalList(String sigunguCode, String landDong, String uaType)
    {
        return leaseRepository.findLeaseRenewalList(sigunguCode, landDong, uaType).stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<AptLeaseResponseDto> findAptLeaseList(TradeSearchCond cond, String type, int term) {
        return leaseRepository.findAptLeaseList(cond, type, term).stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }
}
