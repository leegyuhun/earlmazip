package com.earlmazip.controller.dto;

import com.earlmazip.domain.Population;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.DecimalFormat;

@Getter
public class PopulationResponseDto {
    private String year;

    private String quarter;

    private String date;
    public String getDate(){
        return this.year + "년 " + this.quarter + "분기";
    }

    private String sigunguCode;

    private String sigunguName;
    public String getSigunguName() {
        if (this.sigunguName.length() > 2) {
            return this.sigunguName.replace("서울 ", "");
        } else {
            return this.sigunguName;
        }
    }

    private int households;

    private int koreanCount;
    private String koreanRate;
    public String getKoreanRate() {
        return new DecimalFormat("0.#").format(((float)this.koreanCount / this.totalCount)*100) + "%";
    }

    private int foreignerCount;
    private String foreignerRate;
    public String getForeignerRate() {
        return new DecimalFormat("0.#").format(((float)this.foreignerCount / this.totalCount)*100) + "%";
    }

    private int totalCount;

    private int oldCount;

    private String oldRate;
    public String getOldRate() {
        return new DecimalFormat("0.#").format(((float)this.oldCount / this.totalCount)*100) + "%";
    }

    public PopulationResponseDto(Population entity) {
        this.year = entity.getYear();
        this.quarter = entity.getQuarter();
        this.sigunguCode = entity.getSigunguCode();
        this.sigunguName = entity.getSigunguName();
        this.households = entity.getHouseholds();
        this.koreanCount = entity.getKoreanCount();
        this.foreignerCount = entity.getForeignerCount();
        this.totalCount = entity.getTotalCount();
        this.oldCount = entity.getOldCount();
    }
}
