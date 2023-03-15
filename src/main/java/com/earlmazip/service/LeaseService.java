package com.earlmazip.service;

import com.earlmazip.controller.dto.AptLeaseResponseDto;
import com.earlmazip.controller.dto.TradeSearchCond;

import java.util.List;

public interface LeaseService {

    List<AptLeaseResponseDto> findLeaseList(TradeSearchCond cond);

    List<AptLeaseResponseDto> findLeaseList_Office(TradeSearchCond cond);

    List<AptLeaseResponseDto> findLeaseRenewalList(String sigunguCode, String landDong, String uaType);

    List<AptLeaseResponseDto> findAptLeaseList(TradeSearchCond cond, String type, int term);
}
