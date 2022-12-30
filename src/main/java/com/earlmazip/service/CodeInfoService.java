package com.earlmazip.service;

import com.earlmazip.domain.SigunguCode;
import com.earlmazip.repository.CodeInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeInfoService {

    private final CodeInfoRepository codeInfoRepository;

    public String getCodeName(String code) {
        return codeInfoRepository.getCodeName(code);
    }

    public List<SigunguCode> getSigunguList(String areaCode) {
        return codeInfoRepository.getSigunguList(areaCode);
    }

}
