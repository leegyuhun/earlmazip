package com.earlmazip.service;

import com.earlmazip.controller.dto.EcosDataResponseDto;
import com.earlmazip.controller.dto.PopulationResponseDto;
import com.earlmazip.repository.EcosDataRepository;
import com.earlmazip.repository.PopulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EcosDataService {

    private final EcosDataRepository ecosDataRepository;
    private final PopulationRepository populationRepository;

    public List<EcosDataResponseDto> getEcosData(String statCode, String itemCode1, String itemCode2, String term) {
        return ecosDataRepository.getEcosData(statCode, itemCode1, itemCode2, term);
    }

    public List<PopulationResponseDto> getPopulationSigungu(String sigunguCode, int sortType) {
        return populationRepository.findPopulationSigungu(sigunguCode, sortType).stream().map(PopulationResponseDto::new).collect(Collectors.toList());
    }

}
