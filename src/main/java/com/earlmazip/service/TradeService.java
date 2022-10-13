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

    public List<AptPriceResponseDto> findTradeComparePrevList(TradeSearchCond cond, String type) {
        return tradeRepository.findTradeComparePrevList(cond, type).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getCancelDealList(String regncode) {
        return tradeRepository.findCancelDealList(regncode).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> findAptTradeList(TradeSearchCond cond, int term) {
        return tradeRepository.findAptTradeList(cond, term).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getNewHighestList(String sigungucode, String uaType) {
        return tradeRepository.findNewHighestList(sigungucode, uaType).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getTradeDistribution_BySigungu(String dealYear, String sigunguCode) {
        return tradeRepository.findTradeDistribution_BySigungu(dealYear, sigunguCode).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getTradeDistribution_ByName(String dealYear, String sigunguCode, String landDong, String aptName) {
        return tradeRepository.getTradeDistribution_ByName(dealYear, sigunguCode, landDong, aptName).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }
}
