package com.earlmazip.service;

import com.earlmazip.controller.dto.LandDongInfoDto;
import com.earlmazip.repository.LandDongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LandDongService {

    private final LandDongRepository landDongRepository;

    public List<LandDongInfoDto> getLandDongList_BySigunguCode(String sigunguCode) {
        return landDongRepository.getLandDongList_BySigunguCode(sigunguCode);
    }
}
