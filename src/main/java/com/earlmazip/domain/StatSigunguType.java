package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "stat_sigungu_type")
@Getter
public class StatSigunguType {
    @Id
    @GeneratedValue
    private String seq;

    private String areaCode;

    private String sigunguCode;

    private String sigunguName;

    private String dealType;

    private String dealYear;

    private String dealMon;

    @Column(name = "deal_yymm")
    private String dealYYMM;

    public String getDealYYMM() {
        return dealYYMM.substring(0,4) + "-" + dealYYMM.substring(4,6);
    }

    public String useAreaType;

    private int minPrice;

    private int avgPrice;

    private int maxPrice;

    private int cnt;

    private int highestCnt;

    private float highestRate;

    private float avgUseArea;

    private int totPrice;
}
