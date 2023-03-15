package com.earlmazip.service;

import com.earlmazip.domain.SigunguCode;

import java.util.List;

public interface CodeInfoService {

    String getCodeName(String code);

    List<SigunguCode> getSigunguList(String areaCode);
}
