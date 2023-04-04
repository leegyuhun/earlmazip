package com.earlmazip.service.impl;

import com.earlmazip.domain.SigunguCode;
import com.earlmazip.repository.CodeInfoRepository;
import com.earlmazip.service.CodeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeInfoServiceImpl implements CodeInfoService {

//    CodeInfoRepository codeInfoRepository;
//    @Autowired
//    public CodeInfoServiceImpl(CodeInfoRepository codeInfoRepository) {
//        this.codeInfoRepository = codeInfoRepository;
//    }

    private final CodeInfoRepository codeInfoRepository;

    public CodeInfoServiceImpl(CodeInfoRepository codeInfoRepository) {
        this.codeInfoRepository = codeInfoRepository;
    }

    @Override
    public String getCodeName(String code) {
        if (code.length() == 1) {
            return "";
        } else {
            return codeInfoRepository.getCodeName(code);
        }
    }

    @Override
    public List<SigunguCode> getSigunguList(String areaCode) {
        return codeInfoRepository.getSigunguList(areaCode);
    }
}
