package com.earlmazip.controller.dto;

import com.earlmazip.domain.LandDongInfo;
import lombok.Getter;

@Getter
public class LandDongInfoDto {

    private String sigunguCode;

    private String sigunguName;

    private String landDong;

    public LandDongInfoDto(LandDongInfo entity) {
        this.sigunguCode = entity.getSigunguCode();
        this.sigunguName = entity.getSigunguName();
        this.landDong = entity.getLandDong();
    }
}
