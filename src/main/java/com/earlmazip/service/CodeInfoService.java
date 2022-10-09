package com.earlmazip.service;

import com.earlmazip.repository.CodeInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeInfoService {

    private final CodeInfoRepository codeInfoRepository;

    public String getCodeName(String code) {
        return codeInfoRepository.getCodeName(code);
    }

}
