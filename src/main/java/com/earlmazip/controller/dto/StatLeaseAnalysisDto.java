package com.earlmazip.controller.dto;

import com.earlmazip.domain.StatLeaseAnalysis;
import lombok.Getter;

@Getter
public class StatLeaseAnalysisDto {
    private String sigunguCode;
    private String sigunguName;
    private String dealYear;
    private String dealYYMM;
    public String getDealYYMM() {
        return dealYYMM.substring(0,4) + "-" + dealYYMM.substring(4,6);
    }
    private int shortCnt;
    private int longCnt;
    private int sum;
    private float rate;

    public StatLeaseAnalysisDto(StatLeaseAnalysis entity) {
        this.sigunguCode = entity.getSigunguCode();
        this.sigunguName = entity.getSigunguName();
        this.dealYear = entity.getDealYear();
        this.dealYYMM = entity.getDealYYMM();
        this.shortCnt = entity.getShortCnt();
        this.longCnt = entity.getLongCnt();
        this.sum = entity.getSum();
        this.rate = entity.getRate();
    }
}
