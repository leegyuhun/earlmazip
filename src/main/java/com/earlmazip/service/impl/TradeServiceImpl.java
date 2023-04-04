package com.earlmazip.service.impl;

import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.repository.TradeRepository;
import com.earlmazip.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;
//    TradeRepository tradeRepository;

//    @Autowired
//    public TradeServiceImpl(TradeRepository tradeRepository) {
//        this.tradeRepository = tradeRepository;
//    }
    // 생성자를 통한 TradeRepository 객체주입
    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
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

    @Override
    public List<AptPriceResponseDto> findTradeList_Office(TradeSearchCond cond) {
        return tradeRepository.findTradeList_Office(cond);
    }

    @Override
    public List<AptPriceResponseDto> getTradeListComparePrev(TradeSearchCond cond, String type) {
        String areaCode = cond.getSigunguCode().substring(0, 2);
        if (areaCode.equals("11") || areaCode.equals("28") || areaCode.equals("41")) {
            return tradeRepository.getTradeListComparePrev(cond, type).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else if (areaCode.equals("42")) {
            return tradeRepository.getTradeListComparePrev_GW(cond, type).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else if (areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("31") || areaCode.equals("47") || areaCode.equals("48")) {
            return tradeRepository.getTradeListComparePrev_GS(cond, type).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else if (areaCode.equals("30") || areaCode.equals("36") || areaCode.equals("43") || areaCode.equals("44")) {
            return tradeRepository.getTradeListComparePrev_CC(cond, type).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else if (areaCode.equals("29") || areaCode.equals("45") || areaCode.equals("46")) {
            return tradeRepository.getTradeListComparePrev_JL(cond, type).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else {
            return tradeRepository.getTradeListComparePrev_JJ(cond, type).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        }
    }

    @Override
    public List<AptPriceResponseDto> getCancelDealList(String regncode) {
        return tradeRepository.findCancelDealList(regncode).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    @Override
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

    @Override
    public List<AptPriceResponseDto> findOfficeTradeList(TradeSearchCond cond, int term) {
        return tradeRepository.findOfficeTradeList(cond, term);
    }

    @Override
    public List<AptPriceResponseDto> getNewHighestList(String sigungucode, String uaType, String landDong) {
        String areaCode = sigungucode.substring(0, 2);
        if (areaCode.equals("11") || areaCode.equals("28") || areaCode.equals("41")) {
            return tradeRepository.findNewHighestList(sigungucode, uaType, landDong).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else if (areaCode.equals("42")) {
            return tradeRepository.findNewHighestList_GW(sigungucode, uaType, landDong).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else if (areaCode.equals("26") || areaCode.equals("27") || areaCode.equals("31") || areaCode.equals("47") || areaCode.equals("48")) {
            return tradeRepository.findNewHighestList_GS(sigungucode, uaType, landDong).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else if (areaCode.equals("30") || areaCode.equals("36") || areaCode.equals("43") || areaCode.equals("44")) {
            return tradeRepository.findNewHighestList_CC(sigungucode, uaType, landDong).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else if (areaCode.equals("29") || areaCode.equals("45") || areaCode.equals("46")) {
            return tradeRepository.findNewHighestList_JL(sigungucode, uaType, landDong).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else {
            return tradeRepository.findNewHighestList_JJ(sigungucode, uaType, landDong).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        }
    }

    @Override
    public List<AptPriceResponseDto> getTradeDistribution_BySigungu(String dealYear, String sigunguCode) {
        return tradeRepository.findTradeDistribution_BySigungu(dealYear, sigunguCode).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<AptPriceResponseDto> getTradeDistribution_ByName(String dealYear, String sigunguCode, String landDong, String aptName) {
        return tradeRepository.getTradeDistribution_ByName(dealYear, sigunguCode, landDong, aptName).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }
}
