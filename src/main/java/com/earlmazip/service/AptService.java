package com.earlmazip.service;

import com.earlmazip.controller.dto.AptResponseDto;
import com.earlmazip.domain.AptInfo;
import com.earlmazip.repository.AptRepository;
import com.earlmazip.repository.AptSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AptService {

    List<AptInfo> findApts();

    List<AptInfo> findByName(String name);

    List<AptResponseDto> findAllByName(AptSearch aptSearch);
}
