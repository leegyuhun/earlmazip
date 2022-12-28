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
        String areaCode = cond.getSigunguCode().substring(0, 2);
        if (areaCode.equals("11") || areaCode.equals("28") || areaCode.equals("41")) {
            return tradeRepository.findTradeList(cond);
        } else if (areaCode.equals("42")) {
            return tradeRepository.findTradeList_GW(cond);
        } else if (areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("31") || areaCode.equals("47") || areaCode.equals("48")) {
            return tradeRepository.findTradeList_GS(cond);
        } else if (areaCode.equals("30") || areaCode.equals("36") || areaCode.equals("43") || areaCode.equals("44")) {
            return tradeRepository.findTradeList_CC(cond);
        } else if (areaCode.equals("29") || areaCode.equals("45") || areaCode.equals("46")) {
            return tradeRepository.findTradeList_JL(cond);
        } else {
            return tradeRepository.findTradeList_JJ(cond);
        }
    }

    public List<AptPriceResponseDto> findTradeComparePrevList(TradeSearchCond cond, String type) {
        return tradeRepository.findTradeComparePrevList(cond, type).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getCancelDealList(String regncode) {
        return tradeRepository.findCancelDealList(regncode).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> findAptTradeList(TradeSearchCond cond, int term) {
        String areaCode = cond.getSigunguCode().substring(0, 2);
        if (areaCode.equals("11") || areaCode.equals("28") || areaCode.equals("41")) {
            return tradeRepository.findAptTradeList(cond, term);
        } else if (areaCode.equals("42")) {
            return tradeRepository.findAptTradeList_GW(cond, term);
        } else if (areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("31") || areaCode.equals("47") || areaCode.equals("48")) {
            return tradeRepository.findAptTradeList_GS(cond, term);
        } else if (areaCode.equals("30") || areaCode.equals("36") || areaCode.equals("43") || areaCode.equals("44")) {
            return tradeRepository.findAptTradeList_CC(cond, term);
        } else if (areaCode.equals("29") || areaCode.equals("45") || areaCode.equals("46")) {
            return tradeRepository.findAptTradeList_JL(cond, term);
        } else {
            return tradeRepository.findAptTradeList_JJ(cond, term);
        }
    }

    public List<AptPriceResponseDto> getNewHighestList(String sigungucode, String uaType, String landDong) {
        return tradeRepository.findNewHighestList(sigungucode, uaType, landDong).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getTradeDistribution_BySigungu(String dealYear, String sigunguCode) {
        return tradeRepository.findTradeDistribution_BySigungu(dealYear, sigunguCode).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getTradeDistribution_ByName(String dealYear, String sigunguCode, String landDong, String aptName) {
        return tradeRepository.getTradeDistribution_ByName(dealYear, sigunguCode, landDong, aptName).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }
}
