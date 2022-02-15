package aptdata.earlmazip.controller.dto;

import aptdata.earlmazip.domain.StatAreaYYMM;
import aptdata.earlmazip.domain.StatSidoYYMM;
import lombok.Getter;

import javax.persistence.Column;

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
    public String useAreaType;
    private int minPrice;
    private int avgPrice;
    private int maxPrice;
    private int cnt;
    private float highestRate;

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
    }
}
