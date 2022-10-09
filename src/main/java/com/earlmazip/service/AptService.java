package com.earlmazip.service;

import com.earlmazip.controller.dto.AptResponseDto;
import com.earlmazip.domain.AptInfo;
import com.earlmazip.repository.AptRepository;
import com.earlmazip.repository.AptSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AptService {

    private final AptRepository aptRepository;

    public List<AptInfo> findApts(){return aptRepository.findAll();}

    public List<AptInfo> findByName(String name) {
        return aptRepository.findByName(name);
    }

    public List<AptResponseDto> findAllByName(AptSearch aptSearch) {
        return aptRepository.findAllByString(aptSearch);
    }
}
