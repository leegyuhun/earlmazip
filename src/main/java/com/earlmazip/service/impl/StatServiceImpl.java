package com.earlmazip.service.impl;

import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.controller.dto.RankUaSigunguResponseDto;
import com.earlmazip.controller.dto.StatResponseDto;
import com.earlmazip.repository.StatRepository;
import com.earlmazip.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatServiceImpl implements StatService {

    StatRepository statRepository;

    @Autowired
    public StatServiceImpl(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @Override
    public List<StatResponseDto> getStatTradeList_Area(String areaCode, String term) {
        return statRepository.getStatTradeList(areaCode, term).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<StatResponseDto> getStatTradeList_Seoul(String term) {
        return statRepository.getStatTradeList("11", term).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<StatResponseDto> getStatTradeByUseAreaList(String sigunguCode, String ua, String term) {
        return statRepository.getStatTradeByUseAreaList(sigunguCode, ua, term).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<StatResponseDto> getStatTradeList_Gyunggi(String term) {
        return statRepository.getStatTradeList("41", term).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<StatResponseDto> getStatTradeList_Incheon(String term) {
        return statRepository.getStatTradeList("28", term).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<AptPriceResponseDto> getStatTradeTopByYear(String year, String sigungucode, String uaType) {
        return statRepository.getStatTradeTopByYear(year, sigungucode, uaType).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<AptPriceResponseDto> getStatTradeOfficeTopByYear(String year, String sigungucode, String uaType) {
        return statRepository.getStatTradeOfficeTopByYear(year, sigungucode, uaType).stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<StatResponseDto> getStatNewHighestAndTradeCount(String sigunguCode, String uaType, int term) {
        return statRepository.getStatNewHighestAndTradeCount(sigunguCode, uaType, term).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<StatResponseDto> getStatTheme(String themeCode, String term) {
        return statRepository.getStatTheme(themeCode, term);
    }

    @Override
    public List<RankUaSigunguResponseDto> getStatRankUaTypeList(int rankGubn, int dealYear, String sigunguCode, String uaType) {
        return statRepository.getStatRankUaTypeList(rankGubn, dealYear, sigunguCode, uaType);
    }

    @Override
    public List<RankUaSigunguResponseDto> getStatRankLeaseUaTypeList(int rankGubn, int dealYear, String sigunguCode, String uaType) {
        return statRepository.getStatRankLeaseUaTypeList(rankGubn, dealYear, sigunguCode, uaType);
    }

    @Override
    public List<StatResponseDto> getStatByDealType(String sigunguCode, String uaTYpe, int dealType) {
        return statRepository.getStatByDealType(sigunguCode, uaTYpe, dealType).stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<StatResponseDto> getDistribution(String areaCode, String dealYear) {
        return statRepository.getDistribution(areaCode, dealYear);
    }
}
