package com.earlmazip.service.impl;

import com.earlmazip.controller.dto.AptResponseDto;
import com.earlmazip.domain.AptInfo;
import com.earlmazip.repository.AptRepository;
import com.earlmazip.repository.AptSearch;
import com.earlmazip.service.AptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AptServiceImpl implements AptService {

//    AptRepository aptRepository;
//    @Autowired
//    public AptServiceImpl(AptRepository aptRepository) {
//        this.aptRepository = aptRepository;
//    }

    private final AptRepository aptRepository;

    public AptServiceImpl(AptRepository aptRepository) {
        this.aptRepository = aptRepository;
    }

    @Override
    public List<AptInfo> findApts() {
        return aptRepository.findAll();
    }

    @Override
    public List<AptInfo> findByName(String name) {
        return aptRepository.findByName(name);
    }

    @Override
    public List<AptResponseDto> findAllByName(AptSearch aptSearch) {
        return aptRepository.findAllByString(aptSearch);
    }
}
