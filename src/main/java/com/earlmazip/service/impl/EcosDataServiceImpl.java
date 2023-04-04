package com.earlmazip.service.impl;

import com.earlmazip.controller.dto.EcosDataResponseDto;
import com.earlmazip.controller.dto.PopulationResponseDto;
import com.earlmazip.repository.EcosDataRepository;
import com.earlmazip.repository.PopulationRepository;
import com.earlmazip.service.EcosDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EcosDataServiceImpl implements EcosDataService {

//    EcosDataRepository ecosDataRepository;
//    PopulationRepository populationRepository;

//    @Autowired
//    public EcosDataServiceImpl(EcosDataRepository ecosDataRepository, PopulationRepository populationRepository) {
//        this.ecosDataRepository = ecosDataRepository;
//        this.populationRepository = populationRepository;
//    }

    private final EcosDataRepository ecosDataRepository;
    private final PopulationRepository populationRepository;

    public EcosDataServiceImpl(EcosDataRepository ecosDataRepository, PopulationRepository populationRepository) {
        this.ecosDataRepository = ecosDataRepository;
        this.populationRepository = populationRepository;
    }

    @Override
    public List<EcosDataResponseDto> getEcosData(String statCode, String itemCode1, String itemCode2, String term) {
        return ecosDataRepository.getEcosData(statCode, itemCode1, itemCode2, term).stream().map(EcosDataResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<PopulationResponseDto> getPopulationSigungu(String sigunguCode, int sortType) {
        return populationRepository.findPopulationSigungu(sigunguCode, sortType).stream().map(PopulationResponseDto::new).collect(Collectors.toList());
    }
}
