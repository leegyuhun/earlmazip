package aptdata.earlmazip.controller.dto;

import aptdata.earlmazip.domain.*;
import lombok.Getter;

import javax.persistence.Column;
import java.text.DecimalFormat;

@Getter
public class StatResponseDto {
    private String seq;
    private String code;
    private String name;
    private String dealYear;
    private String dealYYMM;
//    public String getDealYYMM() {
//        return dealYYMM.substring(0,4) + "-" + dealYYMM.substring(4,6);
//    }
    public String dealType;
    public String useAreaType;

    public StatResponseDto() {
    }

    private int minPrice;

    private int avgPrice;
    private int maxPrice;
    private String minPriceStr;
    private String avgPriceStr;
    private String maxPriceStr;
    public String getMinPriceStr() {
        return new DecimalFormat("0.0#").format((float)this.minPrice / 10000) + "억";
    }

    public String getAvgPriceStr() {
        return new DecimalFormat("0.0#").format((float)this.avgPrice / 10000) + "억";
    }

    public String getMaxPriceStr() {
        return new DecimalFormat("0.0#").format((float)this.maxPrice / 10000) + "억";
    }
    private int cnt;
    private int highestCnt;
    private float highestRate;
    private float avgUseArea;

    public StatResponseDto(StatAreaYYMM entity) {
        this.seq = entity.getSeq();
        this.code = entity.getAreaCode();
        this.name = entity.getAreaName();
        this.dealYear = entity.getDealYear();
        this.dealYYMM = entity.getDealYYMM();
        this.useAreaType = entity.getUseAreaType();
        this.minPrice = entity.getMinPrice();
        this.avgPrice = entity.getAvgPrice();
        this.maxPrice = entity.getMaxPrice();
        this.cnt = entity.getCnt();
        this.highestRate = entity.getHighestRate();
        this.avgUseArea = entity.getAvgUseArea();
    }

    public StatResponseDto(StatSidoYYMM entity) {
        this.seq = entity.getSeq();
        this.code = entity.getSidoCode();
        this.name = entity.getSidoName();
        this.dealYear = entity.getDealYear();
        this.dealYYMM = entity.getDealYYMM();
        this.useAreaType = entity.getUseAreaType();
        this.minPrice = entity.getMinPrice();
        this.avgPrice = entity.getAvgPrice();
        this.maxPrice = entity.getMaxPrice();
        this.cnt = entity.getCnt();
        this.highestRate = entity.getHighestRate();
        this.avgUseArea = entity.getAvgUseArea();
    }

    public StatResponseDto(StatTheme entity) {
        this.seq = entity.getSeq();
        this.code = entity.getThemeCode();
        this.name = entity.getThemeName();
        this.dealYYMM = entity.getDate();
        this.minPrice = entity.getMinPrice();
        this.avgPrice = entity.getAvgPrice();
        this.maxPrice = entity.getMaxPrice();
        this.cnt = entity.getCnt();
        this.highestCnt = entity.getHighestCnt();
        this.highestRate = entity.getHighestRate();
    }

    public StatResponseDto(StatSigunguYYMM entity) {
        this.seq = entity.getSeq();
        this.code = entity.getSigunguCode();
        this.name = entity.getSigunguName();
        this.dealYYMM = entity.getDealYYMM();
        this.minPrice = entity.getMinPrice();
        this.avgPrice = entity.getAvgPrice();
        this.maxPrice = entity.getMaxPrice();
        this.cnt = entity.getCnt();
        this.highestCnt = entity.getHighestCnt();
        this.highestRate = entity.getHighestRate();
        this.avgUseArea = entity.getAvgUseArea();
    }

    public StatResponseDto(StatBuildYear entity) {
        this.seq = entity.getSeq();
        this.code = entity.getRegnCode();
        this.name = entity.getRegnName();
        this.dealYYMM = entity.getDealYYMM();
        this.minPrice = entity.getMinPrice();
        this.avgPrice = entity.getAvgPrice();
        this.maxPrice = entity.getMaxPrice();
        this.cnt = entity.getCnt();
    }

    public StatResponseDto(StatSigunguType entity) {
        this.seq = entity.getSeq();
        this.code = entity.getSigunguCode();
        this.name = entity.getSigunguName();
        this.dealYear = entity.getDealYear();
        this.dealYYMM = entity.getDealYYMM();
        this.dealType = entity.getDealType();
        this.useAreaType = entity.getUseAreaType();
        this.minPrice = entity.getMinPrice();
        this.avgPrice = entity.getAvgPrice();
        this.maxPrice = entity.getMaxPrice();
        this.cnt = entity.getCnt();
        this.highestCnt = entity.getHighestCnt();
        this.highestRate = entity.getHighestRate();
        this.avgUseArea = entity.getAvgUseArea();
    }

    public StatResponseDto(StatDistribution entity) {
        this.seq = entity.getSeq();
        this.code = entity.getSigunguCode();
        this.name = entity.getSigunguName();
        this.dealYear = entity.getDealYear();
        this.cnt = entity.getCnt();
    }
}
