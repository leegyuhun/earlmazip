package com.earlmazip.service;

import com.earlmazip.controller.dto.LandDongInfoDto;

import java.util.List;

public interface LandDongService {

    List<LandDongInfoDto> getLandDongList_BySigunguCode(String sigunguCode);
}
