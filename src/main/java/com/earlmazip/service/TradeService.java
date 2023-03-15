package com.earlmazip.service;

import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.controller.dto.TradeSearchCond;

import java.util.List;

public interface TradeService {

    List<AptPriceResponseDto> findTradeList(TradeSearchCond cond);

    List<AptPriceResponseDto> findTradeList_Office(TradeSearchCond cond);

    List<AptPriceResponseDto> getTradeListComparePrev(TradeSearchCond cond, String type);

    List<AptPriceResponseDto> getCancelDealList(String regncode);

    List<AptPriceResponseDto> findAptTradeList(TradeSearchCond cond, int term);

    List<AptPriceResponseDto> findOfficeTradeList(TradeSearchCond cond, int term);

    List<AptPriceResponseDto> getNewHighestList(String sigungucode, String uaType, String landDong);

    List<AptPriceResponseDto> getTradeDistribution_BySigungu(String dealYear, String sigunguCode);

    List<AptPriceResponseDto> getTradeDistribution_ByName(String dealYear, String sigunguCode, String landDong, String aptName);
}
