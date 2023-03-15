package com.earlmazip.service;

import com.earlmazip.controller.dto.AptLeaseResponseDto;
import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.controller.dto.StatResponseDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.repository.ApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ApiService {

    List<AptPriceResponseDto> getTradeListMonthlyV1(TradeSearchCond cond);

    List<StatResponseDto> getStatTradeListMonthlyV1(String sigunguCode, String year, String uaType);

    List<AptLeaseResponseDto> getLeaseListMonthlyV1(TradeSearchCond cond);
}
