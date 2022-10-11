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

    public List<AptLeaseResponseDto> getLeaseRenewalList_SeoulSigungu(String sigungucode)
    {
        return leaseRepository.getLeaseRenewalList_SeoulSigungu(sigungucode);
    }

    public List<AptLeaseResponseDto> getLeaseList_ByName(String regncode, String dong, String aptName, int ua, int term) {
        return leaseRepository.getLeaseList_ByName(regncode, dong, aptName, ua, term);
    }

    public List<AptLeaseResponseDto> findAptLeaseList(TradeSearchCond cond, String type, int term) {
        return leaseRepository.findAptLeaseList(cond, type, term).stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<AptLeaseResponseDto> getMonthlyList_ByName(String regncode, String dong, String aptName, int ua, int term) {
        return leaseRepository.getMonthlyList_ByName(regncode, dong, aptName, ua, term);
    }

}
