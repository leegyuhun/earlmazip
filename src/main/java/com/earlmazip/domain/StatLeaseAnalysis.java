package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "stat_lease_analysis")
@Getter
public class StatLeaseAnalysis {
    @Id
    @GeneratedValue
    @Column(name = "seq")
    private String seq;

    @Column(name = "gubn_code")
    private String gubnCode;

    @Column(name = "gubn_name")
    private String gubnName;

    @Column(name = "deal_year")
    private String dealYear;

    @Column(name = "deal_mon")
    private String dealMon;

    @Column(name = "deal_yymm")
    private String dealYYMM;

    @Column(name = "short_cnt")
    private int shortCnt;

    @Column(name = "long_cnt")
    private int longCnt;

    @Column(name = "sum")
    private int sum;

    @Column(name = "rate")
    private float rate;
}