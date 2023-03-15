package com.earlmazip.service.impl;

import com.earlmazip.controller.dto.LandDongInfoDto;
import com.earlmazip.repository.LandDongRepository;
import com.earlmazip.service.LandDongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LandDongServiceImpl implements LandDongService {

    LandDongRepository landDongRepository;

    @Autowired
    public LandDongServiceImpl(LandDongRepository landDongRepository) {
        this.landDongRepository = landDongRepository;
    }

    @Override
    public List<LandDongInfoDto> getLandDongList_BySigunguCode(String sigunguCode) {
        if (sigunguCode.length() == 5) {
            return landDongRepository.getLandDongList_BySigunguCode(sigunguCode);
        } else {
            return new ArrayList<>();
        }
    }
}
