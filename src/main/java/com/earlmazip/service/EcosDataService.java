package com.earlmazip.service;

import com.earlmazip.controller.dto.EcosDataResponseDto;
import com.earlmazip.repository.EcosDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EcosDataService {

    private final EcosDataRepository ecosDataRepository;

    public List<EcosDataResponseDto> getEcosData(String statCode, String itemCode1, String itemCode2, String term) {
        return ecosDataRepository.getEcosData(statCode, itemCode1, itemCode2, term);
    }

}
