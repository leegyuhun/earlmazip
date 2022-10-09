package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "stat_sido_yymm")
@Getter
public class StatSidoYYMM {
    @Id
    @GeneratedValue
    @Column(name="seq")
    private String seq;

    @Column(name = "sido_code")
    private String sidoCode;

    @Column(name = "sido_name")
    private String sidoName;

    @Column(name = "deal_year")
    private String dealYear;

    @Column(name = "deal_yymm")
    private String dealYYMM;

    public String getDealYYMM() {
        return dealYYMM.substring(0,4) + "-" + dealYYMM.substring(4,6);
    }

    @Column(name = "use_area_type")
    public String useAreaType;

    @Column(name = "min_price")
    private int minPrice;

    @Column(name = "avg_price")
    private int avgPrice;

    @Column(name = "max_price")
    private int maxPrice;

    @Column(name = "cnt")
    private int cnt;

    @Column(name = "highest_rate")
    private float highestRate;

    private float avgUseArea;
}
