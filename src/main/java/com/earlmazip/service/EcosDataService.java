package com.earlmazip.service;

import com.earlmazip.controller.dto.EcosDataResponseDto;
import com.earlmazip.controller.dto.PopulationResponseDto;

import java.util.List;

public interface EcosDataService {

    List<EcosDataResponseDto> getEcosData(String statCode, String itemCode1, String itemCode2, String term);

    List<PopulationResponseDto> getPopulationSigungu(String sigunguCode, int sortType);
}
