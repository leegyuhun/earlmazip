package com.earlmazip.service;

import com.earlmazip.controller.dto.AptLeaseResponseDto;
import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.repository.ApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final ApiRepository apiRepository;

    public List<AptPriceResponseDto> getTradeListMonthlyV1(TradeSearchCond cond) {
        String areaCode = cond.getSigunguCode().substring(0, 2);
        if (areaCode.equals("11") || areaCode.equals("28") || areaCode.equals("41")) {
            return apiRepository.getTradeListMonthlyV1(cond);
        } else if (areaCode.equals("42")) {
            return apiRepository.getTradeListMonthlyV1_GW(cond);
        } else if (areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("31") || areaCode.equals("47") || areaCode.equals("48")) {
            return apiRepository.getTradeListMonthlyV1_GS(cond);
        } else if (areaCode.equals("30") || areaCode.equals("36") || areaCode.equals("43") || areaCode.equals("44")) {
            return apiRepository.getTradeListMonthlyV1_CC(cond);
        } else if (areaCode.equals("29") || areaCode.equals("45") || areaCode.equals("46")) {
            return apiRepository.getTradeListMonthlyV1_JL(cond);
        } else {
            return apiRepository.getTradeListMonthlyV1_JJ(cond);
        }
    }

    public List<AptLeaseResponseDto> getLeaseListMonthlyV1(TradeSearchCond cond) {
        return apiRepository.getLeaseListMonthlyV1(cond);
    }
}
