package com.earlmazip.service;

import com.earlmazip.controller.dto.*;
import com.earlmazip.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatService {

    private final StatRepository statRepository;

    public List<StatResponseDto> getStatTradeList_Area(String areaCode, String term) {
        return statRepository.getStatTradeList(areaCode, term).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatTradeList_Seoul(String term) {
        return statRepository.getStatTradeList("11", term).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatTradeByUseAreaList(String sigunguCode, String ua, String term) {
        return statRepository.getStatTradeByUseAreaList(sigunguCode, ua, term).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatTradeList_Gyunggi(String term) {
        return statRepository.getStatTradeList("41", term).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }
    public List<StatResponseDto> getStatTradeList_Incheon(String term) {
        return statRepository.getStatTradeList("28", term).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getStatTradeTopByYear(String year, String sigungucode, String uaType) {
        return statRepository.getStatTradeTopByYear(year, sigungucode, uaType).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatNewHighestAndTradeCount(String sigunguCode, String uaType, int term) {
        return statRepository.getStatNewHighestAndTradeCount(sigunguCode, uaType, term).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatTheme(String themeCode, String term) {
        return statRepository.getStatTheme(themeCode, term);
    }

    public List<RankUaSigunguResponseDto> getStatRankUaTypeList(int rankGubn, int dealYear, String sigunguCode, String uaType) {
        return statRepository.getStatRankUaTypeList(rankGubn, dealYear, sigunguCode, uaType);
    }

    public List<StatResponseDto> getStatByDealType(String sigunguCode, String uaTYpe, int dealType) {
        return statRepository.getStatByDealType(sigunguCode, uaTYpe, dealType).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getDistribution(String areaCode, String dealYear) {
        return statRepository.getDistribution(areaCode, dealYear);
    }
}
