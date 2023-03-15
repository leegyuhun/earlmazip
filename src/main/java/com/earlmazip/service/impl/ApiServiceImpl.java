package com.earlmazip.service.impl;

import com.earlmazip.controller.dto.AptLeaseResponseDto;
import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.controller.dto.StatResponseDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.repository.ApiRepository;
import com.earlmazip.service.ApiService;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    ApiRepository apiRepository;

    @Autowired
    public ApiServiceImpl(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @Override
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

    @Override
    public List<StatResponseDto> getStatTradeListMonthlyV1(String sigunguCode, String year, String uaType) {
        return apiRepository.getStatTradeListMonthlyV1(sigunguCode, year, uaType);
    }

    @Override
    public List<AptLeaseResponseDto> getLeaseListMonthlyV1(TradeSearchCond cond) {
        return apiRepository.getLeaseListMonthlyV1(cond);
    }
}
