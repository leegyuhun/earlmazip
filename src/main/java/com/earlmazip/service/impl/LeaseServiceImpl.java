package com.earlmazip.service.impl;

import com.earlmazip.controller.dto.AptLeaseResponseDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.repository.LeaseRepository;
import com.earlmazip.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaseServiceImpl implements LeaseService {

//    LeaseRepository leaseRepository;
//    @Autowired
//    public LeaseServiceImpl(LeaseRepository leaseRepository) {
//        this.leaseRepository = leaseRepository;
//    }

    private final LeaseRepository leaseRepository;

    public LeaseServiceImpl(LeaseRepository leaseRepository) {
        this.leaseRepository = leaseRepository;
    }

    @Override
    public List<AptLeaseResponseDto> findLeaseList(TradeSearchCond cond) {
        return leaseRepository.findLeaseList(cond).stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<AptLeaseResponseDto> findLeaseList_Office(TradeSearchCond cond) {
        return leaseRepository.findLeaseList_Office(cond).stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<AptLeaseResponseDto> findLeaseRenewalList(String sigunguCode, String landDong, String uaType) {
        return leaseRepository.findLeaseRenewalList(sigunguCode, landDong, uaType).stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<AptLeaseResponseDto> findAptLeaseList(TradeSearchCond cond, String type, int term) {
        return leaseRepository.findAptLeaseList(cond, type, term).stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }
}
