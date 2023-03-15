package com.earlmazip.service;

import com.earlmazip.controller.dto.*;
import com.earlmazip.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public interface StatService {

    List<StatResponseDto> getStatTradeList_Area(String areaCode, String term);

    List<StatResponseDto> getStatTradeList_Seoul(String term);

    List<StatResponseDto> getStatTradeByUseAreaList(String sigunguCode, String ua, String term);

    List<StatResponseDto> getStatTradeList_Gyunggi(String term);

    List<StatResponseDto> getStatTradeList_Incheon(String term);

    List<AptPriceResponseDto> getStatTradeTopByYear(String year, String sigungucode, String uaType);

    List<AptPriceResponseDto> getStatTradeOfficeTopByYear(String year, String sigungucode, String uaType);

    List<StatResponseDto> getStatNewHighestAndTradeCount(String sigunguCode, String uaType, int term);

    List<StatResponseDto> getStatTheme(String themeCode, String term);

    List<RankUaSigunguResponseDto> getStatRankUaTypeList(int rankGubn, int dealYear, String sigunguCode, String uaType);

    List<RankUaSigunguResponseDto> getStatRankLeaseUaTypeList(int rankGubn, int dealYear, String sigunguCode, String uaType);

    List<StatResponseDto> getStatByDealType(String sigunguCode, String uaTYpe, int dealType);

    List<StatResponseDto> getDistribution(String areaCode, String dealYear);
}
