package com.earlmazip.service;

import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;

    public List<AptPriceResponseDto> findTradeList(TradeSearchCond cond) {
        return tradeRepository.findTradeList(cond).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

//    public List<AptPriceResponseDto> getTradeComparePrevList_SigunguUAType(String sigungucode, String type, String uaType) {
    public List<AptPriceResponseDto> findTradeComparePrevList(TradeSearchCond cond, String type) {
        return tradeRepository.findTradeComparePrevList(cond, type).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
//        return tradeRepository.getTradeComparePrevList_SigunguUAType(sigungucode, type, uaType);
    }

    public List<AptPriceResponseDto> getCancelDealList(String regncode) {
        return tradeRepository.getCancelDealList(regncode);
    }

//    public List<AptPriceResponseDto> getAptTradeList_ByName(String regncode, String landDong, String aptName, int ua, int term) {
    public List<AptPriceResponseDto> findAptTradeList(TradeSearchCond cond, int term) {
        return tradeRepository.findAptTradeList(cond, term).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
//        return tradeRepository.getAptTradeList_ByName(regncode, landDong, aptName, ua, term);
    }

    public List<AptPriceResponseDto> getNewHighestList(String sigungucode, String uaType) {
        return tradeRepository.getNewHighestList(sigungucode, uaType);
    }

    public List<AptPriceResponseDto> getTradeDistribution_BySigungu(String dealYear, String sigungucode) {
        return tradeRepository.getTradeDistribution_BySigungu(dealYear, sigungucode);
    }

    public List<AptPriceResponseDto> getTradeDistribution_ByName(String dealYear, String sigungucode, String landDong, String aptName) {
        return tradeRepository.getTradeDistribution_ByName(dealYear, sigungucode, landDong, aptName);
    }
}
